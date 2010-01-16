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
	
	public CharacterController getController(Fight fight) throws IOException {
		return new NetForwarderController(local,fight,channel.getOutputStream());
		
	}
	
	public void buildListener(Fight fight) throws IOException {
		short id = Fight.ID_P1;
		if (local == id)
			id = Fight.ID_P2;
		CharacterController controller = new CharacterController(id, fight);
		NetListener listener = new NetListener(controller,channel.getInputStream());
		listener.start();
	}
	
	

}
