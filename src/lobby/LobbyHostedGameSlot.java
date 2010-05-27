package lobby;

public class LobbyHostedGameSlot {
	
	public String type;
	public String human;
	
	public LobbyHostedGameSlot() 
	{
		this.type = Messages.OPEN;
		this.human = null;
	}
	
	/**
	 * Adds a player into the slot
	 * @param joiningHuman the name of the joining player 
	 * @return true if joined, false otherwise
	 */
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
	
	/**
	 * Changes the state of the slot to open
	 */
	public void leaveSlot()
	{
		this.type = Messages.OPEN;
		this.human = null;
	}

	/**
	 * Changes the state of the slot to closed
	 */
	public void closeSlot()
	{
		this.type = Messages.CLOSED;
		this.human = null;
	}
	
	/**
	 * Changes the state of the slot to ia
	 */
	public void iaSlot()
	{
		this.type = Messages.IA;
		this.human = null;
	}
}
