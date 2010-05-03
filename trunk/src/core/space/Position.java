/**
 * 
 */
package core.space;

/**
 * @author spax
 * Represents a point into space
 *
 */
public class Position {
	
	float x;
	float y;
	
	public Position() {
		this.set(0,0);
	}
	
	
	
	
	public Position(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}


	


	public float getX() {
		return x;
	}




	public float getY() {
		return y;
	}




	/**
	 * set the cords
	 * @param x the x component
	 * @param y the y component
	 */
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * moves the position given a vector
	 * @param x the x component of the vector
	 * @param y the y component of the vector
	 */
	public void move(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	
	/**
	 * calculates the distance between this point and another one
	 * @param other the other point
	 * @return the distance
	 */
	public float distance(Position other) {
		double x = (double) this.x - other.x;
		double y = (double) this.y - other.y;
		double dist = Math.hypot(x, y);
		return (float) dist;
	}
	
	
	/**
	 * calculates the angle between this point and another one
	 * @param other the other point
	 * @return the angle (in radiant)
	 */
	public float angleBetween(Position other) {
		float m = (other.y-this.y)/(other.x-this.x);
		double angle = Math.abs(Math.atan(m));
		
		if (this.y>other.y)
			angle = -angle;
		if (this.x>other.x)
			angle = Math.PI - angle;
		
		return (float)angle;
	}




	public boolean isValid() {
		return World.validPosition(x, y);
	}

}
