package jmegraphic;

import com.jme.bounding.BoundingBox;
import com.jme.scene.Node;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.CullState;
import com.jme.scene.state.ZBufferState;
import com.jme.scene.state.CullState.Face;
import com.jme.system.DisplaySystem;

public abstract class SceneElem extends Node {
	
	public SceneElem() {
		// TODO Auto-generated constructor stub
	}
	
	public SceneElem(String name) {
		super(name);
	}
	
	public void update(float tpf) {
		this.updateRenderState();
		this.updateGeometricState(tpf, true);
	}
	
	//public abstract void loadModel();
	

	public void addBoundingBox() {
		this.setModelBound(new BoundingBox());
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
}
