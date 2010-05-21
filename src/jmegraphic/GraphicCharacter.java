package jmegraphic;

/*
 * GRAFICA DEL MAGO
 */

import jmegraphic.gamestate.GraphicFight;
import utils.ModelLoader;

import com.jme.image.Texture;
import com.jme.math.Quaternion;
import com.jme.scene.Node;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import com.jme.util.Timer;
import com.jmex.model.animation.JointController;

import core.fight.Character;

public class GraphicCharacter extends GraphicObject {
	
	static final short STAND=0;		//
	static final short WALK=1;		//animazioni
	static final short ATTACK=2;	//
	static final float ANIMATION_UPTIME = 0.5f;
	
	Character coreCharacter; //parte logica corrispondente
	JointController controller;		//per le animazioni
	short currentAnim;				//animazione corrente
	
	boolean preparingSpell;
	float lastTime;

	public GraphicCharacter(Character coreCharacter) {
		super(coreCharacter);
		
		this.coreCharacter = coreCharacter;
		
		this.preparingSpell = false;
		this.lastTime = 0;
	}
	
	
	
	public Character getCoreCharacter() {
		return coreCharacter;
	}



	@Override
	public void update(float tpf) {
		super.update(tpf);
		
		if (!preparingSpell && coreCharacter.isPreparingSpell()) {
			this.preparingSpell = true;
			this.setAnimation(ATTACK);
		}
		
		if (pointOfAnimation(ATTACK,1)) { //se l'animazione di attacco è finita
			this.setAnimation(STAND);
		}
		else if (pointOfAnimation(ATTACK, 0.95f)) {
			coreCharacter.castSpell();
			this.preparingSpell = false;
		}
		
		this.calculateRotation();
		
		//this.controller.update(tpf);
		
	}
	
	
	protected void setAnimation(short x) {
		currentAnim = x;
		switch (x) {
		case STAND:
			controller.setTimes(75, 88);
			break;
			
		case WALK:
			controller.setTimes(2, 14);
			break;
			
		case ATTACK:
			controller.setTimes(112, 126);
			break;

		default:
			break;
		}
		
	}
	
	
	
	//true se l'animazione ha raggiunto il livello indicato da part
	//part va da 0 a 1, 0 inizio, 1 fine
	protected boolean pointOfAnimation(short animation, float part) {
		//TODO controllare che il valore di part sia valido
		return currentAnim == animation
				&& controller.getCurrentTime()+GraphicFight.UPTIME >= (controller.getMaxTime()*part);
	}

	@Override
	public void loadModel() {
		String url = "data/models/dwarf/";
		String modelUrl = url + "dwarf1.ms3d";
		String name = coreCharacter.getName();
		name = name.substring(name.length()-1);
		String textureUrl = url + "dwarf" + name + ".jpg";
		
		Node model = ModelLoader.loadModel(modelUrl,
				"", 1, new Quaternion());

		Texture texture = TextureManager.loadTexture(
		ModelLoader.class.getClassLoader().getResource( textureUrl ),
		Texture.MinificationFilter.Trilinear,
		Texture.MagnificationFilter.Bilinear);
		TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
		ts.setEnabled(true);
		ts.setTexture(texture);
		model.setRenderState( ts );
		
		texture = TextureManager.loadTexture(
		ModelLoader.class.getClassLoader().getResource( url+"axe.jpg" ),
		Texture.MinificationFilter.Trilinear,
		Texture.MagnificationFilter.Bilinear);
		TextureState ts1 = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
		ts1.setEnabled(true);
		ts1.setTexture(texture);
		model.getChild("axe").setRenderState( ts1 );
			
		this.attachChild(model);
		
		this.controller=(JointController)model.getController(0);
		//this.controller.setActive(false);
		this.setAnimation(STAND);
		this.controller.setSpeed(0.3f);
	}

	@Override
	public void startMoving() {
		super.startMoving();
		this.setAnimation(WALK);
		
	}

	@Override
	public void stopMoving() {
		float time = Timer.getTimer().getTimeInSeconds();
		if (lastTime==0)
			lastTime = time;
		else if (time-lastTime>ANIMATION_UPTIME) {
			super.stopMoving();
			this.setAnimation(STAND);
			lastTime = 0;
		}
	}
	
}