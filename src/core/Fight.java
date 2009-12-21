package core;

/*
 * PARTITA
 */

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Fight {
	PlayingCharacter playerOne; // mago 1
	PlayingCharacter playerTwo;	// mago 2
	PlayingCharacter winner; // il vincitore (diverso da null solo a fine scontro)
		
	MovementInterface movement; // gestisce il movimento

	public static final short ID_P1 = 1; // associa un mago al giocatore 1
	public static final short ID_P2 = 2; // associa un mago al giocatore 2
	
	static final float UPTIME = 1; 	// per l'aggiornamento
	float lastTime;					//

	Queue<SpellInstance> spellsInGame;  //coda delle magia in gioco
	Queue<Damage> damages;  //coda dei danni subiti
	
	public boolean running;
	
	
	public Fight(PlayingCharacter p1,PlayingCharacter p2,MovementInterface movement) {
		this.playerOne = p1;
		this.playerTwo = p2;
		this.winner = null;
		this.movement = movement;
			
		this.playerOne.setPosition(-100, 0); // setta la posizione iniziale del mago 1
		this.playerTwo.setPosition(100, 0);  // setta la posizione iniziale del mago 2
		
		this.lastTime = 0;
			
		spellsInGame = new ConcurrentLinkedQueue<SpellInstance>();
		damages = new ConcurrentLinkedQueue<Damage>();
		
		running = false;
	}
			
	// identifica il mago associato al giocatore in base all'id
	public PlayingCharacter getPlayer(short id) {
		switch (id) {
		case ID_P1:
			return playerOne;
		case ID_P2:
			return playerTwo;
		default:
			// TODO eventualmente controllare la validità dell'id
			return null;
		}
	}
	
	//dato un mago restitiusce l'avversario
	public PlayingCharacter getEnemy(PlayingCharacter magician) {
		return (magician==playerOne ? playerTwo : playerOne);
	}

	// lancia la magia in base all'id del giocatore e al nome della stessa
	public void castSpell(short id, String spellName) {
		PlayingCharacter spellCaster = getPlayer(id);
		PlayingCharacter target = null;
		
		if(running && spellCaster.canCast(spellName)) //IF
		{
			SpellInstance spellThread = spellCaster.castSpell(spellName);
				
			if (spellThread instanceof SpellAttack) {// se si tratta di una magia di attacco
				target = getEnemy(spellCaster);
				// l'altro mago è il bersaglio
				( (SpellAttack)spellThread ).setTarget(target,this);
			}
			
			//la magia viene messa in coda e sarà gestita dalla parte grafica
			spellsInGame.add(spellThread);
			
		} //ENDIF
	}

	//data una magia di attacco applica il danno subito al bersaglio
	//successivamente le informazioni sul danno vengono messe in coda
	//per essere rappresentate graficamente
	public synchronized void applyDamage(SpellAttack attack) {
		PlayingCharacter enemy = attack.enemy;
		int damage = enemy.gotSpell(attack.attackPoints);
		damages.add(new Damage(enemy, attack.getSpellName(), damage));
		if (enemy.isDead())
			running = false;
	}
	
	//muove un mago in base all'id del giocatore e ad un identificativo del movimento
	public void moveCharacter(short id, short where) {
		if (running) {
			PlayingCharacter moving = getPlayer(id);
			PlayingCharacter other = getEnemy(moving);
			
			movement.move(moving, where, other);
		}
	}
	
		
	public  SpellInstance pollSpell() {
		return spellsInGame.poll();
	}
	
	public Damage pollDamage() {
		return damages.poll();
	}
	
	//mette il mago in posizione per lanciare una magia, ruotandolo verso l'altro
	public synchronized void moveInSpellCastPosition(short id) {
		PlayingCharacter toRotate = this.getPlayer(id);
		PlayingCharacter enemy = this.getEnemy(toRotate);
		
		//fix dell'angolo in base alla differenza delle coordinate
		toRotate.angle = World.angleBetween(toRotate.x, toRotate.y, enemy.x, enemy.y);
		toRotate.spellCastPosition = true;
	}

	public synchronized void update(float time) {
		if (time-lastTime>UPTIME) {
			playerOne.spellCastPosition = false;
			playerTwo.spellCastPosition = false;
			playerOne.energyPoints++;
			playerTwo.energyPoints++;
			lastTime = time;
		}
	}
	
	public void start() {
		running = true; 
	}
	
	public void end() {
		running = false;
	}
	
}