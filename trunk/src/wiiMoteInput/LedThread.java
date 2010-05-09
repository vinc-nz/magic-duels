package wiiMoteInput;

import motej.Mote;

public class LedThread extends Thread {

	public static String NORMAL = "normal";
	
	public String type;
	private Mote mote;
	private boolean[] leds = new boolean[] { false, false, false, false };

	
	public LedThread(Mote mote)
	{
		this.mote = mote;
		this.type = LedThread.NORMAL;
		mote.setPlayerLeds(this.leds);
	}
	
	public void setType(String type) {
		this.type = type;
	}

	private void normal()
	{
		for (int i = 0; i < this.leds.length; i++) {
			if(leds[i])
				leds[i] = false;
			else
				leds[i] = true;	

			mote.setPlayerLeds(this.leds);
	
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
		
	@Override
	public void run() {

		while(true)
		{
			if(this.type.equals(LedThread.NORMAL))
				this.normal();
		}
		
	}
	
}
