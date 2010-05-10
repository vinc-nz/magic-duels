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
	
	ObjectMap objects;
	LinkedList<SceneElem> elements;
	
	int loading;
	boolean paused;
	
	InputInterface input; // Input
	Timer timer;
	
	Countdown countdown;
	float lastTime;
	
	static final float UPTIME = 0.01f;  //intervallo di aggiornamento (in secondi)
	
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
	    
		Character player = fight.getPlayer(input.getPlayerID());
		Character enemy = fight.getEnemy(player);
		this.setLoading(40);
		
		GraphicCharacter focused = new GraphicCharacter(player);
		objects.put(player, focused);
		StatusBars focusedBars = new StatusBars(player,true,true);
		focusedBars.setPosition(HudObject.POSITION_BOTTOM_LEFT);
		elements.add(focusedBars);
		this.setLoading(70);
		
		GraphicCharacter other = new GraphicCharacter(enemy);
		this.objects.put(enemy, other);
		StatusBars otherBar = new StatusBars(enemy,true,false);
		otherBar.setPosition(HudObject.POSITION_UPPER_RIGHT);
		elements.add(otherBar);
		this.setLoading(80);
		
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
		camera.setCharacters(focused, other);
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
		
			camera.update(timer);
			
			scene.updateGeometricState(interpolation, true);
			scene.updateRenderState();
		} //ENDIF
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
	}
	
	protected void updateElements() {
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
