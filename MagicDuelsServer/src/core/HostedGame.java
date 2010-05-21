package core;

import java.util.ArrayList;
import java.util.Iterator;
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
		
		this.joinGame(this.host);
	}
	
	// TODO: aggiunta/rimozione giocatore : syncronized
	
	public synchronized boolean joinGame(Connection player)
	{
		if(this.freeSlots == 0) return false;
		
		for (Iterator iterator = this.slots.iterator(); iterator.hasNext();) {
			HostedGameSlot slot = (HostedGameSlot) iterator.next();
			
			if(slot.isJoinable())
			{
				slot.joinSlot(player);
				return true;
			}
			
		}
		return false;
	}
	
	public String getSlotMessageInfo()
	{
		String msg = "";
		
		for(int i=0; i<this.numSlots; i++)
			msg += ((HostedGameSlot)this.slots.get(i)).getPlayerName() + ";";
		
		return msg;
		
	}
	
	public void changeSlotType(String msg)
	{
		String []parameter = msg.substring(Messages.CHANGESLOTTYPE.length()).split(";");
		
		this.slots.get(Integer.parseInt(parameter[0])).changeSlotState(parameter[1]);
		this.sendAll(msg);
	}
	
	public void sendAll(String msg)
	{
		for (Iterator iterator = this.slots.iterator(); iterator.hasNext();) {
			HostedGameSlot slot = (HostedGameSlot) iterator.next();
			if(slot.isHuman())
				slot.human.sendMessage(msg);
		}
	}
	
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
