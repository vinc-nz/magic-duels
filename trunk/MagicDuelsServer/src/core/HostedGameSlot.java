package core;

public class HostedGameSlot {
	
	String type;
	Connection human;
	String iaName;
	
	public HostedGameSlot() {
	
		this.type = Messages.OPEN;
		this.human = null;
		this.iaName = "Firefox";
		
	}
	
	public synchronized boolean joinSlot(Connection joiningHuman)
	{
		if(this.type.equals(Messages.OPEN))
		{
			this.type = Messages.HUMAN;
			this.human = joiningHuman;
			return true;
		} else
			return false;
	}
	
	public void changeSlotState(String newType)
	{
		this.type = newType;
		this.human = null;
	}
	
	public boolean isJoinable(){ return this.type.equals(Messages.OPEN); }
	
	public boolean isHuman() { return this.type.equals(Messages.HUMAN); }
	public boolean isIA() { return this.type.equals(Messages.IA); }
	
	public String getPlayerName()
	{
		if(this.type.equals(Messages.HUMAN))
			return this.human.utente.getNome();
		else if(this.type.equals(Messages.IA))
			return this.iaName;
		else return this.type;
	}
	
}
