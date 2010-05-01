package wiiMoteInput;

import motej.Extension;
import motej.Mote;
import motej.event.ExtensionEvent;
import motejx.extensions.nunchuk.Nunchuk;
import motejx.extensions.nunchuk.NunchukButtonEvent;
import motejx.extensions.nunchuk.NunchukButtonListener;

public class Prova extends ExtensionEvent implements NunchukButtonListener {

	private Nunchuk nunchuk;

	protected Mote mote;
	protected PlayerMoteFinder playerMoteFinder;
	
	protected int batteryLevel;

	public void extensionConnected(ExtensionEvent evt) {
		
		final Extension ext = evt.getExtension();
		
		if (ext instanceof Nunchuk) {
			nunchuk = (Nunchuk) ext;
			
			/*
			nunchuk.addAccelerometerListener(this);
			nunchuk.addAnalogStickListener(this);
			*/
			
			nunchuk.addNunchukButtonListener(this);
			
			Thread t = new Thread(new Runnable() {
			
				public void run() {
					while (nunchuk.getCalibrationData() == null) {
						try {
							Thread.sleep(1l);
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					}
					System.out.println(nunchuk.getCalibrationData());	
				}
			});
		}
	}	
	
	public Prova(Mote source) {
		super(source);
		
		this.mote = source;
	}

	public void buttonPressed(NunchukButtonEvent evt) {
		if (evt.isButtonCPressed())
			System.out.println("BOTTONE C");

		
		if (evt.isButtonZPressed())
			System.out.println("BOTTONE Z");

		}
	
	public static void main(String[] args) {
		
		PlayerMote playerMote = new PlayerMote();
		playerMote.findMote();
		
		playerMote.getMote().rumble(1000);
		
		Prova prova = new Prova(playerMote.getMote());
		
		
	}
	
}
