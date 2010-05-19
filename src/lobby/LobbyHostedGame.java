package lobby;

import java.util.ArrayList;
import java.util.List;

public class LobbyHostedGame {

	String gameName;
	int porta;
	int numSlots;
	int freeSlots;
	
	List<LobbyHostedGameSlot> slots;
	
	public LobbyHostedGame(String gameName, int porta, int numSlots)
	{
		this.gameName = gameName;
		this.porta = porta;
		this.numSlots = numSlots;
		this.freeSlots = numSlots;
		this.slots = new ArrayList<LobbyHostedGameSlot>();
		
		for (int i = 0; i < numSlots; i++)
			this.slots.add(new LobbyHostedGameSlot());
		
	}

	// TODO: aggiunta/rimozione giocatore : syncronized
	
	public void changeSlotState(int slotIndex, String state)
	{
		this.slots.get(slotIndex).type = state;
	}
	
	public String getGameName() {
		return gameName;
	}

	public int getPorta() {
		return porta;
	}

	public int getNumSlots() {
		return numSlots;
	}

	public int getFreeSlots() {
		return freeSlots;
	}

	public List<LobbyHostedGameSlot> getSlots() {
		return slots;
	}	

}
