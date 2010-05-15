package jmegraphic;

import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.Renderer;
import com.jme.system.DisplaySystem;


/*
 * GESTORE TELECAMERA
 */

public class CustomCamera {
	Camera camera;
	
	GraphicCharacter focused;  //il personaggio da inquadrare
	GraphicObject target;		//avversario
	Renderer renderer;
	float minDistance;		//distanze della telecamera
	float maxDistance;
	float lastTime;		//ultimo aggiornamento
	
	
	static final int UPTIME=1;  //intervallo di aggiornamento (in secondi)
	
	public CustomCamera(DisplaySystem display) {
		camera = display.getRenderer().createCamera(display.getWidth(), 
													display.getHeight());
		float ratio = (float)display.getWidth()/(float)display.getHeight();
		camera.setFrustumPerspective(45.0f, ratio, 1, 5000);
		camera.update();
		
		renderer = display.getRenderer();
		renderer.setCamera(camera);
		
		
		//da settare in proporzione!!
		this.minDistance = 300;
		this.maxDistance = 600;
		
		
		lastTime = 0;
	}
	
	
	
	
	public void setFocused(GraphicCharacter focused) {
		this.focused = focused;
	}




	protected void setAlongDirection(Vector3f direction) {
		Vector3f location = focused.getLocalTranslation().clone();
		location.addLocal(direction.negate().mult(minDistance/2));
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
		camera.getLocation().y=minDistance/4;
//		this.inclineView(0.08f);
		camera.update();
	}
	
	public void setFromBacksView() {
		Vector3f cameraVec = new Vector3f();
		cameraVec.x = focused.coreCharacter.getDirection().getX();
		cameraVec.z = focused.coreCharacter.getDirection().getY();
		this.setAlongDirection(cameraVec);
		camera.getLocation().y=minDistance/4;
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
//		float time = Timer.getTimer().getTimeInSeconds();
//		if ( time - lastTime > UPTIME) {
			this.setLookAtView();
//			lastTime = time;
//		}
		
	}

}


