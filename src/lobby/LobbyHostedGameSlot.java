package lobby;

public class LobbyHostedGameSlot {
	
	public String type;
	public String human;
	
	public LobbyHostedGameSlot() {
	
		this.type = Messages.OPEN;
		this.human = null;
		
	}
	
	public synchronized boolean joinSlot(String joiningHuman)
	{
		if(this.type.equals(Messages.OPEN))
		{
			this.type = Messages.HUMAN;
			this.human = joiningHuman;
			return true;
		} else
			return false;
	}
	
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
	}
}
