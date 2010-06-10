/**
 * 
 */
package core.space;

import java.util.LinkedList;
import java.util.List;

import core.objects.AbstractObject;

/**
 * @author spax
 *
 */
public class World {
	
	public static final int centerDistance=500;
	
	public static LinkedList<AbstractObject> objects = new LinkedList<AbstractObject>();
	
	public static boolean validPosition(float x,float y) {
		return (Math.abs(x)<centerDistance && Math.abs(y)<centerDistance);
	}
	
	public static void addObject(AbstractObject obj) {
		objects.add(obj);
	}
	
	public static List<AbstractObject> getObjects() {
		return new LinkedList<AbstractObject>(objects);
	}
	
	public static void updateObjects() {
		LinkedList<AbstractObject> newList = new LinkedList<AbstractObject>();
		for (AbstractObject abstractObject : objects) {
			if (abstractObject.isInGame())
				abstractObject.update();
			if (!abstractObject.isToRevome())
				newList.add(abstractObject);
		}
		
		objects = newList;
		
	}
	
	public static void checkCollisions(AbstractObject collideable) {
		if (!collideable.getPosition().isValid())
			collideable.handleCollision(null);
		for (AbstractObject obj : objects) {
			if (collideable != obj && collideable.collides(obj))
				collideable.handleCollision(obj);
		}
	}

}
