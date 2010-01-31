package jmegraphic.models;

import utils.ModelLoader;

import com.jme.image.Texture;
import com.jme.math.Quaternion;
import com.jme.scene.Node;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;

public class RedDwarf implements ModelGetter {

	@Override
	public Node get() {
		Node model = ModelLoader.loadModel("data/models/dwarf/dwarf2.ms3d",
				"", 1, new Quaternion());

		Texture texture = TextureManager.loadTexture(
		ModelLoader.class.getClassLoader().getResource( "data/models/dwarf/dwarf2.jpg" ),
		Texture.MinificationFilter.Trilinear,
		Texture.MagnificationFilter.Bilinear);
		TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
		ts.setEnabled(true);
		ts.setTexture(texture);
		model.setRenderState( ts );
		
		texture = TextureManager.loadTexture(
		ModelLoader.class.getClassLoader().getResource( "data/models/dwarf/axe.jpg" ),
		Texture.MinificationFilter.Trilinear,
		Texture.MagnificationFilter.Bilinear);
		TextureState ts1 = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
		ts1.setEnabled(true);
		ts1.setTexture(texture);
		model.getChild("axe").setRenderState( ts1 );
		
		
		return model;
	}

}
