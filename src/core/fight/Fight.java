/**
 * 
 */
package core.fight;

import ia.Monitor;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.Callable;

import core.objects.Spell;
import core.space.Direction;
import core.space.Position;
import core.space.World;
import core.spells.TargettingSpell;

/**
 * @author spax
 *
 */
public class Fight {

	static final int MANA_INCREASE_FACTOR = 10;

	static Fight instance = null;

	Character[] players;

	public boolean running;
	public boolean finished;
	private boolean paused;

	Callable<Void> togglePause;

	Monitor monitor;


	public static Fight create(int numberOfPlayers) {
		instance = new Fight(numberOfPlayers);
		return instance;
	}



	public static Fight getInstance() {
		return instance;
	}



	public static void setInstance(Fight instance) {
		Fight.instance = instance;
	}



	public Fight(int numberOfPlayers) {
		players = new Character[numberOfPlayers];

		Position[] positions = this.dispose(numberOfPlayers);

		int targets[] = this.defaultTargetting(numberOfPlayers);

		for (int i = 0; i < players.length; i++) {
			String name = "Player" + Integer.toString(i+1);
			players[i] = new Character(name);
			players[i].setPosition(positions[i]);
			players[i].target = targets[i];
		}

		running = false;
		finished = false;

		paused = false;

		monitor = new Monitor();
	}


	private int[] defaultTargetting(int numberOfPlayers) {
		int[] targetting = new int[numberOfPlayers];
		for (int i = 0; i < targetting.length; i++) {
			int playerId = i+1;
			int target = (playerId%2!=0 ? playerId+1 : playerId-1);
			if (target>numberOfPlayers)
				target = 1;
			targetting[i] = target;
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
		if (isActive() && this.getPlayer(playerId).isAvailable()) {
			
			try {
				Spell s = (Spell) spell.newInstance();
				this.getPlayer(playerId).prepareSpell(s);
				this.setSpellParams(playerId, s);

				monitor.startSpell();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public boolean prepSpeel(int playerId, Class<? extends Spell> spell){
		if(monitor.start()){
			monitor.startSpellFalse();
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

		if (isActive() && this.getPlayer(playerId).isAvailable()) {
			String methodName = "move" + where.substring(0, 1).toUpperCase() + where.substring(1);
			try {
				Method m = Character.class.getMethod(methodName);
				m.invoke(this.getPlayer(playerId));
				if(playerId == 2)
					monitor.moveCharacter();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isMove(){
		if(monitor.move()) {
			monitor.moveCharacterFalse();
			return true;
		}
		return false;
	}

	public void nextTarget(int playerId) {
		if (isActive()) {
			Character player = this.getPlayer(playerId);
			player.target = player.target%numberOfPlayers()+1;
			if (player.target == playerId || !player.isInGame())
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
		if (allDead())
			end();

		World.checkCollisions();
		boolean increaseMana = new Date().getTime() % MANA_INCREASE_FACTOR==0;

		for (int i = 0; i < players.length; i++) {
			if (!players[i].isDead()) {
				int target = players[i].target;
				players[i].lookAt(this.getPlayer(target));
				if (increaseMana) 
					players[i].mana++;
			}
		}
	}

	private boolean allDead() {
		int alives = 0;
		int winnerId = 0;
		for (int i = 0; i < players.length; i++) {
			if (!players[i].isDead()) {
				alives++;
				winnerId = i+1;
			}
		}
		if (alives==1) {
			getPlayer(winnerId).win();
			return true;
		}
		return false;
	}



	public int numberOfPlayers() {
		return players.length;
	}


	public boolean paused() {
		return paused;
	}

	public void togglePause() {
		paused = !paused;
		if (togglePause!=null)
			try {
				togglePause.call();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}


	public void setPauseToggling(Callable<Void> togglePause) {
		this.togglePause = togglePause;
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