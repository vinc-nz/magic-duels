package wiiMoteInput;

import main.java.motej.event.CoreButtonEvent;
import main.java.motej.event.CoreButtonListener;

public class PlayerMoteButtonListener implements CoreButtonListener {
	
	public PlayingMote playingMote;
	
	public void setPlayingMote(PlayingMote playingMote) {
		this.playingMote = playingMote;
	}

	@Override
	public void buttonPressed(CoreButtonEvent evento) {
		
		if (evento.isButtonAPressed())
		{
			if(!playingMote.isChecking)
			{
				System.out.println("AVVIO L THREA PER IL CHECK!");
				playingMote.check();
			}
		}

	}

}
