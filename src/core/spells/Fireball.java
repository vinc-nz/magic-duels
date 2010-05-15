/**
 * 
 */
package core.spells;

import core.objects.AbstractObject;

/**
 * @author deamon
 *
 */
public class Fireball extends AbstractProjectileSpell {
	
	

	public Fireball() {
		super();
		this.setSpeed(5);
		this.setRadius(10);
	}

	/* (non-Javadoc)
	 * @see core.spells.AbstractProjectileSpell#getDamage()
	 */
	@Override
	public int getDamage() {
		
		return 5;
	}

	/* (non-Javadoc)
	 * @see core.objects.AbstractObject#getName()
	 */
	@Override
	public String getName() {
		
		return "Fireball";
	}

	/* (non-Javadoc)
	 * @see core.objects.Spell#getManaCost()
	 */
	@Override
	public int getManaCost() {
		
		return 5;
	}
	
	@Override
	public boolean collides(AbstractObject other) {
		if (other instanceof Fireball)
			return false;
		return super.collides(other);
	}

}
