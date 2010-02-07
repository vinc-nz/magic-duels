package wiiMoteInput.spells;

import main.java.motej.request.ReportModeRequest;
import wiiMoteInput.SpellStep;
import wiiMoteInput.SpellTimer;
import wiiMoteInput.Spells;

public class FiveFireBallsSpell extends Spells {

	public final static SpellStep END = new SpellStep(130, 130, 165, CLOSE, CLOSE, CLOSE, AMB, AMB, AMB);

	public void run()
	{
		System.out.println("ENTRO NELLA RUN DELLA MAGIA FIVE FIRE BALLS");
	
		this.mediaX = 0;
		this.mediaY = 0;
		this.mediaZ = 0;
		
		this.timer = new SpellTimer(this, TIMEOUT);
		this.timer.start();
		
		this.stop = false;
				
		int X = this.playingMote.getCurrentXvalue();
		int Y = this.playingMote.getCurrentYvalue();
		int Z = this.playingMote.getCurrentZvalue();
		
		boolean ItIsSpell = false;
		
		while( (!ItIsSpell && !this.stop) || (mediaX == 0 || mediaY == 0 || mediaZ == 0) )
		{
			ItIsSpell = this.isEndPosition(X, Y, Z);

			//System.out.println(X + " : " + Y + " : " + Z);
			
			this.mediaX = (this.mediaX + X) / 2;
			this.mediaY = (this.mediaY + Y) / 2;
			this.mediaZ = (this.mediaZ + Z) / 2;
			
			X = this.playingMote.getCurrentXvalue();
			Y = this.playingMote.getCurrentYvalue();
			Z = this.playingMote.getCurrentZvalue();
		}
		
		// Se è scaduto il tempo:
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
		System.out.println("ENTRO IN ISSPELL DI FIVE BALLS");
		if(super.about(super.mediaZ, 170, IRRILEVANT, DX))
		{
			System.out.println("E' LA MAGIA !!!");
			return true;
		}
		
		System.out.println("NON E' LA MAGIA !!!");
		return false;
	}
}
