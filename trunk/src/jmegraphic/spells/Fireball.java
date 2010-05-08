package jmegraphic.spells;

import java.io.IOException;
import java.net.URL;

import utils.ExplosionFactory;

import com.jme.math.Vector3f;
import com.jme.util.export.binary.BinaryImporter;
import com.jmex.effects.particles.ParticleMesh;

import core.objects.AbstractObject;
import jmegraphic.GraphicObject;

public class Fireball extends GraphicObject {
	
	ParticleMesh explosion = null;
	int height = 20;

	
	
	public Fireball() {
		super();
	}

	public Fireball(AbstractObject object) {
		super(object);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setLocalTranslation(Vector3f localTranslation) {
		localTranslation.y = this.height;
		super.setLocalTranslation(localTranslation);
	}

	@Override
	public void loadModel() {
		URL location = this.getClass().getClassLoader().getResource("data/models/fireball.jme");
		ParticleMesh f = null;
		try {
			f = (ParticleMesh)BinaryImporter.getInstance().load(location);
			f.setLocalScale(0.2f);
			f.setEmissionDirection(new Vector3f(0, 0, 1));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.attachChild(f);
	}
	
	@Override
	public void update() {
		
		super.update();
		
		if (!this.getObject().isInGame())
			this.explode();
	}
	
	@Override
	public boolean isInGame() {
		if (this.explosion==null)
			return true;
		return this.explosion.isActive();
	}

	private void explode() {
		this.detachAllChildren();
		this.explosion = ExplosionFactory.getExplosion();
		this.explosion.setSpeed(10);
		this.explosion.setLocalScale(0.5f);
		this.explosion.forceRespawn();
		this.attachChild(explosion);
	}

	

}
