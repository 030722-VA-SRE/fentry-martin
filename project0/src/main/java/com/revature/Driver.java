package com.revature;

import java.util.List;

import com.revature.exceptions.CardNotFoundException;
import com.revature.models.PlayerCard;
import com.revature.services.PlayerCardService;

import io.javalin.Javalin;

public class Driver {

	public static void main(String[]args) {
		
		PlayerCardService pcs = new PlayerCardService();
		
		Javalin app = Javalin.create();
		app.start(8080);
		
		// retrieve all items
		app.get("cards", (ctx)->{
			// within HTTP handlers, leverage methods to handle HTTP request
			try {
				List<PlayerCard>cards = pcs.getAllCards();
				ctx.json(cards);
				ctx.status(200);
			} catch(CardNotFoundException e) {
				ctx.status(404);
				ctx.result("No cards were found.");
			}
			
		});
		
		// retrieve card by its id
		app.get("card/{id}", (ctx) -> {
			// returns the value of pathparam of name id, converted string to int
			int id = Integer.parseInt(ctx.pathParam("id"));
			
			try {
				PlayerCard card = pcs.getById(id);
				ctx.json(card);
				ctx.status(200);
			} catch (CardNotFoundException e) {
				ctx.status(404);
				ctx.result("Card of id: " + id + " was not found.");
				// log this to file
			}

		});
		
	}
}
