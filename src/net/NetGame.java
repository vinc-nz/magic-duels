package net;

import input.CharacterController;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import core.fight.Fight;



public class NetGame {
	Socket channel;
	NetListener listener;
	int local;
	
	
	
	public NetGame(int local) {
		super();
		this.channel = null;
		this.local = local;
	}
	
	public CharacterController getController(Fight fight) throws IOException {
		return new NetForwarderController(local,fight,channel.getOutputStream());
		
	}
	
	public void buildListener(Fight fight) throws IOException {
		short id = 1;
		if (local == id)
			id = 2;
		CharacterController controller = new CharacterController(id, fight);
		this.listener = new NetListener(controller,channel.getInputStream());
	}

	public void sayReady() {
		try {
			DataOutputStream o = new DataOutputStream(channel.getOutputStream());
			o.writeBytes("ready\n");
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void waitOther() {
		try {
			listener.waitReadySignal();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listener.start();
	}
	
}
