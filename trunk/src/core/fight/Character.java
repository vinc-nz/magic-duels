/**
 * 
 */
package core.fight;

import core.objects.AbstractObject;
import core.objects.MovingObject;
import core.objects.Spell;

/**
 * @author spax
 *
 */
public class Character extends MovingObject { 
	
	public static final String PLAYER_ONE = "PlayerOne";
	public static final String PLAYER_TWO = "PlayerTwo";
	
	int life;
	int mana;
	
	Spell preparedSpell = null;
	
	String name;


	public Character(String name) {
		super();
		this.name = name;
		this.life = 100;
		this.mana = 100;
		this.materialize();
	}

	public int getLife() {
		if (life>0)
			return life;
		return 0;
	}

	public int getMana() {
		return mana;
	}


	
	public boolean isPreparingSpell() {
		return preparedSpell!=null;
	}

	public void applyDamage(int points) {
		life -= points;
	}
	
	
	public boolean inDead() {
		return life<=0;
	}
	
	
	public void prepareSpell(Spell s) {
		preparedSpell = s;
		s.setOwner(this);
		mana -= s.getManaCost();
	}
	
	
	public void castSpell() {
		if (preparedSpell!=null) {
			preparedSpell.launch();
			preparedSpell = null;
		}
	}


	/* (non-Javadoc)
	 * @see core.objects.AbstractObject#handleCollision(core.objects.AbstractObject)
	 */
	@Override
	public void handleCollision(AbstractObject other) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
