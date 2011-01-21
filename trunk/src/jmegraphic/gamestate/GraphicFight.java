package jmegraphic.gamestate;

/*
 * MAIN
 */

import java.util.HashMap;
import java.util.concurrent.Callable;

import jmegraphic.Arena;
import jmegraphic.CountdownTimer;
import jmegraphic.CustomCamera;
import jmegraphic.GraphicCharacter;
import jmegraphic.GraphicObject;
import jmegraphic.JmeGame;
import jmegraphic.spells.Protection;
import utils.ExplosionFactory;

import com.jme.scene.Spatial;
import com.jme.system.DisplaySystem;
import com.jme.util.GameTaskQueueManager;
import com.jmex.game.state.BasicGameState;

import core.fight.Character;
import core.fight.Fight;
import core.objects.AbstractObject;
import core.space.World;



public class GraphicFight extends BasicGameState {
	JmeGame game;
	Fight fight; // parita
	CustomCamera camera; // camera

	GraphicCharacter focused;
	ObjectMap objects;

	CountdownTimer uptime;

	public static final float UPTIME = 0.008f;  //intervallo di aggiornamento (in secondi)

	Arena arena;

	public GraphicFight(JmeGame game) {
		super("GraphicFight");
		this.game = game;
		this.fight = game.getFight();
		uptime = new CountdownTimer();
		objects = new ObjectMap();
	}

	public int getLoadingSteps() {
		return fight.numberOfPlayers() + 3;
	}


	public void initGame(int playerId) {
		camera = new CustomCamera();

		for (int i=1;i<=fight.numberOfPlayers();i++) {
			game.getLoading().increment("loading players");
			Character player = fight.getPlayer(i);
			GraphicCharacter graphicCharacter = new GraphicCharacter(player);
			this.objects.put(player, graphicCharacter);
			if (i==playerId) {
				focused = graphicCharacter;
				camera.setFocused(focused);
				player.atDeath(new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						game.showMessage("sei stato sconfitto", false);
						fight.end();
						return null;
					}
				});
				player.atVictory(new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						game.showMessage("Vittoria!", false);
						return null;
					}
				});
			} else new EnemyDeath(player);
		}
		game.getLoading().increment("initializing particle system");

		ExplosionFactory.warmup();

		GameTaskQueueManager.getManager().update(new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				game.getLoading().increment("building environment");
				arena = new Arena();
				attach(arena);
				arena.update(0);
				return null;
			}
		});
		
		game.getLoading().increment();

		uptime.start(UPTIME);

		//this.setActive(true);
	}



	//	@Override
	//	public void setActive(boolean active) {
	//		super.setActive(active);
	//		if (active)
	//			uptime.start(UPTIME);
	//	}

	// aggiornamento
	@Override
	public void update(float tpf) {
		super.update(tpf);
		
		arena.update(tpf);

		if (uptime.expired()) {
			fight.update();
			uptime.start(UPTIME);
		}

		this.updateObjects(tpf);
		this.updateCamera();


	}

	protected void updateCamera() {
		Character enemy = fight.getEnemy(focused.getCoreCharacter());
		GraphicObject target = objects.get(enemy);
		//		GameState camera = GameStateManager.getInstance().getChild("camera");
		//		((ThirdPersonCameraGameState) camera).lookAt(target);
		camera.setTarget(target);
		camera.update();
	}


	protected void updateObjects(float tpf) {

		for (AbstractObject obj : World.getObjects() ) {
			GraphicObject go = this.objects.get(obj);
			if (go==null)
				this.objects.add(obj);
			else if (!go.isInGame()) {
				this.objects.remove(obj);
			}
			else go.update(tpf);
			
			if (!obj.isInGame())
				obj.setToRevome(true);
		}
	}

	public void attach(Spatial child) {
		this.getRootNode().attachChild(child);
	}

	public void detach(Spatial child) {
		this.getRootNode().detachChild(child);
	}


	public class ObjectMap extends HashMap<AbstractObject, GraphicObject> {
		private static final long serialVersionUID = 1L;

		@Override
		public GraphicObject put(AbstractObject key, GraphicObject value) {
			GraphicFight.this.attach(value);
			value.loadModel();
			return super.put(key, value);
		}

		@Override
		public GraphicObject remove(Object key) {
			
			//in order to avoid a concurrence bug
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			GraphicFight.this.detach(this.get(key));
			return super.remove(key);
		}

		public void add(AbstractObject obj) {
			GraphicObject go = GraphicObject.fromObject(obj);
			this.put(obj, go);
		}
	}

	public class EnemyDeath implements Callable<Void> {
		Character enemy;

		public EnemyDeath(Character enemy) {
			super();
			this.enemy = enemy;
			enemy.atDeath(this);
		}

		@Override
		public Void call() throws Exception {
			game.showNotification(enemy.getName()+"è stato sconfitto");
			return null;
		}

	}



}
