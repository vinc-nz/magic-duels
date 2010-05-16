package net.client;

import input.CharacterController;

import java.io.InputStream;

import net.NetListener;

public class ClientListener extends NetListener {
	
	CharacterController[] controllers;
	int id;

	public ClientListener(CharacterController[] controllers, InputStream is, int id) {
		super(is);
		this.controllers = controllers;
		this.id = id;
	}

	@Override
	protected void performAction(String trigger) {
		String id = trigger.substring(0, trigger.indexOf('>'));
		int playerId = Integer.parseInt(id);
		if (playerId!=this.id)
			controllers[playerId-1].performAction(trigger);
	}

	
}
