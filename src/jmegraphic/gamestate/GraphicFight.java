package jmegraphic.gamestate;

/*
 * MAIN
 */

import java.util.HashMap;

import jmegraphic.Arena;
import jmegraphic.CustomCamera;
import jmegraphic.GraphicCharacter;
import jmegraphic.GraphicObject;

import utils.ExplosionFactory;

import com.jme.scene.Spatial;
import com.jmex.game.state.BasicGameState;

import core.fight.Character;
import core.fight.Fight;
import core.objects.AbstractObject;
import core.space.World;



public class GraphicFight extends BasicGameState {
	Fight fight; // parita
	CustomCamera camera; // camera
	
	GraphicCharacter focused;
	ObjectMap objects;
	
	//CountdownTimer uptime;
	
	public static final float UPTIME = 0.008f;  //intervallo di aggiornamento (in secondi)
	
	Arena arena;
	
	public GraphicFight(Fight fight) {
		super("GraphicFight");
		this.fight = fight;
		//uptime = new CountdownTimer();
		objects = new ObjectMap();
	}
	
	
	

	public void initGame(int playerId) {
		camera = new CustomCamera();
		
		for (int i=1;i<=fight.numberOfPlayers();i++) {
			Character player = fight.getPlayer(i);
			GraphicCharacter graphicCharacter = new GraphicCharacter(player);
			this.objects.put(player, graphicCharacter);
			if (i==playerId) {
				focused = graphicCharacter;
				camera.setFocused(focused);
			}
		}
	    
		ExplosionFactory.warmup();
		
		arena = new Arena();
		this.attach(arena);
		arena.update(0);
		//uptime.start(UPTIME);
	}

	

	// aggiornamento
	@Override
	public void update(float tpf) {
		super.update(tpf);
		
		//if (uptime.expired()) {

			fight.update();
			this.updateObjects(tpf);
			

			this.updateCamera();
			
//			uptime.start(UPTIME);
//		} //ENDIF
		
	}
	
	protected void updateCamera() {
		Character enemy = fight.getEnemy(focused.getCoreCharacter());
		GraphicObject target = objects.get(enemy);
		camera.setTarget(target);
		camera.update();
	}


	protected void updateObjects(float tpf) {
		
		for (AbstractObject obj : World.getObjects() ) {
			GraphicObject go = this.objects.get(obj);
			if (go==null)
				this.objects.add(obj);
			else if (!go.isInGame()) 
				this.objects.remove(obj);
			else go.update(tpf);
		}
	}
	
	public void attach(Spatial child) {
		this.getRootNode().attachChild(child);
	}
	
	public void detach(Spatial child) {
		this.getRootNode().detachChild(child);
	}
	

	public class ObjectMap extends HashMap<AbstractObject, GraphicObject> {
		@Override
		public GraphicObject put(AbstractObject key, GraphicObject value) {
			GraphicFight.this.attach(value);
			value.loadModel();
			return super.put(key, value);
		}
		
		@Override
		public GraphicObject remove(Object key) {
			GraphicFight.this.detach(this.get(key));
			return super.remove(key);
		}
		
		public void add(AbstractObject obj) {
			GraphicObject go = GraphicObject.fromObject(obj);
			this.put(obj, go);
		}
	}

}
