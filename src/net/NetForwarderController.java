package net;

import input.CharacterController;
import core.fight.Fight;
import core.objects.Spell;




/*
 * controller che inoltra in rete i trigger
 */
public abstract class NetForwarderController extends CharacterController {
	String id;
	
	
	public NetForwarderController(int id, Fight fight) {
		super(id,fight);
		this.id = Integer.toString(this.getPlayerID());
	}
	
	public abstract void forward(String trigger);
	
	
	@Override
	public void castSpell(Class<? extends Spell> spell) {
		this.forward(id+">spell>"+spell.getName());
		super.castSpell(spell);
	}
	
	@Override
	public void move(String name) {
		this.forward(id+">move>"+name);
		super.move(name);
	}

}
