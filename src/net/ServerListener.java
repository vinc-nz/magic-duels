package net;

import game.Game;

import java.io.InputStream;


public class ServerListener extends NetListener {
	NetForwarderController controller;
	boolean clientReady;
	ServerGame game;

	public ServerListener(NetForwarderController controller, InputStream is, ServerGame game) {
		super(is);
		this.controller = controller;
		this.clientReady = false;
		this.game = game;
	}

	@Override
	protected void performAction(String message) {
		super.performAction(message);
		if (Message.clientJoin(message))
			game.joinClient(Message.getClientName(message));
		else if (Message.clientReady(message))
			game.clientReady();
		else controller.performAction(message);
	}
	
	@Override
	public Game getGame() {
		return game;
	}
}
