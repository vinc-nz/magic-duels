package core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HostedGame {

	Connection host;
	int porta;
	
	String gameName;
	int numSlots;
	int numHumanPlayers;
	int numIaPlayers;
	
	List<HostedGameSlot> slots;
	
	public HostedGame(Connection host, int porta, String gameName, int numSlots)
	{
		this.host = host;
		this.porta = porta;
		this.gameName = gameName;
		this.numSlots = numSlots;
		this.numHumanPlayers = 0;
		this.numIaPlayers = 0;
		this.slots = new ArrayList<HostedGameSlot>();
		
		for (int i = 0; i < numSlots; i++)
			this.slots.add(new HostedGameSlot());
		
		this.joinGame(this.host);
	}
	
	// TODO: aggiunta/rimozione giocatore : syncronized
	
	public synchronized int joinGame(Connection player)
	{
		
		for (Iterator iterator = this.slots.iterator(); iterator.hasNext();) {
			HostedGameSlot slot = (HostedGameSlot) iterator.next();
			
			if(slot.isJoinable())
			{
				slot.joinSlot(player);
				this.numHumanPlayers++;
				
				System.out.println("NUMERO UMANI: " + this.numHumanPlayers);
				
				return slots.indexOf(slot);
			}
			
		}
		return -1;
	}
	
	public synchronized void leaveGame(String playerName)
	{
		int slotIndex = -1;
		
		for (Iterator iterator = this.slots.iterator(); iterator.hasNext();)
		{
			HostedGameSlot slot = (HostedGameSlot) iterator.next();
			if(slot.isHuman())
				if(slot.human.utente.getNome().equals(playerName))
				{
					slot.changeSlotState(Messages.OPEN);
					slotIndex = this.slots.indexOf(slot);
						
					break;
				}
		}
				
		if(slotIndex != -1)
		{
			String message = Messages.CHANGESLOTTYPE;
			message += String.valueOf(slotIndex) + ";";
			message += Messages.OPEN;

			for (Iterator iterator = this.slots.iterator(); iterator.hasNext();)
			{
				HostedGameSlot slot = (HostedGameSlot) iterator.next();
				if(slot.isHuman() && this.slots.indexOf(slot) != 0)
				{
					slot.human.sendMessage(message);
					System.out.println("Server>" + slot.human.utente.getNome() + ": " + message);
				}
			}
			
			this.host.sendMessage(Messages.SLOTLEFT + slotIndex);
		}
	}
	
	public String getSlotMessageInfo()
	{
		String msg = "";
		
		for(int i=0; i<this.numSlots; i++)
			msg += ((HostedGameSlot)this.slots.get(i)).getPlayerName() + ";";
		
		return msg;
		
	}
	
	public synchronized void changeSlotType(String msg)
	{
		String []parameter = msg.substring(Messages.CHANGESLOTTYPE.length()).split(";");
				
		HostedGameSlot slot = this.slots.get(Integer.parseInt(parameter[0]));
		
		if(slot.type.equals(parameter[1])) return;
		
		if(slot.isHuman())
		{
			slot.human.sendMessage(Messages.PLAYERKICKED);
			this.numHumanPlayers--;
		}
		
		if(slot.isIA()) this.numIaPlayers--;
		
		if(parameter[1].equals(Messages.IA)) this.numIaPlayers++;
			
		slot.changeSlotState(parameter[1]);
		
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
	
	public void startServerGame()
	{
		String message = Messages.STARTSERVERGAME;
		message += String.valueOf(this.numHumanPlayers) + ";";
		message += String.valueOf(this.numIaPlayers);
		
		this.host.sendMessage(message);
	}
	
	public void startClientGame()
	{
		for (Iterator iterator = this.slots.iterator(); iterator.hasNext();) {
			HostedGameSlot slot = (HostedGameSlot) iterator.next();
			
			if(slot.isHuman() && !slot.human.equals(this.host))
				slot.human.sendMessage(Messages.STARTCLIENTGAME);		
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
