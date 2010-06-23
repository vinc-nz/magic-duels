package wiiMoteInput.spells;

import wiiMoteInput.SpellStep;
import wiiMoteInput.SpellTimer;
import wiiMoteInput.Spells;

public class FanBalls extends Spells {

	public final static SpellStep END = new SpellStep(130, 130, 165, CLOSE, CLOSE, CLOSE, AMB, AMB, AMB);
	
	public boolean canCheck;
	private FanBallsTimer canCheckTimer;
	
	public FanBalls() {
		super("FanBalls");
	}

	public void run()
	{
		System.out.println("ENTRO NELLA RUN DELLA MAGIA FAN BALLS");
	
		super.mediaX = 0;
		super.mediaY = 0;
		super.mediaZ = 0;
		
		this.timer = new SpellTimer(this, TIMEOUT);
		this.timer.start();
		
		this.stop = false;
		this.canCheck = false;
		
		this.canCheckTimer = new FanBallsTimer(this, 101);
		this.canCheckTimer.start();
		
		System.out.println("DEVO PRENDERE X Y Z");
		
		int X = this.playingMote.getCurrentXvalue();
		int Y = this.playingMote.getCurrentYvalue();
		int Z = this.playingMote.getCurrentZvalue();
		
		System.out.println("PRIMA DEL WHILE: " + X + " "+ Y + " "+ Z);
		
		boolean ItIsSpell = false;
		
		while( (!this.canCheck) || (!ItIsSpell && !this.stop) || (mediaX == 0 || mediaY == 0 || mediaZ == 0))
		{
			ItIsSpell = this.isEndPosition(X, Y, Z);
			
			super.mediaX = (super.mediaX + X) / 2;
			super.mediaY = (super.mediaY + Y) / 2;
			super.mediaZ = (super.mediaZ + Z) / 2;
			
			X = this.playingMote.getCurrentXvalue();
			Y = this.playingMote.getCurrentYvalue();
			Z = this.playingMote.getCurrentZvalue();
			
			/* CHECK BUG PRESENCE */
			if(mediaX == 0 || mediaY == 0 || mediaZ == 0)
				System.out.println("ATTENZIONE! TUTTI ZERI NELLE ACCELERAZIONI!");
			
		}
		
		if(this.stop && !ItIsSpell) 
		{
			System.out.println("NON E' LA MAGIA (timeout)");	
			return;
		}
		
		if(this.isSpell())
		{
			System.out.println("E' LA MAGIA !!!");
			System.out.println("MEDIA: " + mediaX + " : " + mediaY + " : " + mediaZ);
		}
		else
		{
			System.out.println("NON E' LA MAGIA !!!");
			System.out.println("MEDIA: " + mediaX + " : " + mediaY + " : " + mediaZ);
		}
		
	}
	
	@Override
	protected SpellStep getEndStep() {
		return this.END;
	}

	@Override
	protected boolean isSpell() {
		System.out.println("ENTRO IN ISSPELL DI FAN BALLS");
		if(super.about(super.mediaX, 120, CLOSE, AMB) && super.about(super.mediaY, 130, CLOSE, AMB) && super.about(super.mediaZ, 140, IRRILEVANT, DX))
		{
			System.out.println("E' LA MAGIA !!!");
			super.castSpell();
			return true;
		}
		
		System.out.println("NON E' LA MAGIA !!!");
		return false;
	}
}
