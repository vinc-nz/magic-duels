package wiiMoteInput;

/*
 * The class functions as a timer.
 * It stops the spell object that checks
 * if the player is casting a spell,
 * as the time is passed
 */
public class SpellTimer extends Thread {

	protected Spells spell;
	protected int timer;
	
	public SpellTimer(Spells spell, int millisec) {

		this.spell = spell;
		this.timer = millisec;
		
	}

	public void run()
	{
		try {
			Thread.sleep(this.timer);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// IN CASO BISOGNA FERMARE IL THREAD DELLA MAGIA
		//System.out.println("SPELL TIME OUT");
		this.spell.stop = true;
		this.spell.playingMote.isChecking = false;
	}
	
}
