package core;

/*
 * IMPLEMENTAZIONE DEL MOVIMENTO
 * movimento semplice lungo gli assi
 */

public class SimpleMovement implements MovementInterface {
	
	
	/*
	 * moving si muove esclisivamente in base al valore di where
	 * 
	 * se where:
	 * FORWARD: verso valori positivi di y
	 * BACKWARD: verso valori negativi di y
	 * LEFT: verso valori negativi di x
	 * RIGHT: verso valori positivi di x
	 * 
	 * other è inutilizzato in quest'implementazione
	 */
	public void move(PlayingCharacter moving, short where, PlayingCharacter other) {
		switch (where) {
			case FORWARD:
				moving.move(0, 1);
				break;
			case BACKWARD:
				moving.move(0, -1);
				break;
			case LEFT:
				moving.move(-1, 0);
				break;
			case RIGHT:
				moving.move(1, 0);
				break;
			default:
				break;
			}
	}

}
