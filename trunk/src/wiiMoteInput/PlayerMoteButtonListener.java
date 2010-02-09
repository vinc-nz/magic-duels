package wiiMoteInput;

import input.CharacterController;
import main.java.motej.event.CoreButtonEvent;
import main.java.motej.event.CoreButtonListener;

public class PlayerMoteButtonListener implements CoreButtonListener {
	
	protected CharacterController characterController;
	
	protected PlayingMote playingMote;
	
	public void setPlayingMote(CharacterController characterController, PlayingMote playingMote) {
		this.characterController = characterController;
		this.playingMote = playingMote;
	}

	@Override
	public void buttonPressed(CoreButtonEvent evento) {
		
		if (evento.isButtonAPressed())
		{
			this.characterController.switch_pos();
			if(!this.playingMote.isChecking)
			{
				System.out.println("AVVIO L THREA PER IL CHECK!");
				this.playingMote.check();
			}
		}

	}

}
