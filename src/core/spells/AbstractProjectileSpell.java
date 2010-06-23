/**
 * 
 */
package core.spells;

import core.fight.Character;
import core.objects.AbstractObject;
import core.objects.MovingObject;
import core.objects.Spell;
import core.space.Position;

/**
 * @author spax
 *
 */
public abstract class AbstractProjectileSpell extends MovingObject implements Spell {
	
	Position target;
	Character owner;
	
	public abstract int getDamage();

	
	

	@Override
	public void launch() {
		this.setDirection(owner.getDirection());
		this.materialize();
	}
	
	@Override
	public void update() {
		this.moveForward();
	}
	
	@Override
	public void handleCollision(AbstractObject other) {
		if (other instanceof Character) {
			((Character) other).applyDamage(this.getDamage());
		}
		this.destroy();
	}
	
	@Override
	public void setOwner(Character owner) {
		this.owner = owner;
		this.setPosition(owner.getPosition());
	}
	
	@Override
	public boolean collides(AbstractObject other) {
		if (other instanceof AbstractProjectileSpell && 
				((AbstractProjectileSpell) other).owner == this.owner)
			return false;
		if (other instanceof Protection &&
			((Protection) other).owner == this.owner)
			return false;
		if (other == this.owner)
			return false;
		return super.collides(other);
	}
}