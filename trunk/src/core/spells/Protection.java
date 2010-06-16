package core.spells;

import java.util.Date;

import core.fight.Character;
import core.objects.AbstractObject;
import core.objects.Spell;

public class Protection extends AbstractObject implements Spell {
	public static final int DURATION = 5;
	
	long launchTime = 0;
	Character owner;
	
	public Protection() {
		this.setRadius(50);
	}

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
		this.setPosition(owner.getPosition());
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
		this.owner = owner;
	}
	
	@Override
	public boolean collides(AbstractObject other) {
		if (other instanceof AbstractProjectileSpell
			&& ((AbstractProjectileSpell) other).owner == this.owner)
			return false;
		return super.collides(other);
	}

}
