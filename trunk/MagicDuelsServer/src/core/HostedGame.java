package core;

import java.util.ArrayList;
import java.util.List;

public class HostedGame {

	Connection host;
	int porta;
	
	String gameName;

	int numSlots;
	int freeSlots;
	
	List<HostedGameSlot> slots;
	
	public HostedGame(Connection host, int porta, String gameName, int numSlots)
	{
		this.host = host;
		this.porta = porta;
		this.gameName = gameName;
		this.numSlots = numSlots;
		this.freeSlots = numSlots;
		this.slots = new ArrayList<HostedGameSlot>();
		
		for (int i = 0; i < numSlots; i++)
			this.slots.add(new HostedGameSlot());
		
	}
	
	// TODO: aggiunta/rimozione giocatore : syncronized
	
	public Connection getHost() {
		return host;
	}
	
	public void setHost(Connection host) {
		this.host = host;
	}
	
	public int getPorta() {
		return porta;
	}
	
	public void setPorta(int porta) {
		this.porta = porta;
	}
	
	public String getGameName() {
		return gameName;
	}
	
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public List<HostedGameSlot> getSlots() {
		return slots;
	}

	public void setSlots(List<HostedGameSlot> slots) {
		this.slots = slots;
	}
	
}
