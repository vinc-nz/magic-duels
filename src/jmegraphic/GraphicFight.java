package jmegraphic;

/*
 * MAIN
 */

import input.InputInterface;
import input.KeyboardInput;

import java.util.LinkedList;
import java.util.List;

import jmegraphic.hud.Countdown;
import jmegraphic.hud.HudObject;
import jmegraphic.hud.StatusBars;
import utils.ExplosionFactory;

import com.jme.app.BaseGame;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.system.DisplaySystem;
import com.jme.util.Timer;
import com.jmex.effects.particles.ParticleMesh;

import core.Damage;
import core.Fight;
import core.PlayingCharacter;
import core.SpellInstance;

public class GraphicFight extends BaseGame {
	Fight fight; // parita
	CustomCamera camera; // camera
	Node scene; // nodo radice
	
	LinkedList<GraphicObject> objects; // lista degli oggetti da disegnare
	GraphicCharacter focused; //il mago inquadrato dalla telecamera
	GraphicCharacter other;
	
	InputInterface input; // Input
	Timer timer;
	ModelManager manager;
	Countdown countdown;
	float lastTime;
	
	static final float UPTIME = 0.01f;  //intervallo di aggiornamento (in secondi)
	
	public GraphicFight() {
		this.lastTime=0;
		objects = new LinkedList<GraphicObject>();
		manager = new ModelManager();
	}
	
	public GraphicFight(Fight fight, KeyboardInput input) {
		this.fight=fight;
		this.input=input;
		this.lastTime=0;
		objects = new LinkedList<GraphicObject>();
		manager = new ModelManager();
	}

	@Override
	protected void cleanup() {
		fight.end();
	}

	@Override
	protected void initGame() {
		//viene creato il root node
		scene=new Node("battlefield");
		scene.attachChild(new Arena());
		
	    
		PlayingCharacter player = fight.getPlayer(input.getPlayerID());
		PlayingCharacter enemy = fight.getEnemy(player);
		
		this.focused = new GraphicCharacter(manager,player);
		objects.add(focused);
		StatusBars focusedBars = new StatusBars(player,true,true);
		focusedBars.setPosition(HudObject.POSITION_BOTTOM_LEFT);
		objects.add(focusedBars);
		
		this.other = new GraphicCharacter(manager,enemy);
		objects.add(other);
		StatusBars otherBar = new StatusBars(enemy,true,false);
		otherBar.setPosition(HudObject.POSITION_UPPER_RIGHT);
		objects.add(otherBar);
		
		ExplosionFactory.warmup();
		
		countdown = new Countdown(fight, 3);
		objects.add(countdown);
		
		// attacca gli ogetti in lista al nodo radice
		for(GraphicObject i:objects) {
			scene.attachChild(i);
		}
		
		//camera in terza persona
		camera.setCharacters(focused, other);
		
	}

	@Override
	protected void initSystem() {
		// creazione display
		int width=settings.getWidth(),height=settings.getHeight();
		try {
			display=DisplaySystem.getDisplaySystem();
			display.createWindow(width, height, 
								settings.getDepth(), 
								settings.getFrequency(), 
								settings.isFullscreen());
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
		
			input.update(interpolation);
			
			
			//prende una nuova magia dal core
			SpellInstance newSpell = fight.pollSpell();
			if (newSpell!=null) {
				this.castGraphicSpell(newSpell);
			}
			
			Damage damage = fight.pollDamage();
			if (damage!=null) {
				this.applyGraphicDamage(damage);
			}
			
			this.updateObjects(); //aggiorna la lista degli oggetti
			
			fight.update(timer.getTimeInSeconds());
		
			camera.update(timer);
			
			scene.updateGeometricState(interpolation, true);
			scene.updateRenderState();
		} //ENDIF
	}
	
	protected void updateObjects() {
		List<GraphicObject> elemToRemove = new LinkedList<GraphicObject>();
		for (GraphicObject i:objects) {
			if (i.toRemove()) {
				//metto in un'altra lista gli elementi da rimuovere
				elemToRemove.add(i); 
				scene.detachChild(i);
			}
			else {
				i.update();
				if (i.becomeVisible())
					scene.attachChild(i);
			}
		}
		
		for (GraphicObject i:elemToRemove)
			objects.remove(i);
		
	}

	protected void applyGraphicDamage(Damage damage) {
		GraphicCharacter target = (damage.getTarget() == focused.coreCharacter ?
									focused : other);
		ParticleMesh explosion = ExplosionFactory.getExplosion();
		explosion.setSpeed(10);
		explosion.setLocalScale(0.5f);
		explosion.forceRespawn();
		target.attachChild(explosion);
		
	}

	protected void castGraphicSpell(SpellInstance newSpell) {
		GraphicCharacter spellCaster = (newSpell.getOwner() == focused.coreCharacter ?
										focused : other);
		
		spellCaster.castGraphicSpell(newSpell);
		
		GraphicObject obj = new GraphicSpell(manager, newSpell);
		objects.add(obj); 
	}

	public void startFight() {
		countdown.start();
	}
	
}
