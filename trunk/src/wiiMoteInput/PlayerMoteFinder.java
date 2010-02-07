package wiiMoteInput;

import main.java.motej.Mote;
import main.java.motej.MoteFinder;
import main.java.motej.MoteFinderListener;

public class PlayerMoteFinder implements MoteFinderListener {

	private MoteFinder finder;
	private Object lock = new Object();
	private Mote mote;

	public void moteFound(Mote mote) {
		this.mote = mote;
		synchronized(lock) {
			lock.notifyAll();
		}
	}
	
	public Mote findMote() {
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
		}
		return mote;
	}

}
