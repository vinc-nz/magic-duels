package jmegraphic;

/*
 * GRAFICA DELLA MAGIA
 */
import java.lang.Thread.State;

import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.ZBufferState;
import com.jme.system.DisplaySystem;

import core.SpellAttack;
import core.SpellInstance;

public class GraphicSpell extends GraphicObject {
	SpellInstance coreSpell; //istanza logica della magia
	float height;		//posizionamento sull'asse y del nodo
	boolean visible;	//se visibile
	
	public GraphicSpell(ModelManager manager, SpellInstance coreSpell) {
		super(coreSpell.getSpellName(), manager);
		this.coreSpell = coreSpell;
		this.height = 20; //da settare in proporzione
		this.visible = false;  //all'inizio non viene disegnata
		
		if (coreSpell instanceof SpellAttack) {
			SpellAttack attack = (SpellAttack)coreSpell;
			Vector3f position = GraphicObject.mapCoords(attack.getPosition());
			position.y = height;
			this.setLocalTranslation(position);
			this.lookAt(GraphicObject.mapCoords(attack.getTrajectory()));
		}
	}
	
	// aggiornamento
	public void update() {
		if (coreSpell instanceof SpellAttack) {
			SpellAttack attack = (SpellAttack)coreSpell;
			Vector3f newPosition = GraphicObject.mapCoords(attack.getPosition());
			newPosition.y = height;
			super.updatePosition(newPosition);
		}
		
	}
	
	//true se l'oggetto dev'essere rimosso dalla lista
	public boolean toRemove() {
		return coreSpell.getState() == State.TERMINATED;
	}
	
	@Override
	public boolean becomeVisible() {
		if (!visible && coreSpell.isAlive()) {
			visible = true;
			return true;
		}
		return false;
	}

}
