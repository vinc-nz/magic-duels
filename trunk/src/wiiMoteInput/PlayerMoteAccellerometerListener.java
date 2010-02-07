package wiiMoteInput;

import main.java.motej.Mote;
import main.java.motej.event.AccelerometerEvent;
import main.java.motej.event.AccelerometerListener;

public class PlayerMoteAccellerometerListener implements AccelerometerListener<Mote> {
	
	PlayingMote playingMote;
	
	public PlayerMoteAccellerometerListener(PlayingMote playingMote) {
		this.playingMote = playingMote;
	}

	public void accelerometerChanged(AccelerometerEvent<Mote> evento) {
		
		this.playingMote.setCurrentXvalue(evento.getX());
		this.playingMote.setCurrentXvalue(evento.getY());
		this.playingMote.setCurrentXvalue(evento.getZ());
		
		//System.out.println(evento.getX() + " : " + evento.getY() + " : " + evento.getZ());
		
	}
	
}
