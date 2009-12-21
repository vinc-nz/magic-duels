package jmegraphic;

/*
 * MAIN
 */

import ia.IAStub;
import input.CharacterController;
import input.KeyboardInput;

import java.util.LinkedList;
import java.util.List;

import utils.ExplosionFactory;


import com.jme.app.BaseGame;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.light.DirectionalLight;
import com.jme.light.PointLight;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.state.LightState;
import com.jme.scene.state.ZBufferState;
import com.jme.system.DisplaySystem;
import com.jme.util.Timer;
import com.jmex.effects.particles.ParticleMesh;
import core.Damage;
import core.Fight;
import core.PlayingCharacter;
import core.Spell;
import core.SpellInstance;
import core.ThirdPersonMovement;

public class Main extends BaseGame {
	Fight fight; // parita
	CustomCamera camera; // camera
	Node scene; // nodo radice
	ZBufferState depthBuffer;
	
	LinkedList<GraphicObject> objects; // lista degli oggetti da disegnare
	GraphicCharacter focused; //il mago inquadrato dalla telecamera
	GraphicCharacter other;
	
	KeyboardInput input; // Input
	Timer timer;
	ModelManager manager;
	float lastTime;
	
	static final float UPTIME = 0.01f;  //intervallo di aggiornamento (in secondi)
	
	public Main(Fight fight, KeyboardInput input) {
		this.fight=fight;
		this.input=input;
		this.lastTime=0;
		objects = new LinkedList<GraphicObject>();
		manager = new ModelManager();
	}

	@Override
	protected void cleanup() {
		// TODO Auto-generated method stub

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
		objects.add(new StatusBars(player,true,true));
		
		this.other = new GraphicCharacter(manager,enemy);
		objects.add(other);
		GraphicObject otherBar = new StatusBars(enemy,true,false);
		otherBar.getLocalTranslation().x = (display.getWidth() * 2/3);
		otherBar.getLocalTranslation().y = (display.getHeight() * 4/5);
		objects.add(otherBar);
		
		ExplosionFactory.warmup();
		
		// attacca il modello al nodo
		for(GraphicObject i:objects) {
			scene.attachChild(i);
		}
		
		//camera in terza persona
		camera.setCharacters(focused, other);
		
		buildLighting();
		
		fight.start(); //avvia il combattimento
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
				|| !fight.running)
			finished=true;
		
		if (finished)
			fight.end();
		
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
		explosion.setRenderState(depthBuffer);
		target.attachChild(explosion);
		
	}

	protected void castGraphicSpell(SpellInstance newSpell) {
		GraphicCharacter spellCaster = (newSpell.getOwner() == focused.coreCharacter ?
										focused : other);
		
		spellCaster.castGraphicSpell(newSpell);
		
		GraphicObject obj = new GraphicSpell(manager, newSpell);
		objects.add(obj); 
		//obj.setRenderState(depthBuffer);
	}

	private void buildLighting() {
		/** Set up a basic, default light. */
	    DirectionalLight light = new DirectionalLight();
	    light.setDiffuse(new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
	    light.setAmbient(new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f));
	    light.setDirection(new Vector3f(1,-1,1));
	    light.setEnabled(true);
		
 
	      /** Attach the light to a lightState and the lightState to rootNode. */
	    LightState lightState = display.getRenderer().createLightState();
	    lightState.setEnabled(true);
	    lightState.attach(light);
	    scene.setRenderState(lightState);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//STUB
		Spell fireball = new Spell("fireball", 5, 5, false, 0, 10);
		PlayingCharacter p1 = new PlayingCharacter("dwarf_red", 100, 50, 5, 5, 2);
		p1.addSpell(fireball);
		PlayingCharacter p2 = new PlayingCharacter("dwarf_white", 100, 50, 5, 5, 2);
		p2.addSpell(fireball);
		Fight fight= new Fight(p1, p2, new ThirdPersonMovement());
		CharacterController human = new CharacterController(Fight.ID_P1, fight);
		KeyboardInput input = new KeyboardInput(human);
		Main game= new Main(fight,input);
		new IAStub(new CharacterController(Fight.ID_P2, fight),fight).start();
		game.setConfigShowMode(ConfigShowMode.AlwaysShow);
		game.start();
	}
}
