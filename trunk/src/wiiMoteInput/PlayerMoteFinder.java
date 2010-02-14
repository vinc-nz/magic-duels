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
	
	public void findMote() {
				
			this.start();

	}
	
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
		};
	
		while(!this.disconnectMote)
			try {
				super.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	
		mote.disconnect();
		this.mote = null;
	}
	
}
