package wiiMoteInput;

import input.CharacterController;
import wiiMoteInput.spells.FanBalls;
import wiiMoteInput.spells.FireBallSpell;
import wiiMoteInput.spells.NewSpell;

/*
 * The function manages all the spells that a player could
 * cast by using the wii mote controller
 */
abstract public class Spells extends Thread {

	public static final int ACCURATE = 0;
	public static final int CLOSE = 1;
	public static final int GENEROUS = 2;
	public static final int IRRILEVANT = 3;
	
	public static final int RANGE_ACCURATE =  10;
	public static final int RANGE_CLOSE = 20;
	public static final int RANGE_GENEROUS = 30;
	public static final int RANGE_IRRILEVANT = 150;
	
	public static final int SX = 0;
	public static final int DX = 1;
	public static final int AMB = 2;
	
	protected static final SpellStep FIREBALL_START = new SpellStep(125, 125, 100, CLOSE, CLOSE, CLOSE, AMB, AMB, AMB);
	protected static final SpellStep FIVEFIREBALLS_START = new SpellStep(125, 125, 150, CLOSE, CLOSE, CLOSE, AMB, AMB, AMB);
	protected static final SpellStep NEWSPELL_START = new SpellStep(125, 145, 140, CLOSE, CLOSE, CLOSE, AMB, AMB, AMB);
	
	public static final int TIMEOUT = 1500;
	
	protected SpellTimer timer;
	protected boolean stop;

	protected CharacterController characterController;
	protected PlayingMote playingMote;
	protected String currentSpellName;
	
	protected int mediaX;
	protected int mediaY;
	protected int mediaZ;
		
	protected abstract SpellStep getEndStep();
	protected abstract boolean isSpell();
	
	public String toString() {
		System.out.println("TO STRING: " + currentSpellName);
		return this.currentSpellName;
	}
		
	public Spells(String currentSpellName) {
		this.currentSpellName = currentSpellName;
	}
	/*
	 * 
	 */
	public void setPlayingMote(PlayingMote playingMote) {
		this.playingMote = playingMote;
	}
	
	public void setCharaterController(CharacterController characterController)
	{
		this.characterController = characterController;
	}
	
	/*
	 * The function checks if the given position is the end of a spell movment
	 */
	public boolean isEndPosition(int currentXvalue, int currentYvalue, int currentZvalue)
	{
		if(this.getEndStep().isAtStep(currentXvalue, currentYvalue, currentZvalue))
		{
			System.out.println("POSIZIONE FINALE!");
			return true;			
		}
		
		return false;
	}
	
	/*
	 * The function checks if the given position is the start one of a 
	 * spell stored in the class, if so it returns the spell, null otherwise
	 */
	public static Spells StartingSpell(int X, int Y, int Z)
	{

		System.out.println("VALORE CORRENTE: " + X + ", " + Y + ", " + Z);
		
		if(FIREBALL_START.isAtStep(X, Y, Z))
		{
			System.out.println("FIREBALLS");
			System.out.println("VALORE CORRENTE: " + X + ", " + Y + ", " + Z);
			return new FireBallSpell();
		}
		else 
			if(FIVEFIREBALLS_START.isAtStep(X, Y, Z))
			{
				System.out.println("FIREBALLS");
				System.out.println("VALORE CORRENTE: " + X + ", " + Y + ", " + Z);
				return new FanBalls();
			}
		else 
			if(NEWSPELL_START.isAtStep(X, Y, Z))
			{
				System.out.println("FIREBALLS");
				System.out.println("VALORE CORRENTE: " + X + ", " + Y + ", " + Z);
				return new NewSpell();
			}
		return null;
		
	}
	
	protected void castSpell()
	{
		characterController.castSpell(this.toString());
		System.out.println("LANCIATO!");
	}
	
	/*
	 * RICEVE IL VALORE CORRENTE, IL VALORE A CUI AVVICINARSI E DUE PARAMETRI:
	 * METER CORRISPONDE AL RAGGIO ENTRO IL QUALE IL VALORE CORRENTE PUO' RIENTRARE;
	 * SIDE PUO' ESSERE: SINISTRO, DESTRO O AMBIDESTRO:
	 * SE SINISTRO ALLORA IL VALORE CORRENTE E' SUFFICIENTE CHE SIA PIU' PICCOLO DEL
	 * VALORE DA CHECCARE,
	 * SE DESTRO ALLORA IL VALORE CORRENTE E' SUFFICIENTE CHE SIA PIU' GRANDE DEL
	 * VALORE DA CHECCARE,
	 * SE AMBIDESTRO DEVE ESSERE COMPRESO NEL RANGE.
	 * NATURALMENTE SE SI TRATTA DI DESTRO O SINISTRO IL RANGE VIENE IGNORATO.
	 */
	public static boolean about(int val, int valueToCheck, int meter, int side)
	{
		int range = 0;
		
		switch (meter) {
		case ACCURATE:
			range = RANGE_ACCURATE;
			break;
		case CLOSE:
			range = RANGE_CLOSE;
			break;
		case GENEROUS:
			range = RANGE_GENEROUS;
			break;
		case IRRILEVANT:
			range = RANGE_IRRILEVANT;
			break;
		}
		
		switch(side)
		{
			case SX:
				if( val < (valueToCheck) )
					return true;
				break;
			case DX:
				if( val > (valueToCheck) )
					return true;
				break;
			case AMB:
				if( (val > (valueToCheck - range) ) && (val < (valueToCheck + range) ) )
					return true;
				break;
		}
		
		return false;
	}
	
}
