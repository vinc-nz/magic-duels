package net;

import java.io.IOException;
import java.net.ServerSocket;

import core.fight.Fight;

public class ServerGame extends NetGame{
	
	public ServerGame(int port) {
		super(1);
		ServerSocket s;
		try {
			s = new ServerSocket(port);
			this.channel = s.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
