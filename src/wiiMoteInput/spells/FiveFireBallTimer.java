package wiiMoteInput.spells;

import wiiMoteInput.Spells;

/*
 * The class functions as a timer.
 * It stops the spell object that checks
 * if the player is casting a spell,
 * as the time is passed
 */
public class FiveFireBallTimer extends Thread {

	protected FanBalls spell;
	protected int timer;
	
	public FiveFireBallTimer(FanBalls spell, int millisec) {

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
		this.spell.canCheck = true;
	}
	
}
