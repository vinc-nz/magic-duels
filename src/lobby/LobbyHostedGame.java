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

	/**
	 * Adds a player into the slot
	 * @param slotIndex the index of the slot
	 * @param playerName the name of the player who's joining 
	 */
	public void joinSlot(int slotIndex, String playerName)
	{
		if(this.slots.get(slotIndex).joinSlot(playerName))
			this.lobbyClient.graphicLobby.hostAGame();
	}
	
	/**
	 * Changes tha state of the slot
	 * @param slotIndex the index of the slot
	 * @param state the new state of the slot
	 */
	public void changeSlotState(int slotIndex, String state)
	{
		String msg = Messages.CHANGESLOTTYPE;
		msg += String.valueOf(slotIndex) + ";";
		msg += state;
		
		this.lobbyClient.sendMessage(msg);
		this.slots.get(slotIndex).type = state;
	}
	
	/**
	 * @return the name of the game
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * @return the port of the game
	 */
	public int getPorta() {
		return porta;
	}

	/**
	 * @return the number of slots of the game
	 */
	public int getNumSlots() {
		return numSlots;
	}

	/**
	 * @return the number of free slot of the game
	 */
	public int getFreeSlots() {
		return freeSlots;
	}

	/**
	 * @return a list of the game slots 
	 */
	public List<LobbyHostedGameSlot> getSlots() {
		return slots;
	}	

	/**
	 * Returs a slot
	 * @param index the index of the slot 
	 * @return the slot indexed by the given number
	 */
	public LobbyHostedGameSlot getSlot(int index)
	{
		return this.slots.get(index);
	}
	
}
