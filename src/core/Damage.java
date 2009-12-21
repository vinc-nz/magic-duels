package core;

/*
 * DANNO
 */

public class Damage {
	PlayingCharacter target;  //il personaggio che lo subisce
	String spellName;		  //il nome della magia che l'ha causato
	int damage;				  //l'ammontare del danno

	public Damage(PlayingCharacter target, String spellName, int damage) {
		this.target = target;
		this.spellName = spellName;
		this.damage = damage;
	}
	
	public PlayingCharacter getTarget() {
		return target;
	}
	
	public String getSpellName() {
		return spellName;
	}
	
	public int getDamage() {
		return damage;
	}
	
}
