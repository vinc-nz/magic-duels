package net;

import input.CharacterController;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import core.fight.Fight;
import core.objects.Spell;




/*
 * controller che inoltra in rete i trigger
 */
public class NetForwarderController extends CharacterController {
	DataOutputStream forwarder;
	
	public NetForwarderController(int id, Fight fight, OutputStream os) {
		super(id,fight);
		forwarder = new DataOutputStream(os);
	}
	
	
	public void forward(String trigger) {
		try {
			forwarder.writeBytes(trigger + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void castSpell(Class<? extends Spell> spell) {
		this.forward("spell>"+spell.getName());
		super.castSpell(spell);
	}
	
	@Override
	public void move(String name) {
		this.forward("move>"+name);
		super.move(name);
	}

}
