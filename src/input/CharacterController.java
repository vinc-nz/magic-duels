package input;

/*
 * INPUT MAGIA
 */

import core.fight.Fight;
import core.objects.Spell;



/*
 * CONTROLLO DEL CHARACTER
 * in base a trigger ricevuti (da input,rete o ia) si occupa di controllare un PlayingCharacter
 */
public class CharacterController {
	int playerID; // id player
	Fight fight; // partita
	
	public CharacterController(int playerID, Fight fight) {
		super();
		this.playerID = playerID;
		this.fight = fight;
	}
	
	/**
	 * @return the PlayerID
	 */
	public int getPlayerID() {
		return playerID;
	}

	//riceve il trigger e svolge l'azione
	public void performAction(String trigger) {
		int i = trigger.indexOf(">")+1;
		if (trigger.contains("spell")) {
			String name = "core.spells." + trigger.substring(i);
			this.castSpell(name);
		}
		else if (trigger.contains("move")) {
			this.move(trigger.substring(i));
		}
	}
	
	protected void castSpell(String spellName) {
		try {
			Class<? extends Spell> spell = (Class<? extends Spell>) Class.forName(spellName);
			this.castSpell(spell);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * lancia una magia
	 * @param name nome della magia
	 */
	public void castSpell(Class<? extends Spell> spell) {
		fight.prepareSpell(this.playerID, spell);
	}
	

	
	/**
	 * @param where direzione, usare parametri di Trigger
	 */
	public void move(String name) {
		fight.moveCharacter(playerID, name);
	}

	public void switch_pos() {
		// TODO Auto-generated method stub
		
	}
	
}
