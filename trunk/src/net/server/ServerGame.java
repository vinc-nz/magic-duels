package net.server;

import input.CharacterController;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import net.NetForwarderController;
import net.NetGame;
import net.NetListener;
import net.client.ClientController;

import core.fight.Fight;

public class ServerGame extends NetGame{
	Socket[] channels;
	NetListener[] listeners;
	DataOutputStream[] out;
	int clientsReady;

	public ServerGame(int port) throws IOException {
		this("Player1", 2, port);
		this.clientsReady = 0;
	}

	public ServerGame(String name, int numberOfPlayers, int port) throws IOException {
		super(name,numberOfPlayers,1);
		ServerSocket s;
		channels = new Socket[numberOfPlayers-1];
		s = new ServerSocket(port);
		for (int i=0;i<channels.length;i++) 
			channels[i] = s.accept();
	}

	@Override
	public void buildListening() throws IOException {
		this.buildForwarding();
		listeners = new NetListener[channels.length];
		for (int i = 0; i < listeners.length; i++) {
			NetForwarderController controller = 
				new ClientController(i+2, this.getFight(), channels[i].getOutputStream());
			listeners[i] = new ServerListener(controller, channels[i].getInputStream(), this);
			listeners[i].start();
		}
	}
	
	public void buildForwarding() throws IOException {
		out = new DataOutputStream[channels.length];
		for (int i = 0; i < out.length; i++) {
			out[i] = new DataOutputStream(channels[i].getOutputStream());
		}
	}

	@Override
	public CharacterController getController() throws IOException {
		return new ServerController(1, this.getFight(), this);
	}

	@Override
	public void sayReady() throws IOException {
		// TODO Auto-generated method stub

	}

	public void forward(String trigger) throws IOException {
		for (int i = 0; i < out.length; i++) {
			out[i].writeBytes(trigger + "\n");
		}
	}


	@Override
	public synchronized void waitOthers() throws IOException {
		if (clientsReady<channels.length)
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public synchronized void clientReady() {
		clientsReady++;
		if (clientsReady==channels.length)
			notify();
	}



}
