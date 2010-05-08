/**
 * 
 */
package core.spells;

import core.fight.Character;
import core.objects.AbstractObject;
import core.objects.MovingObject;
import core.space.Direction;
import core.space.Position;

/**
 * @author spax
 *
 */
public abstract class AbstractProjectileSpell extends MovingObject implements TargettingSpell {
	
	Position target;
	Character owner;
	
	public abstract int getDamage();

	@Override
	public void setTarget(Position target) {
		this.target = target;
		
	}
	

	@Override
	public void launch() {
		Direction d = Direction.fromPoints(this.owner.getPosition(), target);
		this.setDirection(d);
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
		if (other!=this.owner)
			return super.collides(other);
		return false;
	}

}
