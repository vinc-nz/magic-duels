package jmegraphic;

/*
 * GRAFICA DEL MAGO
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.jme.image.Texture;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.scene.Controller;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.shape.Box;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import com.jme.util.Timer;
import com.jme.util.export.binary.BinaryImporter;
import com.jmex.model.animation.JointController;
import com.jmex.model.converters.MilkToJme;

import core.PlayingCharacter;
import core.SpellInstance;

public class GraphicCharacter extends GraphicObject {
	
	static final short STAND=0;		//
	static final short WALK=1;		//animazioni
	static final short ATTACK=2;	//
	
	PlayingCharacter coreCharacter; //parte logica corrispondente
	JointController controller;		//per le animazioni
	boolean moving;					//se si sta muovendo
	short currentAnim;				//animazione corrente
	SpellInstance spellToCast;		//magia da lanciare, null se non presente
	

	public GraphicCharacter(ModelManager manager, PlayingCharacter coreCharacter) {
		super(coreCharacter.getName(), manager);
		this.coreCharacter = coreCharacter;
		this.moving=false;
		
		// posizione del mago
		Vector3f position = GraphicObject.mapCoords(coreCharacter.getPosition());
		
		this.setLocalTranslation(position); // setta la posizione
		
		this.controller=(JointController)this.getChild(0).getController(0);
		this.controller.setActive(false);
		this.setAnimation(STAND);
		
		this.spellToCast = null;
	}
	
	// aggiornamento
	public void update() {
		Vector3f newPosition = GraphicObject.mapCoords(coreCharacter.getPosition());
		
		if (!moving && this.getLocalTranslation().distance(newPosition)>0.01f) { //si sta muovendo
			moving=true;
			this.setAnimation(WALK); //animazione di movimento
		}
		else if(moving && this.getLocalTranslation().distance(newPosition)<0.01f){
			moving=false;
			this.setAnimation(STAND); //animazione di standing
		}
		
		if (pointOfAnimation(ATTACK,1)) //se l'animazione di attacco è finita
			this.setAnimation(STAND);
		else if (pointOfAnimation(ATTACK, 0.95f) && spellToCast!=null) {
			//se c'è da lanciare una magia
			spellToCast.start();	//lancia la magia
			spellToCast = null;		//dopo il lancio il riferimento viene cancellato
		}
		
		this.calculateRotation();
		
		if (moving)
			this.setLocalTranslation(newPosition);
		
		this.controller.update(Timer.getTimer().getTimePerFrame());
		
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
	
	//calcola la rotazione del modello
	protected void calculateRotation() {
		float[] direction = coreCharacter.getDirection();
		this.lookAt(GraphicObject.mapCoords(direction));
	}

	//lancio di una magia (a livello grafico)
	public void castGraphicSpell(SpellInstance spell) {
		this.setAnimation(ATTACK);
		moving=false;
		spellToCast = spell;
	}
	
	//true se l'animazione ha raggiunto il livello indicato da part
	//part va da 0 a 1, 0 inizio, 1 fine
	protected boolean pointOfAnimation(short animation, float part) {
		//TODO controllare che il valore di part sia valido
		return currentAnim == animation
				&& controller.getCurrentTime()+0.01 >= (controller.getMaxTime()*part);
	}
	
}