package jmegraphic;

import java.net.URL;

import com.jme.image.Texture;
import com.jme.image.Texture.WrapMode;
import com.jme.light.DirectionalLight;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.SharedMesh;
import com.jme.scene.Skybox;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.LightState;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;

import core.space.World;


/*
 *  ENVIRONMENT
 */
public class Arena extends SceneElem {
	DisplaySystem display;


	public Arena() {
		super("arena");
		display = DisplaySystem.getDisplaySystem();
		this.loadModel();
		this.addBoundingBox();
		this.buildLighting();
		this.applyZBufferState();
	}

	@Override
	public void loadModel() {
		//this.setupWalls();
		this.buildSkyBox();
		//this.buildTerrain();
		this.setupColumns();
		this.setupFloor();
	}

	private void setupWalls() {
		int bound = World.centerDistance;
		Quad wallModel = new Quad("wall model", bound*2, bound/2);
		wallModel.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
		Texture t = TextureManager.loadTexture(
				this.getClass().getClassLoader().getResource("data/textures/wall.jpg"),
				Texture.MinificationFilter.Trilinear,
				Texture.MagnificationFilter.Bilinear);
		t.setWrap(WrapMode.Repeat);
		t.setScale(Vector3f.UNIT_XYZ.mult(20));
		TextureState ts = display.getRenderer().createTextureState();
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
		int bound = World.centerDistance;
		Box model = new Box("tower", 
				new Vector3f(-bound, -bound*2, -bound), 
				new Vector3f(bound, 0, bound));
//		Quad model = new Quad("terrain", bound*2, bound*2);
//		Quaternion q = new Quaternion();
//		q.fromAngleAxis(-FastMath.PI/2, new Vector3f(1, 0, 0));
//		model.setLocalRotation(q);

//		CullState cs = display.getRenderer().createCullState();
//		cs.setEnabled(true);
//		cs.setCullFace(Face.Back);
//		model.setRenderState(cs);

		URL textureLoc = this.getClass().getClassLoader().getResource("data/textures/floor.jpg");
		Texture t = TextureManager.loadTexture(textureLoc,Texture.MinificationFilter.Trilinear,
				Texture.MagnificationFilter.Bilinear);
		t.setWrap(WrapMode.Repeat);
		t.setScale(Vector3f.UNIT_XYZ.divide(0.5f));
		TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
		ts.setTexture(t);

		model.setRenderState(ts);
		this.attachChild(model);

	}
	
	private void setupColumns() {
		int bound = World.centerDistance;
		Box model = new Box("column model", new Vector3f(0, 0, 0), new Vector3f(50, 50, 50));
		
		URL textureLoc = this.getClass().getClassLoader().getResource("data/textures/floor.jpg");
		Texture t = TextureManager.loadTexture(textureLoc,Texture.MinificationFilter.Trilinear,
				Texture.MagnificationFilter.Bilinear);
		t.setWrap(WrapMode.Repeat);
		//t.setScale(Vector3f.UNIT_XYZ.divide(0.01f));
		TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
		ts.setTexture(t);

		model.setRenderState(ts);
		
		Vector3f[] positions = {
				new Vector3f(bound-50, 0, bound-50),
				new Vector3f(-bound, 0, bound-50),
				new Vector3f(bound-50, 0, -bound),
				new Vector3f(-bound, 0, -bound)
		};
		
		for (int i=0;i<4;i++) {
			SharedMesh column = new SharedMesh(model);
			column.setLocalTranslation(positions[i]);
			this.attachChild(column);
		}
	}

	

	private void buildSkyBox() {
		Skybox skybox = new Skybox("skybox", 1000, 1000, 1000);

		Texture north = TextureManager.loadTexture(
				Arena.class.getClassLoader().getResource(
						"data/textures/north.jpg"),
						Texture.MinificationFilter.BilinearNearestMipMap,
						Texture.MagnificationFilter.Bilinear);
		Texture south = TextureManager.loadTexture(
				Arena.class.getClassLoader().getResource(
						"data/textures/south.jpg"),
						Texture.MinificationFilter.BilinearNearestMipMap,
						Texture.MagnificationFilter.Bilinear);
		Texture east = TextureManager.loadTexture(
				Arena.class.getClassLoader().getResource(
						"data/textures/east.jpg"),
						Texture.MinificationFilter.BilinearNearestMipMap,
						Texture.MagnificationFilter.Bilinear);
		Texture west = TextureManager.loadTexture(
				Arena.class.getClassLoader().getResource(
						"data/textures/west.jpg"),
						Texture.MinificationFilter.BilinearNearestMipMap,
						Texture.MagnificationFilter.Bilinear);
		Texture up = TextureManager.loadTexture(
				Arena.class.getClassLoader().getResource(
						"data/textures/top.jpg"),
						Texture.MinificationFilter.BilinearNearestMipMap,
						Texture.MagnificationFilter.Bilinear);
		Texture down = TextureManager.loadTexture(
				Arena.class.getClassLoader().getResource(
						"data/textures/bottom.jpg"),
						Texture.MinificationFilter.BilinearNearestMipMap,
						Texture.MagnificationFilter.Bilinear);

		skybox.setTexture(Skybox.Face.North, north);
		skybox.setTexture(Skybox.Face.West, west);
		skybox.setTexture(Skybox.Face.South, south);
		skybox.setTexture(Skybox.Face.East, east);
		skybox.setTexture(Skybox.Face.Up, up);
		skybox.setTexture(Skybox.Face.Down, down);
		skybox.preloadTextures();
		this.attachChild(skybox);
	}

	private void buildLighting() {
		/** Set up a basic, default light. */
		DirectionalLight light = new DirectionalLight();
		light.setDiffuse(new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
		light.setAmbient(new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f));
		light.setDirection(new Vector3f(1,-1,0));
		light.setEnabled(true);

		/** Attach the light to a lightState and the lightState to rootNode. */
		LightState lightState = display.getRenderer().createLightState();
		lightState.setEnabled(true);
		lightState.attach(light);
		this.setRenderState(lightState);
	}


	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
