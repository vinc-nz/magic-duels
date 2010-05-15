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
import Menu.src.MainMenu;

import com.jme.app.BaseGame;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.renderer.ColorRGBA;
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
	public Node scene; // nodo radice
	
	GraphicCharacter focused;
	ObjectMap objects;
	LinkedList<SceneElem> elements;
	StatusBars enemyBars;
	
	int loading;
	boolean paused;
	
	InputInterface input; // Input
	Timer timer;
	
	Countdown countdown;
	float lastTime;
	
	static final float UPTIME = 0.008f;  //intervallo di aggiornamento (in secondi)
	
	MainMenu mainMenu;
	
	public GraphicFight(MainMenu mainMenu) {
		this.lastTime=0;
		objects = new ObjectMap();
		elements = new LinkedList<SceneElem>();
		this.mainMenu = mainMenu;
		this.fight = new Fight();
		this.loading = 0;
		this.paused = false;
	}
	
	

	@Override
	protected void cleanup() {
		fight.end();
	}

	@Override
	protected void initGame() {
		this.setLoading(30);
		//viene creato il root node
		scene = new Node("battlefield");
		scene.attachChild(new Arena());
		this.setLoading(35);
		
		for (int i=1;i<=fight.numberOfPlayers();i++) {
			Character player = fight.getPlayer(i);
			GraphicCharacter graphicCharacter = new GraphicCharacter(player);
			this.objects.put(player, graphicCharacter);
			if (i==input.getPlayerID()) {
				this.focused = graphicCharacter;
				StatusBars focusedBars = new StatusBars(player,true,true);
				focusedBars.setPosition(HudObject.POSITION_BOTTOM_LEFT);
				elements.add(focusedBars);
				this.setLoading(50);
			}
		}
		this.setLoading(80);
		
		int enemy = focused.coreCharacter.getTarget();
		this.enemyBars = new StatusBars(fight.getPlayer(enemy), true, false);
		enemyBars.setPosition(HudObject.POSITION_UPPER_RIGHT);
		elements.add(enemyBars);
	    
		ExplosionFactory.warmup();
		
		countdown = new Countdown(fight, 3);
		elements.add(countdown);
		this.setLoading(85);
		
		// attacca gli ogetti in lista al nodo radice
		for(SceneElem i:elements) {
			i.loadModel();
			scene.attachChild(i);
		}
		this.setLoading(95);
		
		//camera in terza persona
		camera.setFocused(focused);
		this.setLoading(100);
	}

	@Override
	protected void initSystem() {
		// creazione display
		int width=mainMenu.WIDTH;
		int height=mainMenu.HEIGHT;
		
		this.setLoading(10);
		try {
			display=DisplaySystem.getDisplaySystem();
			display.createWindow(width, height, 
								settings.getDepth(), 
								settings.getFrequency(), 
								mainMenu.fullscreen);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		this.setLoading(20);
		// setta il colore di default
		display.getRenderer().setBackgroundColor(ColorRGBA.black);
		
		camera = new CustomCamera(display);
		
		KeyBindingManager.getKeyBindingManager().set("exit", KeyInput.KEY_ESCAPE);
		
		timer = Timer.getTimer();
		this.setLoading(25);
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
	
	
	
	public synchronized int getLoading() {
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loading;
	}



	public synchronized void setLoading(int loading) {
		this.loading = loading;
		notify();
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
