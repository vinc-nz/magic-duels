package net.server;

import java.io.IOException;
import java.io.InputStream;

import net.NetListener;

import input.CharacterController;

public class ServerListener extends NetListener {
	CharacterController controller;
	boolean clientReady;
	ServerGame game;

	public ServerListener(CharacterController controller, InputStream is, ServerGame game) {
		super(is);
		this.controller = controller;
		this.clientReady = false;
		this.game = game;
	}

	@Override
	protected void performAction(String trigger) throws IOException {
		if (!clientReady && trigger.equals("ready")) {
			clientReady = true;
			game.clientReady();
		}
		else {
			this.controller.performAction(trigger);
			game.forward(trigger);
		}
	}
}
