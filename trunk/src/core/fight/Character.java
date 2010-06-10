/**
 * 
 */
package core.fight;

import java.util.concurrent.Callable;

import core.objects.AbstractObject;
import core.objects.MovingObject;
import core.objects.Spell;
import core.space.Direction;

/**
 * @author spax
 *
 */
public class Character extends MovingObject { 
	int life;
	int mana;
	int target;
	boolean enoughMana = true;
	boolean moving = false;
	
	Spell preparedSpell = null;
	//Lock action = new ReentrantLock();
	Callable<Void> deathEvent = null;
	Callable<Void> winEvent = null;
	
	String name;


	public Character(String name) {
		super();
		this.name = name;
		this.life = 100;
		this.mana = 100;
		this.target = 1;
		this.setRadius(25);
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
	
	
	
	public synchronized boolean isAvailable() {
		if (isPreparingSpell() || moving) {
//			try {
//				wait();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			return false;
		}
		return life>0;
	}

	public synchronized void applyDamage(int points) {
		life -= points;
		if (life<=0) {
			setCollideable(false);
			notifyAll();
			if (deathEvent!=null)
				try {
					deathEvent.call();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	
	
	public synchronized void prepareSpell(Spell s) {
		int cost = s.getManaCost();
		if (cost<=mana) {		
			preparedSpell = s;
			s.setOwner(this);
			mana -= cost;
			notify();
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else this.enoughMana = false;
	}
	
	
	
	public boolean notEnoughMana() {
		if (!enoughMana) {
			enoughMana = true;
			return true;
		}
		return false;
	}


	public void castSpell() {
		
		if (preparedSpell!=null) {
			preparedSpell.launch();
			preparedSpell = null;
		}
	}

	@Override
	protected void moveThrough(Direction d) {
		super.moveThrough(d);
		moving = true;
		synchronized (this) {
			notify();
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}
	
	@Override
	public boolean collides(AbstractObject other) {
		if (other instanceof Character)
			return this.getPosition().distance(other.getPosition())<50;
		return super.collides(other);
	}

	

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	public void setName(String name) {
		this.name = name;
	}



	public void atDeath(Callable<Void> deathEvent) {
		this.deathEvent = deathEvent;
	}
	
	
	
	public void atVictory(Callable<Void> winEvent) {
		this.winEvent = winEvent;
	}

	public void win() {
		if (winEvent!=null)
			try {
				winEvent.call();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public boolean isDead() {
		return life<=0;
	}

	public synchronized void checkState() {
		while (!moving && !isPreparingSpell() && !isDead())
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public synchronized void ready() {
		notifyAll();
	}
	
	public void moved() {
		moving = false;
	}

	public boolean isMoving() {
		return moving;
	}
	
	
	
	

}
