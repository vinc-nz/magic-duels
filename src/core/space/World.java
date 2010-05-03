/**
 * 
 */
package core.space;

import java.util.LinkedList;

import core.objects.AbstractObject;

/**
 * @author spax
 *
 */
public class World {
	
	public static final int centerDistance=500;
	
	static LinkedList<AbstractObject> objects = new LinkedList<AbstractObject>();
	
	public static boolean validPosition(float x,float y) {
		return (Math.abs(x)<centerDistance && Math.abs(y)<centerDistance);
	}
	
	public static void addObject(AbstractObject obj) {
		objects.add(obj);
	}
	
	public static void checkCollisions() {
		LinkedList<AbstractObject> newList = new LinkedList<AbstractObject>();
		for (AbstractObject abstractObject : objects) {
			if (abstractObject.isInGame())
				newList.add(abstractObject);
		}
		
		objects = newList;
		
		for (AbstractObject i : objects) {
			for (AbstractObject j : objects) {
				if (!i.getPosition().isValid())
					i.handleCollision(null);
				if (i != j && i.collides(j))
					i.handleCollision(j);
			}
			
		}
		
	}

}
