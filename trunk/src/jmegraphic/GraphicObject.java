package jmegraphic;

/*
 * GENERICO OGGETTO DA DISEGNARE
 * ( è un nodo )
 */

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

import core.objects.AbstractObject;
import core.objects.MovingObject;

public abstract class GraphicObject extends SceneElem {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	AbstractObject object;
	boolean moving;
	
	public GraphicObject() {
		super();
	}
	
	public abstract void loadModel();
	
	public GraphicObject(AbstractObject object) {
		super(object.getName());
		this.init(object);
	}



	public void init(AbstractObject object) {
		this.object = object;
		this.moving = false;
		this.addBoundingBox();
		this.applyCullState();
		//this.applyZBufferState();
		this.getLocalTranslation().x = this.object.getPosition().getX();
		this.getLocalTranslation().z = this.object.getPosition().getY();
		if (object instanceof MovingObject)
			this.calculateRotation();
	}
	
	
	
	
	public void updatePosition() {
		Vector3f position = new Vector3f();
		position.x = this.object.getPosition().getX();
		position.z = this.object.getPosition().getY();
		if (!this.getLocalTranslation().equals(position)) {
			this.setLocalTranslation(position);
			if (!moving)
				this.startMoving();
		}
		else if (moving) stopMoving();
	}
	
	public void startMoving() {
		this.moving = true;
	}
	
	public void stopMoving() {
		this.moving = false;
	}
	
	
	public AbstractObject getObject() {
		return object;
	}


	public boolean isMoving() {
		return moving;
	}
	
	public boolean isInGame() {
		return this.object.isInGame();
	}
	
	//ruota all'oggetto in modo che sia rivolto verso le coordinate di point
	protected void lookAt(Vector3f point) {
		Vector3f position = this.getWorldTranslation();
		Vector3f lookAtVector = position.add(point);
		position.addLocal(lookAtVector.negate());
		Quaternion q = new Quaternion();
		q.lookAt(position, new Vector3f(0, 1, 0));
		this.setLocalRotation(q);
	}
	
	
	//coordinate e vettori del core sono inseriti in un array di due elementi
	//la funzione restituisce un Vector3f corrispondente ai valori
	public static Vector3f mapCoords(float[] arrayCoord) {
		if (arrayCoord.length==2)
			return new Vector3f(arrayCoord[0], 0, arrayCoord[1]);
		return new Vector3f(arrayCoord[0],
							arrayCoord[1],
							arrayCoord[2]);	
	}
	
	//calcola la rotazione del modello
	protected void calculateRotation() {
		MovingObject movingObject = (MovingObject) this.object;
		Vector3f direction = new Vector3f();
		direction.x = movingObject.getDirection().getX();
		direction.z = movingObject.getDirection().getY();
		this.lookAt(direction);
	}




	public static GraphicObject fromObject(AbstractObject obj) {
		String name = obj.getClass().getName();
		name = GraphicObject.class.getPackage().getName() + name.substring(name.indexOf('.'));
		try {
			Class go = Class.forName(name);
			GraphicObject instance = (GraphicObject) go.newInstance();
			instance.init(obj);
			return instance;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
