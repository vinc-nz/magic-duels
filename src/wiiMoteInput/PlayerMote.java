package wiiMoteInput;

import input.CharacterController;
import motej.Mote;

import com.intel.bluetooth.BlueCoveImpl;

/**
 * The class manages the Wii Mote Controller 
 * with all its functions
 * @author Neb
 */
public class PlayerMote extends Thread {
	
	public Mote mote;
	public PlayerMoteFinder playerMoteFinder;
	public PlayingMote playingMote;
	
	/**
	 * The function is used to connect a WiiMote Controller.
	 * It starts the main thread that operate the connection
	 */
	public void findMote()
	{
		this.start();
	}
	
	/**
	 * The function returns the connected
	 * WiiMote Controller, null if no controller 
	 * is connected
	 * @return the Mote object
	 */
	public Mote getMote()
	{
		return this.mote;
	}
	
	/**
	 * The function disconnects the wii mote controller
	 * (if there's one connected) and sets to null
	 * the variable mote
	 */
	public void disconnectMote()
	{
		if(this.mote != null)
		{
			this.playerMoteFinder.disconnectMote();
			this.mote = null;
		}
	}

	/**
	 * The function creates a PlayingMote that must be associated
	 * to a CharacterController
	 * @param characterController
	 */
	public void createPlayingMote(CharacterController characterController){
		this.playingMote = new PlayingMote(characterController, this);
	}
	
	/**
	 * The function removes the PlayingMote object
	 */
	public void removePlayingMote()
	{
		this.playingMote.removeAccellerometerListener();
		this.playingMote.removeButtonListener();
		this.playingMote = null;
	}

	/**
	 * @return the PlayingMote object
	 */
	public PlayingMote getPlayingMote() {
		return this.playingMote;
	}

	@Override
	public void run() {

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
		
		if (mote != null)
		{	
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
		
		if (this.mote != null)
		{
			Thread thread = new LedThread(this.mote);
			thread.start();	
		}	
	}	
}
