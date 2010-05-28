package wiiMoteInput;

import input.CharacterController;

import java.util.Date;

import motej.event.CoreButtonEvent;
import motej.event.CoreButtonListener;

/**
 * The class manages the Wii Mote button listener
 * @author neb
 *
 */
public class PlayerMoteButtonListener implements CoreButtonListener {
	
	protected CharacterController characterController;
	protected PlayingMote playingMote;
	
	long buttonBTimeStamp;
	
	public PlayerMoteButtonListener(PlayingMote playingMote, CharacterController characterController)
	{
		this.characterController = characterController;
		this.playingMote = playingMote;
		this.buttonBTimeStamp = 0;
	}

	/**
	 * Is called as a button is pressed
	 */
	@Override
	public void buttonPressed(CoreButtonEvent evento) {
		
		if (evento.isButtonAPressed())
			if(!this.playingMote.isChecking)
				this.playingMote.check();
		
		if(evento.isButtonMinusPressed() || evento.isDPadLeftPressed())
			this.characterController.move("left");
		
		if(evento.isButtonPlusPressed() || evento.isDPadRightPressed())
			this.characterController.move("right");

		if(evento.isDPadUpPressed())
		this.characterController.move("forward");
		
		if(evento.isDPadDownPressed())
		this.characterController.move("backward");
		
		if(evento.isButtonBPressed())
		{
			long nextTimestamp = new Date().getTime();
			if( nextTimestamp - this.buttonBTimeStamp > 300)
				this.characterController.nextTarget();
			this.buttonBTimeStamp = nextTimestamp;
		}

	}
}