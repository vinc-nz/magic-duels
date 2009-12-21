package jmegraphic;

/*
 * GENERICO OGGETTO DA DISEGNARE
 * ( è un nodo )
 */

import com.jme.bounding.BoundingBox;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.CullState;
import com.jme.scene.state.ZBufferState;
import com.jme.scene.state.CullState.Face;
import com.jme.system.DisplaySystem;

public abstract class GraphicObject extends Node {
	
	public GraphicObject(String name) {
		super(name);
	}
	
	public GraphicObject(String name, ModelManager manager) {
		super(name);
		Node model = manager.get(name); //richiede al ModelManager un modello identificato da name
		model.setModelBound(new BoundingBox());
		model.updateModelBound();
		this.attachChild(model);
		this.applyCullState();
		this.applyZBufferState();
	}
	
	// aggiorna la posizione
	protected void updatePosition(Vector3f newPosition) {
		if (!this.getLocalTranslation().equals(newPosition)) {
			this.setLocalTranslation(newPosition);
		}
	}
	
	
	//applica all'oggetto un CullState di default
	public void applyCullState() {
		CullState cs = DisplaySystem.getDisplaySystem().getRenderer().createCullState();
        cs.setCullFace(Face.Back);
        cs.setEnabled(true);
        this.setRenderState(cs);
	}
	
	//applica all'oggetto un BlendState di default
	public void applyBlendState() {
		BlendState bs = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
		bs.setBlendEnabled(true);
		bs.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
        bs.setDestinationFunction(BlendState.DestinationFunction.One);
        bs.setTestEnabled(true);
        bs.setTestFunction(BlendState.TestFunction.GreaterThan);
        bs.setEnabled(true);
        this.setRenderState(bs);
	}
	
	
	//applica all'oggetto uno ZBufferState di default
	public void applyZBufferState() {
		ZBufferState zs = DisplaySystem.getDisplaySystem().getRenderer().createZBufferState();
	    zs.setEnabled(true);
	    zs.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);
	    this.setRenderState(zs);
	}
	
	// verifica se l'oggetto se è da rimuovere.
	//se è necessario usarlo fare l'override
	public boolean toRemove() {
		return false;
	}
	
	//vero se un oggetto in precedenza invisibile diviene visibile 
	//se è necessario usarlo fare l'override
	public boolean becomeVisible() {
		return false;
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
	
	public abstract void update();
	
	
	//coordinate e vettori del core sono inseriti in un array di due elementi
	//la funzione restituisce un Vector3f corrispondente ai valori
	public static Vector3f mapCoords(float[] arrayCoord) {
		if (arrayCoord.length==2)
			return new Vector3f(arrayCoord[0], 0, arrayCoord[1]);
		return new Vector3f(arrayCoord[0],
							arrayCoord[1],
							arrayCoord[2]);	
	}

}
