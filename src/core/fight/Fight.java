/**
 * 
 */
package core.fight;

import java.lang.reflect.Method;
import java.util.Date;

import core.objects.Spell;
import core.space.Position;
import core.space.World;
import core.spells.TargettingSpell;

/**
 * @author spax
 *
 */
public class Fight {
	
	Character[] players = new Character[4];
	
	
	
	
	public boolean running;
	public boolean finished;
	private boolean paused;
	
	
	
	
	public Fight() {
		Position[] positions = {
				new Position(100, 100),
				new Position(-100, 100),
				new Position(100, -100),
				new Position(-100, -100)
		};
		
		for (int i = 0; i < players.length; i++) {
			String name = "Player" + Integer.toString(i+1);
			players[i] = new Character(name);
			players[i].setPosition(positions[i]);
		}
		
		
		running = false;
		finished = false;
		
	}
	
	
	public Character getPlayer(int id) {
		return players[id-1];
	}
	
	public Character getEnemy(Character character) {
		return this.getPlayer(character.target);
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
	
	public void nextTarget(int playerId) {
		Character player = this.getPlayer(playerId);
		player.target = player.target%4+1;
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
		boolean increaseMana = new Date().getTime() % 1000==0;
		
		for (int i = 0; i < players.length; i++) {
			int target = players[i].target;
			players[i].lookAt(this.getPlayer(target));
			if (increaseMana) 
				players[i].mana++;
		}
	}


	public boolean paused() {
		return paused;
	}
	
	public void togglePause() {
		paused = !paused;
	}

}
