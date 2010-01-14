package jmegraphic;

/*
 * MAIN
 */

import ia.IAStub;
import input.CharacterController;
import input.KeyboardInput;

import java.util.LinkedList;
import java.util.List;

import jmegraphic.hud.Countdown;
import jmegraphic.hud.HudObject;
import jmegraphic.hud.Notification;
import jmegraphic.hud.StatusBars;

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

public class GraphicFight extends BaseGame {
	Fight fight; // parita
	CustomCamera camera; // camera
	Node scene; // nodo radice
	
	LinkedList<GraphicObject> objects; // lista degli oggetti da disegnare
	GraphicCharacter focused; //il mago inquadrato dalla telecamera
	GraphicCharacter other;
	
	KeyboardInput input; // Input
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
		objects.add(countdown.getNotification());
		
		// attacca gli ogetti in lista al nodo radice
		for(GraphicObject i:objects) {
			scene.attachChild(i);
		}
		
		//camera in terza persona
		camera.setCharacters(focused, other);
		
		countdown.start();
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
		if (KeyBindingManager.getKeyBindingManager().isValidCommand("exit"))
			finished=true;
		
		if (finished)
			fight.end();
		
		timer.update();
		countdown.update(timer);
		
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
	
	
	//per i settaggi video
	public void videoSettings() {
		this.setConfigShowMode(ConfigShowMode.AlwaysShow);
		this.getAttributes();
		this.setConfigShowMode(ConfigShowMode.ShowIfNoConfig);
		//lscritta : 800 = x : lsfondo
		
	}


	//inizializza una partita in single player
	public void initSingleGame() {
		//creo personaggi e magie
		Spell fireball = new Spell("fireball", 5, 5, false, 0, 10);
		PlayingCharacter p1 = new PlayingCharacter("dwarf_red", 100, 50, 5, 5, 2);
		p1.addSpell(fireball);
		PlayingCharacter p2 = new PlayingCharacter("dwarf_white", 100, 50, 5, 5, 2);
		p2.addSpell(fireball);
		
		//creo partita logica
		Fight fight= new Fight(p1, p2);
		CharacterController human = new CharacterController(Fight.ID_P1, fight);
		CharacterController ia = new CharacterController(Fight.ID_P2, fight);
		new IAStub(ia,fight).start(); //avvio il thread dell'ia
		
		//inizializzo la classe this
		this.fight = fight;
		this.input = new KeyboardInput(human);
		this.start();
	}
	
	public static void main(String[] args) {
		GraphicFight game = new GraphicFight();
		game.initSingleGame();
	}
	
	
}
