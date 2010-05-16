package core;

public class HostedGameSlot {

	public static String OPEN = "OPEN";
	public static String CLOSED = "CLOSED";
	public static String IA = "IA";
	public static String HUMAN = "HUMAN";
	
	String type;
	Connection human;
	
	public HostedGameSlot() {
	
		this.type = HostedGameSlot.OPEN;
		this.human = null;
		
	}
	
	public synchronized boolean joinSlot(Connection joiningHuman)
	{
		if(this.type.equals(HostedGameSlot.OPEN))
		{
			this.type = HostedGameSlot.HUMAN;
			this.human = joiningHuman;
			return true;
		} else
			return false;
	}
	
	public void leaveSlot()
	{
		this.type = HostedGameSlot.OPEN;
		this.human = null;
	}

	public void closeSlot()
	{
		this.type = HostedGameSlot.CLOSED;
		this.human = null;
	}
	
	public void iaSlot()
	{
		this.type = HostedGameSlot.IA;
		this.human = null;
	}
}
