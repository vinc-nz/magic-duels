/**
 * 
 */
package core.space;

/**
 * @author spax
 * A 2-component unit vector that represents an orientation into space
 */
public class Direction {
	
	float angle;
	
	
	
	public Direction() {
		this.angle = 0;
	}
	
	
	public Direction(float angle) {
		super();
		this.angle = angle;
		this.simplyAngle();
	}
	
	
	public float getX() {
		return (float)Math.cos((double)angle);
	}
	public float getY() {
		return (float)Math.sin((double)angle);
	}
	
	
	/**
	 * gets the opposite of this vector
	 * @return the opposite of this vector
	 */
	public Direction opposite() {
		return this.rotate((float) Math.PI);
	}
	
	public Direction rotate(float angle) {
		return new Direction((float)(this.angle+angle));
	}
	
	
	/**
	 * creates a new vector of the direction between two points
	 * @param from the source position
	 * @param where the position to look at
	 * @return the vector
	 */
	public static Direction fromPoints(Position from, Position where) {
		float angle = from.angleBetween(where);
		return new Direction(angle);
	}
	
	
	public static Direction xDirection() {
		return new Direction();
	}
	
	public static Direction yDirection() {
		float angle = (float) (Math.PI/2);
		return new Direction(angle);
	}
	
	private void simplyAngle() {
		while (this.angle>2*Math.PI)
			this.angle-=2*Math.PI;
	}

}
