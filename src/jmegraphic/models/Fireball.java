package jmegraphic.models;

import java.io.IOException;
import java.net.URL;

import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.util.export.binary.BinaryImporter;
import com.jmex.effects.particles.ParticleMesh;

public class Fireball implements ModelGetter {

	@Override
	public Node get() {
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
