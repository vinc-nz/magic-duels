package jmegraphic.spells;

import jmegraphic.GraphicObject;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.BlendState;
import com.jme.system.DisplaySystem;

public class Protection extends GraphicObject {
	
	@Override
	public void loadModel() {
		Sphere s = new Sphere("bolla",30,30,40);
		s.setLocalTranslation(Vector3f.UNIT_Y.mult(20));
		s.setDefaultColor(ColorRGBA.magenta);
		
		BlendState bs = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
		bs.setBlendEnabled(true);
		bs.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
        bs.setDestinationFunction(BlendState.DestinationFunction.One);
        bs.setTestEnabled(true);
        bs.setTestFunction(BlendState.TestFunction.GreaterThan);
        bs.setEnabled(true);
        s.setRenderState(bs);
		
		this.attachChild(s);
		
	}
	
	@Override
	public void update(float tpf) {
		this.updatePosition();
		super.update(tpf);
	}
	
	

}
