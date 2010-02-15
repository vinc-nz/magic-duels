package wiiMoteInput;

import com.intel.bluetooth.BlueCoveImpl;
import com.intel.bluetooth.BluetoothStack;

import input.CharacterController;
import motej.Mote;

/*
 * The class manages the Wii Mote Controller 
 * with all its functions
 */
public class PlayerMote {
	
	protected Mote mote;
	protected PlayerMoteFinder playerMoteFinder;
	protected PlayingMote playingMote;
	
	protected int batteryLevel;

	/**
	 * The function is used to connect a WiiMote Controller.
	 * @return true if connected, false otherwise
	 */
	public boolean findMote()
	{

		BlueCoveImpl.setConfigProperty("bluecove.stack", "widcomm");
		
		this.playerMoteFinder = new PlayerMoteFinder();
		this.playerMoteFinder.start();
		
		while(this.playerMoteFinder.getMote() == null)
			try {
				Thread.sleep(101);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		
		this.mote = this.playerMoteFinder.getMote();
		
		if (mote != null) {
			
			while (mote.getStatusInformationReport() == null) {
				System.out.println("waiting for status information report");
				try {
					Thread.sleep(10l);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(mote.getStatusInformationReport());	
			
		}
		
		if (this.mote != null) return true;
		
		return false;
	}
	
	/*
	 * The function returns the connected
	 * WiiMote Controller, null if no controller 
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
			this.playerMoteFinder.disconnectMote();
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

	/**
	 * The function creates a PlayingMote that must be associated
	 * to a CharacterController
	 * @param characterController
	 */
	public void createPlayingMote(CharacterController characterController){
		this.playingMote = new PlayingMote(characterController, this);
	}
	
	public void removePlayingMote()
	{
		this.playingMote = null;
	}

	public PlayingMote getPlayingMote() {
		return this.playingMote;
	}
	
	public static void main(String[] args) {
		
		PlayerMote playerMote = new PlayerMote();
		playerMote.findMote();
		
		playerMote.getMote().rumble(1000);
		
		//PlayingMote playingMote = new PlayingMote(characterController, playerMote);
		
	}
	
}
