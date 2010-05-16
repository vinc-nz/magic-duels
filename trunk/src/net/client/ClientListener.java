package net.client;

import input.CharacterController;

import java.io.InputStream;

import net.NetListener;

public class ClientListener extends NetListener {
	
	CharacterController[] controllers;

	public ClientListener(CharacterController[] controllers, InputStream is) {
		super(is);
		this.controllers = controllers;
	}

	@Override
	protected void performAction(String trigger) {
		int playerId = NetListener.getId(trigger);
		controllers[playerId-1].performAction(trigger);
	}

	
}
