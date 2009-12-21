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
		if (TriggerParser.isSwitchPosTrigger(trigger)) {
			fight.moveInSpellCastPosition(playerID);
		}
		else if(TriggerParser.isMoveTrigger(trigger)) {
			fight.moveCharacter(playerID, TriggerParser.parseMovement(trigger));
		}
		else if(TriggerParser.isSpellTrigger(trigger)) {
			fight.castSpell(playerID, TriggerParser.getAdvice(trigger));
		}
	}
	
	
	//restituisce un set con tutti i trigger validi
	public Set<String> getActions() {
		Set<String> actions = new HashSet<String>(fight.getPlayer(playerID).getSpellsName());
		
		for (String i:actions) {
			i = TriggerParser.getTriggerFromSpell(i);
		}
		TriggerParser.addMovementsTriggers(actions);
		
		return actions;
	}
}
