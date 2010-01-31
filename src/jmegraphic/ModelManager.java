package jmegraphic;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

import utils.ModelLoader;

import com.jme.image.Texture;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import com.jme.util.export.binary.BinaryImporter;
import com.jmex.effects.particles.ParticleMesh;



/*
 * GESTISIONE MODELLI GRAFICI
 */
public class ModelManager {
	DisplaySystem display;
	
	public ModelManager() {
		display = DisplaySystem.getDisplaySystem();
	}
	
	public Node get(String name) { //restituisce un modello in base al nome
		Method m = null;
		Node model = null;
			try {
				m = this.getClass().getMethod(name);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				model = (Node)m.invoke(this);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return model;
	}
	
	
	public Node dwarf_white() {
		return dwarf("data/models/dwarf/dwarf1.ms3d","data/models/dwarf/dwarf.jpg");
	}
	
	public Node dwarf_red() {
		return dwarf("data/models/dwarf/dwarf2.ms3d","data/models/dwarf/dwarf2.jpg");
	}
	
	private Node dwarf(String modelUrl,String textUrl) {
		Node model = ModelLoader.loadModel(modelUrl, "", 1, new Quaternion());
		
		Texture texture = TextureManager.loadTexture(
                ModelLoader.class.getClassLoader().getResource( textUrl ),
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
        TextureState ts1 = display.getRenderer().createTextureState();
        ts1.setEnabled(true);
        ts1.setTexture(texture);
        model.getChild("axe").setRenderState( ts1 );
        
            
        return model;
	}
	
	public Node fireball() {
		URL location = this.getClass().getClassLoader().getResource("data/models/fireball.jme");
		ParticleMesh f = null;
		try {
			f = (ParticleMesh)BinaryImporter.getInstance().load(location);
			f.setLocalScale(0.2f);
			f.setEmissionDirection(new Vector3f(0, 0, 1));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}

}
