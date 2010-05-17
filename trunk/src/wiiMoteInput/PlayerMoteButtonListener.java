package wiiMoteInput;

import input.CharacterController;

import java.sql.Timestamp;
import java.util.Date;

import motej.event.CoreButtonEvent;
import motej.event.CoreButtonListener;

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
		{
			long nextTimestamp = new Date().getTime();
			if( nextTimestamp - this.buttonBTimeStamp > 300)
				this.characterController.nextTarget();
			this.buttonBTimeStamp = nextTimestamp;
		}

	}
}