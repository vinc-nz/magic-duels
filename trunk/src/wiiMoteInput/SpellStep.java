package wiiMoteInput;

/*
 * The class rapresents one of the different steps
 * necessary to let the alghoritm to understand what
 * spell is the player attempting to cast
 */
public class SpellStep {

	protected int valueX;
	protected int valueY;
	protected int valueZ;
	
	protected int meterX;
	protected int meterY;
	protected int meterZ;
	
	protected int sideX;
	protected int sideY;
	protected int sideZ;
	
	public SpellStep(int valueX, int valueY, int valueZ, int meterX, int meterY, int meterZ, int sideX, int sideY, int sideZ) {
		this.valueX = valueX;
		this.valueY = valueY;
		this.valueZ = valueZ;
		this.meterX = meterX;
		this.meterY = meterY;
		this.meterZ = meterZ;
		this.sideX = sideX;
		this.sideY = sideY;
		this.sideZ = sideZ;
	}

	/*
	 * The function returns thhe value of the X position
	 * related to the object
	 */
	public int getValueX() {
		return valueX;
	}
	
	/*
	 * The function returns thhe value of the Y position
	 * related to the object
	 */
	public int getValueY() {
		return valueY;
	}

	/*
	 * The function returns thhe value of the Z position
	 * related to the object
	 */
	public int getValueZ() {
		return valueZ;
	}

	/*
	 * The function checks if the given position is the 
	 * same of the one stored in the object variables
	 */
	public boolean isAtStep(int currentXvalue, int currentYvalue, int currentZvalue)
	{
		//System.out.println("");
		//System.out.println("STEP FISSATO: " + valueX + ", " + valueY + ", " + valueZ);
		//System.out.println("VALORE CORRENTE: " + currentXvalue + ", " + currentYvalue + ", " + currentZvalue);
		
		if(Spells.about(currentXvalue, this.valueX, this.meterX, this.sideX))
			if(Spells.about(currentYvalue, this.valueY, this.meterY, this.sideY))
				if(Spells.about(currentZvalue, this.valueZ, this.meterZ, this.sideZ))
					return true;
		
		return false;
	}
}