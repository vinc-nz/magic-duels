package net;

import input.CharacterController;

import java.io.IOException;
import java.net.Socket;

import core.Fight;

public class NetGame {
	Socket channel;
	short local;
	
	
	
	public NetGame(short local) {
		super();
		this.channel = null;
		this.local = local;
	}
	
	public CharacterController getController(short id, Fight fight) throws IOException {
		if (id==local) {
			return new NetForwarderController(id,fight,channel.getOutputStream());
		}
		CharacterController controller = new CharacterController(id, fight);
		NetListener listener = new NetListener(controller,channel.getInputStream());
		listener.start();
		return controller;
	}
	
	

}
