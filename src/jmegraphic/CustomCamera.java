package jmegraphic;

import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.system.DisplaySystem;


/*
 * GESTORE TELECAMERA
 */

public class CustomCamera {
	Camera camera;
	
	GraphicCharacter focused;  //il personaggio da inquadrare
	GraphicObject target;		//avversario
	
	
	static final float DISTANCE = 300;
	
	
	public CustomCamera() {
		camera = DisplaySystem.getDisplaySystem().getRenderer().getCamera();
	}
	
	
	
	
	public void setFocused(GraphicCharacter focused) {
		this.focused = focused;
	}




	protected void setAlongDirection(Vector3f direction) {
		Vector3f location = focused.getLocalTranslation().clone();
		location.addLocal(direction.negate().mult(DISTANCE/2));
		camera.setLocation(location);
		camera.setDirection(direction);
		camera.setUp(new Vector3f(0, 1, 0));
		//camera.setUp(Vector3f.UNIT_Y);
		camera.setLeft(direction.cross(new Vector3f(0, -1, 0)));
		camera.getLocation().addLocal(camera.getLeft().negate().mult(50));
	}
	
	//inquadra entrambi dalle spalle del focused
	public void setLookAtView() {
		Vector3f location = focused.getLocalTranslation();
		Vector3f cameraVec = target.getLocalTranslation().subtract(location).normalize();
		this.setAlongDirection(cameraVec);
		camera.getLocation().y=DISTANCE/4;
//		this.inclineView(0.08f);
		camera.update();
	}
	
	public void setFromBacksView() {
		Vector3f cameraVec = new Vector3f();
		cameraVec.x = focused.coreCharacter.getDirection().getX();
		cameraVec.z = focused.coreCharacter.getDirection().getY();
		this.setAlongDirection(cameraVec);
		camera.getLocation().y=DISTANCE/4;
		camera.update();
	}
	
	//inclina la visuale in basso in base all'angolo
	public void inclineView(float angle) {
		float radius = camera.getLocation().distance(focused.getLocalTranslation());
		float y = radius*FastMath.sin(angle*FastMath.DEG_TO_RAD);
		Vector3f direction = camera.getDirection().add(new Vector3f(0, -y, 0));
		camera.setDirection(direction);
		camera.setUp(direction.cross(camera.getLeft()));
	}
	
	
	
	public void setTarget(GraphicObject target) {
		this.target = target;
	}




	public void update() {
			this.setLookAtView();
	}

}


