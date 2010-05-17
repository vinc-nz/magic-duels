package net.client;

import input.CharacterController;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import net.NetGame;
import net.NetListener;

public class ClientGame extends NetGame {
	Socket channel;
	NetListener listener;
	
	public ClientGame(String name, int numberOfPlayers, int localId, String address, int port) throws UnknownHostException, IOException {
		super(name, numberOfPlayers, localId);
		channel = new Socket(address, port);
	}
	
	public ClientGame(String address, int port) throws UnknownHostException, IOException {
		this("Player2", 2, 2, address, port);
	}
	
	@Override
	public CharacterController getController() throws IOException {
		return new ClientController(this.getLocalId(), this.getFight(), channel.getOutputStream());
	}
	
	@Override
	public void buildListening() throws IOException {
		CharacterController[] controllers = new CharacterController[this.getFight().numberOfPlayers()];
		for (int i = 0; i < controllers.length; i++) {
			if (i+1!=this.getLocalId())
				controllers[i] = new ClientController(i+1, this.getFight(), channel.getOutputStream());
		}
		this.listener = new ClientListener(controllers, channel.getInputStream(), this.getLocalId());
	}
	
	@Override
	public void sayReady() throws IOException {
		DataOutputStream o = new DataOutputStream(channel.getOutputStream());
		o.writeBytes("ready\n");
	}

	@Override
	public void waitOthers() throws IOException {
		listener.waitReadySignal();
		listener.start();
	}
	
}
