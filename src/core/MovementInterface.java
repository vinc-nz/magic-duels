package core;

/*
 * INTERFACCIA DEL MOVIMENTO
 */

public interface MovementInterface {
	public static final short FORWARD=0; // AVANTI
	public static final short BACKWARD=1; // INDIETRO
	public static final short LEFT=2; // SINISTRA
	public static final short RIGHT=3; // DESTRA
	
	// movimento
	public void move(PlayingCharacter moving, short where, PlayingCharacter other);

}
