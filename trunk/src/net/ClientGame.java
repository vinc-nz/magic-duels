package net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import core.Fight;

public class ClientGame extends NetGame {
	
	public ClientGame(String address, int port) {
		super(Fight.ID_P2);
		try {
			channel = new Socket(address, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
