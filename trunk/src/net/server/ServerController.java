package net.server;

import java.io.IOException;

import net.NetForwarderController;

import core.fight.Fight;

public class ServerController extends NetForwarderController {
	ServerGame game;
	

	public ServerController(int id, Fight fight, ServerGame game) {
		super(id, fight);
		this.game = game;
	}

	@Override
	public void forward(String trigger) {
		try {
			game.forward(trigger);
		} catch (IOException e) {
			System.out.println("errore ServerController");
			e.printStackTrace();
			System.exit(3);
		}
	}

}
