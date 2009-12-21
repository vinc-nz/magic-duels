package core;

/*
 * MAGIA DIFENSIVA ( è un thread )
 */

public class SpellDefence extends SpellInstance {
	
	public SpellDefence(Spell spell, PlayingCharacter owner) {
		super(spell, owner);
	}
	
	@Override
	public void run() {	
		super.run();
		try {
			sleep(spell.duration*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		owner.clearProtection(); // elimina la barriera
	}

}
