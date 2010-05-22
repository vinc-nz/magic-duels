package input;
import game.Event;
import core.fight.Fight;
import core.objects.Spell;
import core.spells.FanBalls;
import core.spells.Fireball;

/**
 * Gives the control of a Character
 * @author spax
 *
 */
public class CharacterController {
	int playerID; // id player
	Fight fight; // partita
	
	public CharacterController(int playerID) {
		super();
		this.playerID = playerID;
		this.fight = Fight.getInstance();
	}
	
	/**
	 * gets the id of the controlled Character
	 * @return the PlayerID
	 */
	public int getPlayerID() {
		return playerID;
	}
	
	/**
	 * the controlled Character will cast the spell specified by name
	 * @param spellName the name of the spell to cast
	 */
	public void castSpell(String spellName) {
		if (!spellName.contains("."))
			spellName = "core.spells." + spellName;
		try {
			Class<? extends Spell> spell = (Class<? extends Spell>) Class.forName(spellName);
			this.castSpell(spell);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * the controlled Character will cast the spell specified by class
	 * @param spell the class of the spell to cast
	 */
	public void castSpell(Class<? extends Spell> spell) {
		fight.prepareSpell(this.playerID, spell);
	}
	

	/**
	 * moves the Character in the specified direction
	 * @param direction where to move
	 */
	public void move(String direction) {
		fight.moveCharacter(playerID, direction);
	}
	
	
	/**
	 * changes character's current target to the next other character
	 */
	public void nextTarget() {
		fight.nextTarget(playerID);
	}
	
	
	/**
	 * pause the game
	 */
	public void pause() {
		fight.togglePause();
	}

	
	/**
	 * 
	 * @return the current instance of Fight
	 */
	public Fight getFight() {
		return fight;
	}
	
	
	/**
	 * notifies an error
	 * @param e the type of error occured
	 */
	public void notifyError(Event e) {
		fight.notifyProblem(e);
	}
	
}
