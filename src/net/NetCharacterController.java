package net;

import input.CharacterController;

public class NetCharacterController extends CharacterController {

	public NetCharacterController(int playerID) {
		super(playerID);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * performs an action specified by an incoming message
	 * @param message the message
	 *  
	 */
	public void performAction(String message) {
		if (Message.isSpellMessage(message)) {
			this.castSpell(Message.getSpellName(message));
		}
		else if (Message.isMovementMessage(message)) {
			this.move(Message.getMovementDirection(message));
		}
	}

}
