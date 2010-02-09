package wiiMoteInput;

import main.java.motej.Mote;
import main.java.motej.event.AccelerometerEvent;
import main.java.motej.event.AccelerometerListener;

public class PlayerMoteAccellerometerListener implements AccelerometerListener<Mote> {
	
	PlayingMote playingMote;
	
	public PlayerMoteAccellerometerListener(PlayingMote playingMote) {
		this.playingMote = playingMote;
	}

	public void accelerometerChanged(AccelerometerEvent<Mote> event) {
		
		this.playingMote.setCurrentXvalue(event.getX());
		this.playingMote.setCurrentXvalue(event.getY());
		this.playingMote.setCurrentXvalue(event.getZ());
		
		//System.out.println(evento.getX() + " : " + evento.getY() + " : " + evento.getZ());
		
	}
	
}
