package wiiMoteInput;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import motej.Mote;
import motej.MoteFinder;
import motej.MoteFinderListener;

import com.intel.bluetooth.BlueCoveImpl;

/**
 * The class is used to find a Wii Mote controller
 * @author Neb
 *
 */
public class PlayerMoteFinder extends Thread implements MoteFinderListener {

	protected MoteFinder finder;
	protected Object lock = new Object();
	protected Mote mote;
	
	protected boolean disconnectMote;

	public boolean exception;
	
	public PlayerMoteFinder()
	{
		this.disconnectMote = false;
		this.exception = false;
				
		this.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e)
			{
				PlayerMoteFinder.this.exception = true;
				
		    	JOptionPane.showMessageDialog(new JFrame(), "Oops!\nSomething  went wrong!\nAre you sure you have connected your bluetooth device!?", 
		    			"Magic Duels Game", JOptionPane.ERROR_MESSAGE);
		    	e.printStackTrace();
			}
		});
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
		synchronized(lock) {
			lock.notifyAll();
		}
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

		System.out.println("SONO QUI !");
			
		mote.disconnect();
		this.mote = null;

		BlueCoveImpl.shutdown();
		BlueCoveImpl.shutdownThreadBluetoothStack();
		
		System.out.println("SONO QUI 2 !");
	}
	
}
