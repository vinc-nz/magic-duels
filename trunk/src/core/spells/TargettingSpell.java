/**
 * 
 */
package core.spells;

import core.objects.Spell;
import core.space.Position;

/**
 * @author deamon
 *
 */
public interface TargettingSpell extends Spell {
	
	public void setTarget(Position target);

}
