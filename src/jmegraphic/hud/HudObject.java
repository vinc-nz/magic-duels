package jmegraphic.hud;

import com.jme.renderer.Renderer;
import com.jme.system.DisplaySystem;

import jmegraphic.GraphicObject;

/*
 * heads on display
 * GENERICO OGGETTO HUD
 */

public abstract class HudObject extends GraphicObject {
	DisplaySystem display;
	float width, height; //per il posizionamento
	
	public static final short POSITION_CENTER = 0;
	public static final short POSITION_BOTTOM = 1;
	public static final short POSITION_BOTTOM_LEFT = 2;
	public static final short POSITION_UP = 3;
	public static final short POSITION_UPPER_RIGHT = 4;

	public HudObject(String name) {
		super(name);
		this.setRenderQueueMode(Renderer.QUEUE_ORTHO);
		this.display = DisplaySystem.getDisplaySystem();
		this.width = 0;
		this.height = 0;
		
	}
	
	
	//setta la posizione dell'oggeto in proporzione al display
	//i valori dei parametri vanno da 0 a 1
	public void setPosition(float widthProp, float heightProp) {
		this.getLocalTranslation().x = (float)display.getWidth() * widthProp;
		this.getLocalTranslation().y = (float)display.getHeight() * heightProp;
	}
	
	
	//setta la posizione in base alle costanti
	//per il posizionamento corretto
	//il centro dell'oggetto dovrebbe essere (width/2,height/2)
	public void setPosition(short hint) {
		switch (hint) {
		case POSITION_BOTTOM:
			this.getLocalTranslation().x = display.getWidth()/2;
			this.getLocalTranslation().y = this.height/2;
			break;
		case POSITION_BOTTOM_LEFT:
			this.getLocalTranslation().x = this.width/2;
			this.getLocalTranslation().y = this.height/2;
			break;
		case POSITION_CENTER:
			this.setPosition(0.5f, 0.5f);
			break;
		case POSITION_UP:
			this.getLocalTranslation().x = display.getWidth()/2;
			this.getLocalTranslation().y = display.getHeight() - this.height/2;
			break;
		case POSITION_UPPER_RIGHT:
			this.getLocalTranslation().x = display.getWidth() - this.width/2;
			this.getLocalTranslation().y = display.getHeight() - this.height/2;
			break;
		default:
			break;
		}
	}

}
