package input;

import java.util.Set;

import core.MovementInterface;

/* parser dei trigger per il CharacterController
 * un trigger è costituito da un identificatore, un separatore e un advice, eventualmente nullo
 * l'identificatore determina il tipo di azione da compiere
 * l'advice da ulteriori specifiche su di essa
 * 
 * es:
 * move>forward
 * 
 * move è l'identificatore, indica che si tratta di un'azione di movimento
 * forward è l'advice, indica che si tratta di un movimento in avanti
 */

public class TriggerParser {
	static final String MOV_ADVICE_FORWARD = "forward";
	static final String MOV_ADVICE_BACKWARD = "backward";
	static final String MOV_ADVICE_LEFT = "left";
	static final String MOV_ADVICE_RIGHT = "right";
	
	//se si tratta di un trigger di movimento
	public static boolean isMoveTrigger(String trigger) {
		return trigger.contains("move");
	}
	
	
	//se si tratta del lancio di una magia
	public static boolean isSpellTrigger(String trigger) {
		return trigger.contains("spell");
	}
	
	//per il cambio di posizione
	public static boolean isSwitchPosTrigger(String trigger) {
		return trigger.contains("switch_pos");
	}
	
	public static String getAdvice(String trigger) {
		return trigger.substring(trigger.indexOf(">")+1);
	}
	
	//data il nome della magia restituisce il trigger che permette di lanciarla
	public static String getTriggerFromSpell(String trigger) {
		return "spell>"+trigger;
	}
	
	
	//il trigger dev'essere di movimento
	//in base all'advice restituisce il parametro da passare al core
	public static short parseMovement(String trigger) {
		String advice = getAdvice(trigger);
		short where = 0;
		if (advice.equals(MOV_ADVICE_FORWARD))
			where=MovementInterface.FORWARD;
		else if (advice.equals(MOV_ADVICE_BACKWARD))
			where=MovementInterface.BACKWARD;
		else if (advice.equals(MOV_ADVICE_LEFT))
			where=MovementInterface.LEFT;
		else if (advice.equals(MOV_ADVICE_RIGHT))
			where=MovementInterface.RIGHT;
		
		return where;
	}

	//aggiunge i trigger di movimento al set ricevuto
	public static void addMovementsTriggers(Set<String> actions) {
		String[] advices = {MOV_ADVICE_BACKWARD,
							MOV_ADVICE_FORWARD,
							MOV_ADVICE_LEFT,
							MOV_ADVICE_RIGHT };
		
		for (int i = 0; i < advices.length; i++) {
			actions.add("move>"+advices[i]);
		}
		
	}

}
