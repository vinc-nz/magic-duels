package core;

/*
 * INFORMAZIONI SUL MANA ( è un thread )
 * 
 * MANA = energia necessaria per il lancio di una magia.
 */

public class ManaAdvice extends SpellInstance {
	
	public ManaAdvice(Spell spell, PlayingCharacter owner) {
		super(spell, owner);
	}
	
	public void run()
	{
		super.run();
		// STUB
		System.out.println("No Mana!!");
	}
}
