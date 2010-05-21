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
	
	public void changeSlotType(String message)
	{
		String []parameter = message.substring(Messages.CHANGESLOTTYPE.length()).split(";");

		this.slots.remove((Integer.parseInt(parameter[0])));
		this.slots.add(Integer.parseInt(parameter[0]), parameter[1]);
	}
	
	public void addSlot(String slot){ this.slots.add(slot); }

	public int getNumSlots(){ return this.slots.size(); }

	public List<String> getSlots() { return slots; }
	
}
