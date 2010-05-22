package net;

import java.io.IOException;

import core.fight.Fight;
import core.objects.Spell;




/*
 * controller che inoltra in rete i trigger
 */
public class NetForwarderController extends NetCharacterController {
	NetGame game;
	
	
	public NetForwarderController(int id, Fight fight, NetGame game) {
		super(id);
		this.game = game;
	}
	
	public void forward(String message) {
		try {
			game.forward(message);
		} catch (IOException e) {
			game.notifyHostUnreachable();
		};
	}
	
	
	@Override
	public void castSpell(Class<? extends Spell> spell) {
		this.forward(Message.getSpellMessage(getPlayerID(), spell.getName()));
		super.castSpell(spell);
	}
	
	@Override
	public void move(String direction) {
		this.forward(Message.getMoveMessage(this.getPlayerID(), direction));
		super.move(direction);
	}

}
