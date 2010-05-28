package wiiMoteInput;

import motej.Mote;
import motej.MoteFinder;
import motej.MoteFinderListener;

/**
 * The class is used to 
 * @author neb
 *
 */
public class PlayerMoteFinder extends Thread implements MoteFinderListener {

	protected MoteFinder finder;
	protected Object lock = new Object();
	protected Mote mote;
	
	protected boolean disconnectMote;

	public PlayerMoteFinder()
	{
		this.disconnectMote = false;
	}
	
	/**
	 * @return the mote object
	 */
	public Mote getMote() {
		return mote;
	}

	/**
	 * Disconnects the mote
	 */
	public synchronized void disconnectMote()
	{
		this.disconnectMote = true;
		super.notifyAll();
	}
	
	/**
	 * Is called as the mote has been connected
	 */
	public void moteFound(Mote mote) {
		this.mote = mote;
		synchronized(lock) {
			lock.notifyAll();
		}
	}
	
	/*
	public void findMote() {
				
		if (finder == null) {
			finder = MoteFinder.getMoteFinder();
			finder.addMoteFinderListener(this);
		}
		
		finder.startDiscovery();
		
		try {
			synchronized(lock) {
				lock.wait();
			}
		} catch (InterruptedException ex) {
			System.out.println(ex.getMessage());
		};
		
		while(!this.disconnectMote)
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	
	}
	*/
	@Override
	public void run() {
	
		if (finder == null) {
			finder = MoteFinder.getMoteFinder();
			finder.addMoteFinderListener(this);
		}
		finder.startDiscovery();
		try {
			synchronized(lock) {
				lock.wait();
			}
		} catch (InterruptedException ex) {
			System.out.println(ex.getMessage());
		};
		
		while(!this.disconnectMote)
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		mote.disconnect();
		this.mote = null;

	}
	
}
