package wiiMoteInput.spells;

import wiiMoteInput.SpellStep;
import wiiMoteInput.SpellTimer;
import wiiMoteInput.Spells;

public class Protection extends Spells {
		
	public final static SpellStep END = new SpellStep(120, 100, 130, Spells.CLOSE, Spells.CLOSE, Spells.IRRILEVANT, Spells.AMB, Spells.AMB, Spells.DX);

	public Protection() {
		super("Protection");
	}
	
	public void run()
	{
		System.out.println("ENTRO NELLA RUN DELLA MAGIA NEW SPELL");
	
		this.mediaX = 0;
		this.mediaY = 0;
		this.mediaZ = 0;
	
		this.timer = new SpellTimer(this, TIMEOUT);
		this.timer.start();
		
		this.stop = false;
				
		System.out.println("DEVO PRENDERE X Y Z");
	
		int X = this.playingMote.getCurrentXvalue();
		int Y = this.playingMote.getCurrentYvalue();
		int Z = this.playingMote.getCurrentZvalue();
		
		System.out.println("PRIMA DEL WHILE: " + X + " "+ Y + " "+ Z);

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
			
			/* CHECK BUG PRESENCE */
			if(mediaX == 0 || mediaY == 0 || mediaZ == 0)
				System.out.println("ATTENZIONE! TUTTI ZERI NELLE ACCELERAZIONI!");
		}
		
		// Se � scaduto il tempo:
		if(this.stop && !ItIsSpell) 
		{
			System.out.println("NON E' LA MAGIA (timeout)");
		}		
		else if(this.isSpell())
		{
			System.out.println("E' LA MAGIA !!!");
			System.out.println("MEDIA: " + mediaX + " : " + mediaY + " : " + mediaZ);
		}
		else
		{
			System.out.println("NON E' LA MAGIA !!!");
			System.out.println("MEDIA: " + mediaX + " : " + mediaY + " : " + mediaZ);
		}
		
		this.playingMote.isChecking = false;
		
	}
	
	@Override
	protected SpellStep getEndStep() {
		return Protection.END;
	}
	
	@Override
	protected boolean isSpell() {
		System.out.println("ENTRO IN ISSPELL DI NEW SPELL");
		/*if(about(super.mediaX, 125, CLOSE, AMB) && about(super.mediaY, 120, CLOSE, AMB))
			return true;*/
		if(about(super.mediaY, 70, IRRILEVANT, DX) && about(super.mediaZ, 80, IRRILEVANT, DX))
		{
			System.out.println("E' LA MAGIA !!!");
			super.castSpell();
			return true;
		}
		
		System.out.println("NON E' LA MAGIA !!!");
		return false;
	}

}