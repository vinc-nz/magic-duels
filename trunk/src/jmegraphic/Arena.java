package jmegraphic;

import java.io.Reader;
import java.net.URL;

import utils.ModelLoader;

import com.jme.image.Texture;
import com.jme.image.Texture.ApplyMode;
import com.jme.image.Texture.EnvironmentalMapMode;
import com.jme.image.Texture.WrapMode;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.SharedMesh;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.CullState;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.TextureState;
import com.jme.scene.state.ZBufferState;
import com.jme.scene.state.CullState.Face;
import com.jme.scene.state.RenderState.StateType;
import com.jme.system.DisplaySystem;
import com.jme.util.BumpMapColorController;
import com.jme.util.TextureManager;

import core.World;


/*
 *  ENVIRONMENT
 */
public class Arena extends GraphicObject {
	int bound;
	
	public Arena() {
		super("arena");
		this.bound = World.centerDistance;
		this.setupWalls();
		this.setupFloor();
		this.applyBlendState();
		this.applyZBufferState();
	}

	private void setupWalls() {
		Quad wallModel = new Quad("wall model", bound*2, bound/2);
		wallModel.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
		Texture t = TextureManager.loadTexture(
							this.getClass().getClassLoader().getResource("data/textures/wall.jpg"),
							Texture.MinificationFilter.Trilinear,
			                Texture.MagnificationFilter.Bilinear);
		t.setWrap(WrapMode.Repeat);
		t.setScale(Vector3f.UNIT_XYZ.mult(20));
		TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
		ts.setTexture(t);
		wallModel.setRenderState(ts);
		
		for (int i = 0; i < 4; i++) {
			SharedMesh wall = new SharedMesh(wallModel);
			this.attachChild(wall);
			wall.getLocalTranslation().y = bound/4;
			if (i<2)
				wall.getLocalTranslation().z = bound*(i==0 ? -1 : 1);
			else {
				Quaternion q = new Quaternion();
				q.fromAngleAxis(FastMath.PI/2, new Vector3f(0, 1, 0));
				wall.setLocalRotation(q);
				wall.getLocalTranslation().x = bound*(i==2 ? -1 : 1);
			}
		}
	}

	private void setupFloor() {
		Quad model = new Quad("terrain", bound*2, bound*2);
		Quaternion q = new Quaternion();
		q.fromAngleAxis(-FastMath.PI/2, new Vector3f(1, 0, 0));
		model.setLocalRotation(q);
		
		CullState cs = DisplaySystem.getDisplaySystem().getRenderer().createCullState();
		cs.setEnabled(true);
		cs.setCullFace(Face.Back);
		model.setRenderState(cs);
		
		URL textureLoc = this.getClass().getClassLoader().getResource("data/textures/floor.jpg");
		Texture t = TextureManager.loadTexture(textureLoc,Texture.MinificationFilter.Trilinear,
                Texture.MagnificationFilter.Bilinear);
		t.setWrap(WrapMode.Repeat);
		t.setScale(Vector3f.UNIT_XYZ.divide(0.01f));
		TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
		ts.setTexture(t);
		
		model.setRenderState(ts);
		this.attachChild(model);
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
