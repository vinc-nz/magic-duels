/**
 * 
 */
package core.objects;

import core.space.Direction;

/**
 * @author spax
 * An abstract object that can moves throw a direction at a given speed
 *
 */
public abstract class MovingObject extends AbstractObject {
	
	Direction direction;
	
	float speed = 1;
	
	public MovingObject() {
		this.direction = new Direction();
	}
	
	
	public void lookAt(AbstractObject obj) {
		float angle = this.position.angleBetween(obj.position);
		this.direction = new Direction(angle);
	}


	public Direction getDirection() {
		return direction;
	}

	

	public void setDirection(Direction direction) {
		this.direction = direction;
	}


	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	
	public void moveForward() {
		this.moveThrough(this.direction);
	}
	
	public void moveBackward() {
		this.moveThrough(this.direction.opposite());
	}
	
	public void moveLeft() {
		float angle = (float) -Math.PI/2;
		this.moveThrough(this.direction.rotate(angle));
	}
	
	public void moveRight() {
		float angle = (float) Math.PI/2;
		this.moveThrough(this.direction.rotate(angle));
	}
	
	public void moveALeft() {
		Direction d = Direction.yDirection().opposite();
		this.moveThrough(d);
	}
	
	public void moveARight() {
		Direction d = Direction.yDirection();
		this.moveThrough(d);
	}
	
	
	private void moveThrough(Direction d) {
		float x = d.getX() * speed;
		float y = d.getY() * speed;
		this.getPosition().move(x, y);
	}

	

}
