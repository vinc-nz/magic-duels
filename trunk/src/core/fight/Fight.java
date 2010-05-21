/**
 * 
 */
package core.fight;

import java.lang.reflect.Method;
import java.util.Date;

import core.objects.Spell;
import core.space.Direction;
import core.space.Position;
import core.space.World;
import core.spells.TargettingSpell;
import game.Error;

/**
 * @author spax
 *
 */
public class Fight {
	
	static final int MANA_INCREASE_FACTOR = 10;
	
	Character[] players;
	
	public boolean running;
	public boolean finished;
	private boolean paused;
	
	public boolean start;
	public boolean move;
	Error fightError;
	
	
	public Fight(int numberOfPlayers) {
		players = new Character[numberOfPlayers];
		
		Position[] positions = this.dispose(numberOfPlayers);
		
		int targets[] = this.defaultTargetting(numberOfPlayers);
		
		for (int i = 0; i < players.length; i++) {
			String name = "Player" + Integer.toString(i+1);
			players[i] = new Character(name);
			players[i].setId(i+1);
			players[i].setPosition(positions[i]);
			players[i].target = targets[i];
		}
		
		
		running = false;
		finished = false;
		start = false;
		move = false;
		paused = false;
		fightError = Error.NONE;
		
	}
	
	
	private int[] defaultTargetting(int numberOfPlayers) {
		int[] targetting = new int[numberOfPlayers];
		int target = 2;
		for (int i = 0; i < targetting.length; i+=2) {
			targetting[i] = target + (i%numberOfPlayers);
			targetting[i+1] = targetting[i] - 1;
		}
		return targetting;
	}


	private Position[] dispose(int numberOfPlayers) {
		int distance = 200;
		float angleOffset = (float)(2*Math.PI/numberOfPlayers);
		Direction d = new Direction();
		Position[] pos = new Position[numberOfPlayers];
		for (int i = 0; i < pos.length; i++) {
			Direction charcaterDirection = d.rotate(angleOffset*i);
			pos[i] = new Position(
					charcaterDirection.getX()*distance, 
					charcaterDirection.getY()*distance);
		}
		return pos;
	}


	public Character getPlayer(int id) {
		return players[id-1];
	}
	
	public Character getEnemy(Character character) {
		return this.getPlayer(character.target);
	}
	
	
	public void prepareSpell(int playerId, Class<? extends Spell> spell) {
		if (isActive() && !this.getPlayer(playerId).isPreparingSpell()) {
			try {
				Spell s = (Spell) spell.newInstance();
				if (this.getPlayer(playerId).prepareSpell(s)){
					this.setSpellParams(playerId, s);
					
					start = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public boolean prepSpeel(int playerId, Class<? extends Spell> spell){
		if(start){
			start = false;
			return true;
		}
		return false;
	}
	
	private void setSpellParams(int playerId, Spell s) {
		if (s instanceof TargettingSpell) {
			Character enemy = this.getEnemy(this.getPlayer(playerId));
			((TargettingSpell) s).setTarget(enemy.getPosition());
		}
		
	}
	
	public void moveCharacter(int playerId, String where) {
		
		if (isActive() && !this.getPlayer(playerId).isPreparingSpell()) {
			String methodName = "move" + where.substring(0, 1).toUpperCase() + where.substring(1);
			try {
				Method m = Character.class.getMethod(methodName);
				m.invoke(this.getPlayer(playerId));
				if(playerId == 2)
					move = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isMove(){
		if(move) {
			move = false;
			return true;
		}
		return false;
	}
	
	public void nextTarget(int playerId) {
		if (isActive()) {
			Character player = this.getPlayer(playerId);
			player.target = player.target%numberOfPlayers()+1;
			if (player.target == playerId)
				this.nextTarget(playerId);
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
		if (isActive()) {
			World.checkCollisions();
			boolean increaseMana = new Date().getTime() % MANA_INCREASE_FACTOR==0;
			
			for (int i = 0; i < players.length; i++) {
				int target = players[i].target;
				players[i].lookAt(this.getPlayer(target));
				if (increaseMana) 
					players[i].mana++;
			}
		}
	}
	
	public int numberOfPlayers() {
		return players.length;
	}


	public boolean paused() {
		return paused;
	}
	
	public synchronized void togglePause() {
		paused = !paused;
		notify();
	}


	public Error getFightError() {
		return fightError;
	}


	public synchronized void notifyError(Error fightError) {
		this.fightError = fightError;
		notify();
	}
	
	public synchronized void checkState() {
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isActive() {
		return running && !paused;
	}


	public void setNames(String[] names) {
		for (int i = 0; i < players.length; i++) {
			players[i].setName(names[i]);
		}
		
	}

}
