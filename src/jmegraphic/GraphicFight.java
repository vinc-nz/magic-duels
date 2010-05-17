package jmegraphic;

/*
 * MAIN
 */

import input.InputInterface;

import java.util.HashMap;
import java.util.LinkedList;

import jmegraphic.hud.Countdown;
import jmegraphic.hud.HudObject;
import jmegraphic.hud.Notification;
import jmegraphic.hud.StatusBars;
import utils.ExplosionFactory;

import com.jme.app.BaseGame;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.scene.Node;
import com.jme.system.DisplaySystem;
import com.jme.util.Timer;

import core.fight.Character;
import core.fight.Fight;
import core.objects.AbstractObject;
import core.space.World;



public class GraphicFight extends BaseGame {
	Fight fight; // parita
	CustomCamera camera; // camera
	Node scene; // nodo radice
	
	GraphicCharacter focused;
	ObjectMap objects;
	LinkedList<SceneElem> elements;
	StatusBars enemyBars;
	
	
	boolean paused;
	
	InputInterface input; // Input
	Timer timer;
	
	Countdown countdown;
	float lastTime;
	
	static final float UPTIME = 0.008f;  //intervallo di aggiornamento (in secondi)
	
	
	
	public GraphicFight() {
		this.lastTime=0;
		timer = Timer.getTimer();
		objects = new ObjectMap();
		elements = new LinkedList<SceneElem>();
		this.paused = false;
	}
	
	

	@Override
	protected void cleanup() {
		fight.end();
	}

	@Override
	protected void initGame() {
		
		KeyBindingManager.getKeyBindingManager().set("exit", KeyInput.KEY_ESCAPE);
		camera = new CustomCamera(display);
		
		//viene creato il root node
		scene = new Node("battlefield");
		
		for (int i=1;i<=fight.numberOfPlayers();i++) {
			Character player = fight.getPlayer(i);
			GraphicCharacter graphicCharacter = new GraphicCharacter(player);
			this.objects.put(player, graphicCharacter);
			if (i==input.getPlayerID()) {
				this.focused = graphicCharacter;
				StatusBars focusedBars = new StatusBars(player,true,true,false);
				focusedBars.setPosition(HudObject.POSITION_BOTTOM_LEFT);
				elements.add(focusedBars);
				
			}
		}
		
		
		int enemy = focused.coreCharacter.getTarget();
		this.enemyBars = new StatusBars(fight.getPlayer(enemy), true, false, true);
		enemyBars.setPosition(HudObject.POSITION_UPPER_RIGHT);
		elements.add(enemyBars);
	    
		ExplosionFactory.warmup();
		
		countdown = new Countdown(fight, 3);
		elements.add(countdown);
		
		
		// attacca gli ogetti in lista al nodo radice
		for(SceneElem i:elements) {
			i.loadModel();
			scene.attachChild(i);
		}
		
		scene.attachChild(new Arena());
		
		//camera in terza persona
		camera.setFocused(focused);
		
	}

	@Override
	protected void initSystem() {
		this.creanteDisplay(settings.getWidth(), settings.getHeight(), settings.isFullscreen());
	}
	
	protected void creanteDisplay(int width, int height, boolean fullscreen) {
		try {
			display=DisplaySystem.getDisplaySystem();
			display.createWindow(width, height, 
								settings.getDepth(), 
								settings.getFrequency(), 
								fullscreen);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	protected void reinit() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void render(float arg0) {
		display.getRenderer().clearBuffers();
		display.getRenderer().draw(scene);

	}

	// aggiornamento
	@Override
	protected void update(float interpolation) {
		if (KeyBindingManager.getKeyBindingManager().isValidCommand("exit")
				|| fight.finished)
			finished=true;
		
		timer.update();
		
		if (timer.getTimeInSeconds()-lastTime>UPTIME) {
			interpolation=timer.getTimePerFrame();
			lastTime=timer.getTimeInSeconds();
		
			
			
			fight.update();
			
			if (fight.paused()) {
				if (!this.paused)
					pause();
			}
			else if (this.paused)
				resume();
			else this.updateObjects();
			
			this.updateElements();
		
			this.updateCamera();
			
			scene.updateGeometricState(interpolation, true);
			scene.updateRenderState();
		} //ENDIF
	}
	
	protected void updateCamera() {
		int enemy = focused.coreCharacter.getTarget();
		GraphicObject target = objects.get(fight.getPlayer(enemy));
		camera.setTarget(target);
		camera.update();
	}


	private void resume() {
		SceneElem notification = elements.getLast();
		this.scene.detachChild(notification);
		elements.remove(notification);
		this.paused = false;
	}



	private void pause() {
		this.paused = true;
		Notification n = new Notification("Pausa");
		n.setPosition(HudObject.POSITION_CENTER);
		this.scene.attachChild(n);
		this.elements.add(n);
	}



	protected void updateObjects() {
		
		for (AbstractObject obj : World.getObjects() ) {
			GraphicObject go = this.objects.get(obj);
			if (go==null)
				this.objects.add(obj);
			else if (!go.isInGame()) 
				this.objects.remove(obj);
			else go.update();
		}
		
		if (focused.coreCharacter.notEnoughMana()) {
			Notification noMana = new Notification("NO MANA!");
			noMana.setPosition(HudObject.POSITION_BOTTOM);
			noMana.setExpireTime(3);
			noMana.loadModel();
			scene.attachChild(noMana);
			this.elements.add(noMana);
		}
	}
	
	protected void updateElements() {
		int enemy = focused.coreCharacter.getTarget();
		enemyBars.setCoreCharacter(fight.getPlayer(enemy));
		
		LinkedList<SceneElem> newElems = new LinkedList<SceneElem>();
		
		for (SceneElem sceneElem : this.elements) {
			sceneElem.update();
			if (sceneElem.isInGame())
				newElems.add(sceneElem);
			else this.scene.detachChild(sceneElem);
		}
		
		this.elements = newElems;
	}

	public void startFight() {
		countdown.start();
	}
	

	public class ObjectMap extends HashMap<AbstractObject, GraphicObject> {
		@Override
		public GraphicObject put(AbstractObject key, GraphicObject value) {
			GraphicFight.this.scene.attachChild(value);
			value.loadModel();
			return super.put(key, value);
		}
		
		@Override
		public GraphicObject remove(Object key) {
			GraphicFight.this.scene.detachChild(this.get(key));
			return super.remove(key);
		}
		
		public void add(AbstractObject obj) {
			GraphicObject go = GraphicObject.fromObject(obj);
			this.put(obj, go);
		}
	}
	
}
