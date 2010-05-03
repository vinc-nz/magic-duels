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
	boolean collideable = false;
	
	public abstract int getDamage();

	@Override
	public void setTarget(Position target) {
		this.target = target;
		
	}
	

	@Override
	public void launch() {
		Direction d = Direction.fromPoints(this.getPosition(), target);
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
	public boolean collides(AbstractObject other) {
		float dist = this.getPosition().distance(other.getPosition());
		
		if (!collideable && dist > 200) {
			collideable = true;
			return false;
		}
		return collideable && super.collides(other);
	}

}
