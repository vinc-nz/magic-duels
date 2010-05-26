package wiiMoteInput;

import input.CharacterController;
import input.InputInterface;
import motej.request.ReportModeRequest;

/*
 * The class manages all the operation that are necessary 
 * as the game is started and until it is finished.
 */
public class PlayingMote extends Thread implements InputInterface
{
	protected CharacterController characterController;
	
	protected PlayerMote playerMote;	
	protected PlayerMoteAccellerometerListener accellerometerListener;
	protected PlayerMoteButtonListener buttonsListener;
	
	protected Spells currentSpell;
	
	protected int currentXvalue;
	protected int currentYvalue;
	protected int currentZvalue;

	public boolean isChecking;
	
	
	public PlayingMote(CharacterController characterController, PlayerMote playerMote) {
		
		this.characterController = characterController;
		this.playerMote = playerMote;

		this.createButtonListener();
		this.createAccellerometerListener();
		this.openAccellerometerListener();
		
	}

	/*
	 * The function creates, if not already exists, a button listener
	 * object and assignes it to the related class variable
	 */
	public void createButtonListener()
	{
		this.buttonsListener = new PlayerMoteButtonListener(this, this.characterController);
		this.playerMote.getMote().addCoreButtonListener(this.buttonsListener);
	}
	
	public void removeButtonListener()
	{
		if(this.buttonsListener != null)
		{
			this.playerMote.getMote().removeCoreButtonListener(this.buttonsListener);
			this.buttonsListener = null;
		}
	}
	
	/*
	 * The function creates, if not already exists, an accellerometer listener
	 * and adds it to the Mote object
	 */
	public void createAccellerometerListener()
	{
		this.accellerometerListener = new PlayerMoteAccellerometerListener(this);
		this.playerMote.getMote().addAccelerometerListener(this.accellerometerListener);
	}
	
	/*
	 * The function remove the accellerometer from the Mote,
	 * the object will be destroyd thanks to gc
	 */
	public void removeAccellerometerListener()
	{
		this.closeAccellerometerListener();
		this.playerMote.getMote().removeAccelerometerListener(this.accellerometerListener);
		this.accellerometerListener = null;
	}
	
	/*
	 * The function returns the accellerometer listened object.
	 */
	public PlayerMoteAccellerometerListener getAccellerometerListener() {
		return accellerometerListener;
	}

	/*
	 * The function starts the accellerometer listening
	 * (it's raccomanded to keep it close if not necessary,
	 * so to increase battery duration)
	 */
	public void openAccellerometerListener()
	{
		if(this.accellerometerListener != null)
			this.playerMote.getMote().setReportMode(ReportModeRequest.DATA_REPORT_0x31);
	}
	
	/*
	 * The functione closes the accellerometer listening
	 */
	public void closeAccellerometerListener()
	{
		if(this.accellerometerListener != null)
			this.playerMote.getMote().setReportMode(ReportModeRequest.DATA_REPORT_0x30);
	}
		
	/*
	 * The function returns the current mote accelleration
	 * alaong the X ass
	 */
	public int getCurrentXvalue() {
		return currentXvalue;
	}

	/*
	 * The function returns the current mote accelleration
	 * alaong the Y ass
	 */
	public int getCurrentYvalue() {
		return currentYvalue;
	}

	/*
	 * The function returns the current mote accelleration
	 * alaong the Z ass
	 */
	public int getCurrentZvalue() {
		return currentZvalue;
	}

	/*
	 * The function sets the current value of the mote accelleration
	 * along the X ass. Note that it's only used by accellerometer listener
	 * object.
	 */
	public void setCurrentXvalue(int currentXvalue) {
		this.currentXvalue = currentXvalue;
	}

	/*
	 * The function sets the current value of the mote accelleration
	 * along the Y ass. Note that it's only used by accellerometer listener
	 * object.
	 */
	public void setCurrentYvalue(int currentYvalue) {
		this.currentYvalue = currentYvalue;
	}

	/*
	 * The function sets the current value of the mote accelleration
	 * along the Z ass. Note that it's only used by accellerometer listener
	 * object.
	 */
	public void setCurrentZvalue(int currentZvalue) {
		this.currentZvalue = currentZvalue;
	}
	
	/*
	 * The function checks if the current position is associated 
	 * to a spell. If true it starts a spell thread that will ensure
	 * that the movement done is a spell, if so the object will
	 * call the CharacterController's funciton to advise the spell has benn cast.
	 * Note that function is called by button listener object 
	 */
	public void check()
	{
		
		this.isChecking = true;
				
		int X = this.currentXvalue;
		int Y = this.currentYvalue;
		int Z = this.currentZvalue;
		
		this.currentSpell = Spells.StartingSpell(X, Y, Z);
		
		if(currentSpell != null)
		{
		
			currentSpell.setPlayingMote(this);
			currentSpell.setCharaterController(characterController);
			currentSpell.start();
			
		} else 
			this.isChecking = false;
		
	}

	@Override
	public int getPlayerID() {
		return this.characterController.getPlayerID();
	}

	
	
}