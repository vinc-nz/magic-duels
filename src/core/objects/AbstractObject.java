/**
 * 
 */
package core.objects;

import core.space.Position;
import core.space.World;

/**
 * @author spax
 * A generic object that has a position and handles collisions
 */
public abstract class AbstractObject {
	
	Position position = new Position();
	
	float radius = 1;
	
	private boolean inGame = false;

	
	/**
	 * gets object's position
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}
	
	
	
	
	/**
	 * the abstract object is represented as a circle with its position as center and a radius.
	 * this convention is used in the default collides() method
	 * @param radius the value to set as radius
	 */
	public void setRadius(float radius) {
		this.radius = radius;
	}





	/**
	 * check if there's a collision between this object and another one
	 * @param other the other object
	 * @return true if the objects collide
	 */
	public boolean collides(AbstractObject other) {
		return this.position.distance(other.position) <= this.radius + other.radius;
	}
	
	
	/**
	 * this method will be executed when a collision between the two objects is detected
	 * @param other the object that collides with this
	 */
	public abstract void handleCollision(AbstractObject other);
	
	
	public abstract String getName();



	public void materialize() {
		this.inGame = true;
		World.addObject(this);
	}
	
	public void destroy() {
		this.inGame = false;
	}

	public boolean isInGame() {
		return inGame;
	}




	public void setPosition(Position position) {
		this.position.set(position.getX(), position.getY());
	}
	
	
	public abstract void update();

}
