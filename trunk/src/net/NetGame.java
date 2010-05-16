package net;

import input.CharacterController;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import core.fight.Fight;



public abstract class NetGame {
	int localId;
	int numberOfPlayers;
	String name;
	Fight fight;
	
	
	
	
	public NetGame(String name, int numberOfPlayers, int localId) {
		this.name = name;
		this.numberOfPlayers = numberOfPlayers;
		this.localId = localId;
		this.fight = new Fight(numberOfPlayers);
		fight.getPlayer(localId).setName(name);
	}

	
	
	public int getLocalId() {
		return localId;
	}



	public abstract CharacterController getController() throws IOException;
	public abstract void buildListening() throws IOException;
	


	public Fight getFight() {
		return fight;
	}
	
	

	public abstract void sayReady() throws IOException;
	public abstract void waitOthers() throws IOException;
	
}
