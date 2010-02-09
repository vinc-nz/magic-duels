package input;

/*
 * INPUT MAGIA
 */

import java.util.HashSet;
import java.util.Set;

import core.Fight;

/*
 * CONTROLLO DEL CHARACTER
 * in base a trigger ricevuti (da input,rete o ia) si occupa di controllare un PlayingCharacter
 */
public class CharacterController {
	short playerID; // id player
	Fight fight; // partita
	
	public CharacterController(short playerID, Fight fight) {
		super();
		this.playerID = playerID;
		this.fight = fight;
	}
	
	
	//riceve il trigger e svolge l'azione
	public void performAction(String trigger) {
		if (Trigger.isSwitchPosTrigger(trigger)) {
			fight.moveInSpellCastPosition(playerID);
		}
		else if(Trigger.isMoveTrigger(trigger)) {
			fight.moveCharacter(playerID, Trigger.parseMovement(trigger));
		}
		else if(Trigger.isSpellTrigger(trigger)) {
			fight.castSpell(playerID, Trigger.getAdvice(trigger));
		}
	}
	
	
	/**
	 * lancia una magia
	 * @param name nome della magia
	 */
	public void castSpell(String name) {
		this.performAction(Trigger.getTriggerFromSpell(name));
	}
	
	/**
	 * sposta il character in posizione di attacco
	 */
	public void switch_pos() {
		this.performAction("switch_pos>");
	}
	
	/**
	 * @param where direzione, usare parametri di Trigger
	 */
	public void move(String name) {
		this.performAction("move>"+name);
	}
	
	
	//restituisce un set con tutti i trigger validi
	public Set<String> getActions() {
		Set<String> actions = new HashSet<String>(fight.getPlayer(playerID).getSpellsName());
		
		for (String i:actions) {
			i = Trigger.getTriggerFromSpell(i);
		}
		Trigger.addMovementsTriggers(actions);
		
		return actions;
	}
}
