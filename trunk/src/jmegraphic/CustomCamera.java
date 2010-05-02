package jmegraphic;

import com.jme.input.ChaseCamera;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.Renderer;
import com.jme.system.DisplaySystem;
import com.jme.util.Timer;


/*
 * GESTORE TELECAMERA
 */

public class CustomCamera {
	Camera camera;
	ChaseCamera chaser;	//per la telecamera a inseguimento
	GraphicCharacter focused;  //il personaggio da inquadrare
	GraphicCharacter enemy;		//avversario
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
	
	
	//imposta i personaggi da inquadrare
	public void setCharacters(GraphicCharacter focused, GraphicCharacter enemy) {
		this.focused = focused;
		this.enemy = enemy;
		
		//setup chase camera
		chaser = new ChaseCamera(camera, focused);
		chaser.setMinDistance(minDistance);
		chaser.setMaxDistance(maxDistance);
		
		this.setFromBacksView();
	}
	
	
	//camera a inseguimento
	public void setChaserView() {
		chaser.update(0);
	}
	
	protected void setAlongDirection(Vector3f direction) {
		Vector3f location = focused.getLocalTranslation().clone();
		location.addLocal(direction.negate().mult(minDistance));
		camera.setLocation(location);
		camera.setDirection(direction);
		camera.setUp(new Vector3f(0, 1, 0));
		//camera.setUp(Vector3f.UNIT_Y);
		camera.setLeft(direction.cross(new Vector3f(0, -1, 0)));
	}
	
	//inquadra entrambi dalle spalle del focused
	public void setLookAtEnemyView() {
		Vector3f location = focused.getLocalTranslation();
		Vector3f cameraVec = enemy.getLocalTranslation().subtract(location).normalize();
		this.setAlongDirection(cameraVec);
		camera.getLocation().y=minDistance;
		this.inclineView(0.08f);
		camera.update();
	}
	
	public void setFromBacksView() {
		float[] direction = focused.coreCharacter.getDirection();
		Vector3f cameraVec = new Vector3f(direction[0], 0, direction[1]);
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
	
	public void update(Timer timer) {
		//TODO eventualmente lanciare un'eccezione se i personaggi non sono stati settati
		if (timer.getTimeInSeconds()- lastTime > UPTIME) {
//			if (focused.coreCharacter.isInSpellCastPosition())
//				this.setLookAtEnemyView();
//			else
//				//chaser.update(timer.getTimePerFrame());
//				this.setFromBacksView();
			this.setLookAtEnemyView();
		}
	}

}


