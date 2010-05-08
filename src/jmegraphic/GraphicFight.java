package jmegraphic;

/*
 * MAIN
 */

import input.InputInterface;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jmegraphic.hud.Countdown;
import jmegraphic.hud.HudObject;
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
import com.jmex.effects.particles.ParticleMesh;


import core.fight.Fight;
import core.objects.AbstractObject;
import core.space.World;
import core.fight.Character;



public class GraphicFight extends BaseGame {
	Fight fight; // parita
	CustomCamera camera; // camera
	public Node scene; // nodo radice
	
	ObjectMap objects;
	List<SceneElem> elements;
	
	GraphicCharacter focused; //il mago inquadrato dalla telecamera
	GraphicCharacter other;
	
	InputInterface input; // Input
	Timer timer;
	
	Countdown countdown;
	float lastTime;
	
	static final float UPTIME = 0.005f;  //intervallo di aggiornamento (in secondi)
	
	MainMenu mainMenu;
	
	public GraphicFight(MainMenu mainMenu) {
		this.lastTime=0;
		objects = new ObjectMap();
		elements = new LinkedList<SceneElem>();
		this.mainMenu = mainMenu;
		this.fight = new Fight();
	}
	
	

	@Override
	protected void cleanup() {
		fight.end();
	}

	@Override
	protected void initGame() {
		//viene creato il root node
		scene = new Node("battlefield");
		scene.attachChild(new Arena());
		
	    
		Character player = fight.getPlayer(input.getPlayerID());
		Character enemy = fight.getEnemy(player);
		
		this.focused = new GraphicCharacter(player);
		objects.put(player, focused);
		StatusBars focusedBars = new StatusBars(player,true,true);
		focusedBars.setPosition(HudObject.POSITION_BOTTOM_LEFT);
		elements.add(focusedBars);
		
		this.other = new GraphicCharacter(enemy);
		this.objects.put(enemy, other);
		StatusBars otherBar = new StatusBars(enemy,true,false);
		otherBar.setPosition(HudObject.POSITION_UPPER_RIGHT);
		elements.add(otherBar);
		
		ExplosionFactory.warmup();
		
		countdown = new Countdown(fight, 3);
		elements.add(countdown);
		
		// attacca gli ogetti in lista al nodo radice
		for(SceneElem i:elements) {
			i.loadModel();
			scene.attachChild(i);
		}
		
		//camera in terza persona
		camera.setCharacters(focused, other);
		
	}

	@Override
	protected void initSystem() {
		// creazione display
		int width=mainMenu.WIDTH;
		int height=mainMenu.HEIGHT;
		
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
		// setta il colore di default
		display.getRenderer().setBackgroundColor(ColorRGBA.black);
		
		camera = new CustomCamera(display);
		
		KeyBindingManager.getKeyBindingManager().set("exit", KeyInput.KEY_ESCAPE);
		
		timer = Timer.getTimer();
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
			
			this.updateObjects();
			this.updateElements();
		
			camera.update(timer);
			
			scene.updateGeometricState(interpolation, true);
			scene.updateRenderState();
		} //ENDIF
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
			else {this.scene.detachChild(sceneElem);System.out.println("\n\n\n\n\n\n"+sceneElem);}
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
