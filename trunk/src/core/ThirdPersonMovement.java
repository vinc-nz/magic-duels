package core;

/*
 * IMPLEMENTAZIONE DEL MOVIMENTO
 * movimento in terza persona
 */


public class ThirdPersonMovement implements MovementInterface {
	
	static final float DIR_CHANGE = 0.01f;
	
	
	/*
	 * moving si muove esclisivamente in base al valore di where
	 * 
	 * se where:
	 * FORWARD: segue la sua direzione
	 * BACKWARD: verso la direzione opposta alla sua
	 * LEFT: ruota verso sinistra (cambiando la direzione)
	 * RIGHT: ruota verso destra (cambiando la direzione)
	 * 
	 * other è inutilizzato in quest'implementazione
	 */
	@Override
	public void move(PlayingCharacter moving, short where,
			PlayingCharacter other) {
		
		if (where==RIGHT) {
			moving.changeAngle(+DIR_CHANGE);
		}
		else if (where==LEFT) {
			moving.changeAngle(-DIR_CHANGE);
		}
		else {
			float[] direction = moving.getDirection();
			float x = direction[0]*moving.speed;
			float y = direction[1]*moving.speed;
			if (where==BACKWARD) {
				x=-x;
				y=-y;
			}
			moving.move(x, y);
		}
	}

}
