package net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import core.Fight;

import input.CharacterController;

public class ServerGame extends NetGame{
	Socket channel;
	
	public ServerGame(int port) {
		super(Fight.ID_P1);
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
