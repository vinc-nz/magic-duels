package core;

/*
 * IMPLEMENTAZIONE DEL MOVIMENTO
 * tiene conto di dove si trova il nemico
 * 
 */

public class EnemyDependedMovement implements MovementInterface {
	
	/*
	 * moving si muove in base alla posizione di other
	 * 
	 *  se where:
	 *  FORWARD: verso other
	 *  BACKWARD: indietreggia rispetto other
	 *  LEFT e RIGHT: movimento laterale
	 */
	@Override
	public void move(PlayingCharacter moving, short where,
			         PlayingCharacter other) {
		
		//calcolo del vettore traiettoria
		float[] trajectory = World.calculateTrajectory(moving.x, moving.y, 
													other.x, other.y, moving.speed);
		
		float x=trajectory[0], y=trajectory[1];
		float moveX=0, moveY=0;
		
		switch (where) {
			case FORWARD:
				moveX=x;
				moveY=y;
				break;
			case BACKWARD:
				moveX=-x;
				moveY=-y;
				break;
			case RIGHT:
				moveX=-y;
				moveY=x;
				break;
			case LEFT:
				moveX=y;
				moveY=-x;
				break;
			default:
				// eventualmente gestire il caso in cui 'where' 
				// non rientri nelle costanti
				break;
			}
		
		moving.move(moveX, moveY);
	}
}
