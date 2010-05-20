package net;

import game.Game;

import java.io.InputStream;


public class ClientListener extends NetListener {
	
	NetCharacterController[] controllers;
	ClientGame game;

	public ClientListener(NetCharacterController[] controllers, InputStream is, ClientGame game) {
		super(is);
		this.controllers = controllers;
		this.game = game;
	}

	@Override
	protected void performAction(String message) {
		super.performAction(message);
		if (Message.serverReady(message))
			game.serverReady(Message.getPlayersName(message));
		else {
			int id = Message.getPlayerId(message);
			if (id!=game.localId)
				controllers[id-1].performAction(message);
		}
			
	}
	
	@Override
	public Game getGame() {
		return game;
	}

	
}
