package wiiMoteInput;

import motej.Mote;
import motej.MoteFinder;
import motej.MoteFinderListener;

public class PlayerMoteFinder extends Thread implements MoteFinderListener {

	protected MoteFinder finder;
	protected Object lock = new Object();
	protected Mote mote;
	
	protected boolean disconnectMote;

	public PlayerMoteFinder()
	{
		this.disconnectMote = false;
	}
	
	public Mote getMote() {
		return mote;
	}

	public void disconnectMote()
	{
		this.disconnectMote = true;
		super.notifyAll();
	}
	
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
		

		System.out.println("INIZIO WHILE: IF NOT DISCONNECT..");
		while(!this.disconnectMote)
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("FINE WHILE: MOTE DISCONNECTED..");	
		mote.disconnect();
		this.mote = null;

	}
	
}
