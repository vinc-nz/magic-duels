package core;

/*
 * GENERICA MAGIA LANCIATA (istanza)
 */

public abstract class SpellInstance extends Thread {
	Spell spell;	//i dati della magia
	PlayingCharacter owner;		//il mago che l'ha lanciata
		
	
	public SpellInstance(Spell spell, PlayingCharacter owner) {
		super();
		this.spell = spell;
		this.owner = owner;
	}


	public PlayingCharacter getOwner() {
		return owner;
	}
	
	
	public String getSpellName() {
		return spell.name;
	}
	
	@Override
	public void run() {
		super.run();
		owner.spellInWaiting = false;  //una volta lanciata notifica al character
	}
}
