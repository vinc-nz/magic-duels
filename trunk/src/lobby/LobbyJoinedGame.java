package lobby;

import java.util.ArrayList;
import java.util.List;

public class LobbyJoinedGame {
	
	String gameName;

	String ip;
	int porta;

	List<String> slots;

	public LobbyJoinedGame(String gameName, String ip, int porta) {

		this.gameName = gameName;
		this.ip = ip;
		this.porta = porta;
		
		this.slots = new ArrayList<String>();
	}
	
	/**
	 * Changes the type of a slot
	 * @param message the message sent by the server containing informations about the slot to modify and protocol information
	 */
	public void changeSlotType(String message)
	{
		String []parameter = message.substring(Messages.CHANGESLOTTYPE.length()).split(";");

		this.slots.remove((Integer.parseInt(parameter[0])));
		this.slots.add(Integer.parseInt(parameter[0]), parameter[1]);
	}
	
	/**
	 * @return the name of the game
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * Adds a slot
	 * @param slot the slot to add
	 */
	public void addSlot(String slot){ this.slots.add(slot); }

	/**
	 * @return the number of slots
	 */
	public int getNumSlots(){ return this.slots.size(); }

	/**
	 * @return a list of slots
	 */
	public List<String> getSlots() { return slots; }
	
}
