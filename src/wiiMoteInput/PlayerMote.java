package wiiMoteInput;

import motej.Mote;

/*
 * The class manages the Wii Mote Controller 
 * with all its functions
 */
public class PlayerMote {
	
	protected Mote mote;
	protected PlayingMote playingMote;
	
	protected int batteryLevel;

	/*
	 * The function is used to connect and syncronize
	 * a Wii Mote Controller.
	 * return true if connected,
	 * false otherwise
	 */
	public boolean findMote()
	{
		PlayerMoteFinder simpleMoteFinder = new PlayerMoteFinder();
		this.mote = simpleMoteFinder.findMote();
		
		if (this.mote != null) return true;
		
		return false;
	}
	
	/*
	 * The function returns the connected
	 * Wii Mote Controller, null if no contreller 
	 * is connected
	 */
	public Mote getMote()
	{
		return this.mote;
	}
	
	/*
	 * The function disconnects the wii mote controller
	 * (if there's one connected) and sets to null
	 * tha variable mote
	 */
	public void disconnectMote()
	{
		if(this.mote != null)
		{
			this.mote.disconnect();
			this.mote = null;
		}
	}
	
	/*
	 * The functions set the current value of the mote battery level.
	 * Note that it's only used by the report information listener
	 */
	public void setBatteryLevel(int batteryLevel)
	{
		this.batteryLevel = batteryLevel;
	}
}
