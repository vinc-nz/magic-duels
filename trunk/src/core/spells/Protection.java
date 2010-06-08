package core.spells;

import java.util.Date;

import core.fight.Character;
import core.objects.AbstractObject;
import core.objects.Spell;

public class Protection extends AbstractObject implements Spell {
	public static final int DURATION = 5;
	
	long launchTime = 0;
	

	@Override
	public String getName() {
		
		return "Protection";
	}

	@Override
	public void handleCollision(AbstractObject other) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		if (isInGame() && new Date().getTime()-launchTime>DURATION*1000)
			this.destroy();
	}

	@Override
	public int getManaCost() {
		
		return 10;
	}

	@Override
	public void launch() {
		this.materialize();
		launchTime = new Date().getTime();
	}

	@Override
	public void setOwner(Character owner) {
		this.setPosition(owner.getPosition());
	}

}
