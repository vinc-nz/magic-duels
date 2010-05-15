package wiiMoteInput;

import input.CharacterController;
import motej.event.CoreButtonEvent;
import motej.event.CoreButtonListener;

public class PlayerMoteButtonListener implements CoreButtonListener {
	
	protected CharacterController characterController;
	
	protected PlayingMote playingMote;
	
	public PlayerMoteButtonListener(PlayingMote playingMote, CharacterController characterController)
	{
		this.characterController = characterController;
		this.playingMote = playingMote;		
	}

	@Override
	public void buttonPressed(CoreButtonEvent evento) {
		
		if (evento.isButtonAPressed())
			if(!this.playingMote.isChecking)
				this.playingMote.check();
		
		if(evento.isButtonMinusPressed())
			this.characterController.move("left");
		
		if(evento.isButtonPlusPressed())
			this.characterController.move("right");

		if(evento.isButtonBPressed())
			this.characterController.nextTarget();
	}

}