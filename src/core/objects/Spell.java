/**
 * 
 */
package core.objects;

/**
 * @author spax
 *
 */
public interface Spell {
	
	public int playerId = 0;
	
	public int getManaCost();
	public void launch();
	public void update();

}
