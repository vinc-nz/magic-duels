/**
 * 
 */
package core.objects;

import core.fight.Character;


/**
 * @author spax
 *
 */
public interface Spell {
	
	public void setOwner(Character owner);
	public int getManaCost();
	public void launch();

}
