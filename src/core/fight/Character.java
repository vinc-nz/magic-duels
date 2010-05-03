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
		mana -= s.getManaCost();
	}
	
	
	public Spell castSpell() {
		Spell s = preparedSpell;
		preparedSpell = null;
		return s;
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

}
