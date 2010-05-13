/**
 * 
 */
package core.fight;

import java.lang.reflect.Method;
import java.util.Date;

import core.objects.Spell;
import core.space.World;
import core.spells.TargettingSpell;

/**
 * @author spax
 *
 */
public class Fight {
	
	public static final int ID_P1 = 0; 
	public static final int ID_P2 = 1;
	
	Character[] players = new Character[2];
	
	
	
	
	public boolean running;
	public boolean finished;
	private boolean paused;
	
	
	
	
	public Fight() {
		players[0] = new Character(Character.PLAYER_ONE);
		players[1] = new Character(Character.PLAYER_TWO);
		
		players[0].getPosition().set(-100, 0);
		players[1].getPosition().set(100, 0);
		
		running = false;
		finished = false;
		
	}
	
	
	public Character getPlayer(int id) {
		return players[id];
	}
	
	public Character getEnemy(Character character) {
		return (character == players[0] ? players[1] : players[0]);
	}
	
	
	public void prepareSpell(int playerId, Class<? extends Spell> spell) {
		if (running && !this.getPlayer(playerId).isPreparingSpell()) {
			try {
				Spell s = (Spell) spell.newInstance();
				if (this.getPlayer(playerId).prepareSpell(s))
					this.setSpellParams(playerId, s);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setSpellParams(int playerId, Spell s) {
		
		
		if (s instanceof TargettingSpell) {
			Character enemy = this.getEnemy(this.getPlayer(playerId));
			((TargettingSpell) s).setTarget(enemy.getPosition());
		}
		
	}


	
	
	public void moveCharacter(int playerId, String where) {
		
		if (running && !this.getPlayer(playerId).isPreparingSpell()) {
			String methodName = "moveA" + where.substring(0, 1).toUpperCase() + where.substring(1);
			try {
				Method m = Character.class.getMethod(methodName);
				m.invoke(this.getPlayer(playerId));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void start() {
		running = true; 
	}
	
	public void end() {
		finished = true;
		running = false;
	}
	
	public void update() {
		World.checkCollisions();
		
		players[0].lookAt(players[1]);
		players[1].lookAt(players[0]);
		
		long time = new Date().getTime();
		if (time%1000==0) {
			players[0].mana++;
			players[1].mana++;
		}
	}


	public boolean paused() {
		return paused;
	}
	
	public void togglePause() {
		paused = !paused;
	}

}
