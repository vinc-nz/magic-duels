package wiiMoteInput;

import motej.Mote;
import motej.event.AccelerometerEvent;
import motej.event.AccelerometerListener;

public class PlayerMoteAccellerometerListener implements AccelerometerListener<Mote> {
	
	PlayingMote playingMote;
	
	public PlayerMoteAccellerometerListener(PlayingMote playingMote) {
		this.playingMote = playingMote;
	}

	public void accelerometerChanged(AccelerometerEvent<Mote> event) {
		
		this.playingMote.setCurrentXvalue(event.getX());
		this.playingMote.setCurrentYvalue(event.getY());
		this.playingMote.setCurrentZvalue(event.getZ());
		
		//System.out.println(event.getX() + " : " + event.getY() + " : " + event.getZ());
		
	}
	
}