package lobby;

import java.util.ArrayList;
import java.util.List;

public class LobbyHostedGame {

	LobbyClient lobbyClient;
	
	String gameName;
	int porta;
	int numSlots;
	int freeSlots;
	
	List<LobbyHostedGameSlot> slots;
	
	public LobbyHostedGame(LobbyClient lobbyClient, String gameName, int porta, int numSlots)
	{
		this.lobbyClient = lobbyClient;
		
		this.gameName = gameName;
		this.porta = porta;
		this.numSlots = numSlots;
		this.freeSlots = numSlots;
		this.slots = new ArrayList<LobbyHostedGameSlot>();
		
		for (int i = 0; i < numSlots; i++)
			this.slots.add(new LobbyHostedGameSlot());
	
		this.slots.get(0).joinSlot(this.lobbyClient.playerName);
	}

	// TODO: aggiunta/rimozione giocatore : syncronized
	
	public void joinSlot(int slotIndex, String playerName)
	{
		if(this.slots.get(slotIndex).joinSlot(playerName))
			this.lobbyClient.graphicLobby.hostAGame();
	}
	
	public void changeSlotState(int slotIndex, String state)
	{
		String msg = Messages.CHANGESLOTTYPE;
		msg += String.valueOf(slotIndex) + ";";
		msg += state;
		
		this.lobbyClient.sendMessage(msg);
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

	public LobbyHostedGameSlot getSlot(int index)
	{
		return this.slots.get(index);
	}
	
}
