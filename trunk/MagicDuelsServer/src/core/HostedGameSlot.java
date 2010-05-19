package core;

public class HostedGameSlot {
	
	String type;
	Connection human;
	
	public HostedGameSlot() {
	
		this.type = Messages.OPEN;
		this.human = null;
		
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
	
	/*
	public void leaveSlot()
	{
		this.type = Messages.OPEN;
		this.human = null;
	}

	public void closeSlot()
	{
		this.type = Messages.CLOSED;
		this.human = null;
	}
	
	public void iaSlot()
	{
		this.type = Messages.IA;
		this.human = null;
	}*/
}
