/**
 * 
 */
package core.spells;

import core.objects.AbstractObject;
import core.space.Position;

/**
 * @author deamon
 *
 */
public class Fireball extends AbstractProjectileSpell {
	
	

	public Fireball() {
		super();
		this.setSpeed(3);
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
	
	

}
