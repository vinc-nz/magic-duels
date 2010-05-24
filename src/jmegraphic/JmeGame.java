package jmegraphic;

import input.InputInterface;

import java.util.concurrent.Callable;

import jmegraphic.gamestate.CountdownState;
import jmegraphic.gamestate.GraphicFight;
import jmegraphic.gamestate.StatusGameState;
import jmegraphic.gamestate.TimedGameState;
import jmegraphic.hud.HudObject;
import jmegraphic.hud.Notification;

import com.jme.app.BaseGame;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import com.jme.util.GameTaskQueue;
import com.jme.util.GameTaskQueueManager;
import com.jme.util.Timer;
import com.jmex.game.state.BasicGameState;
import com.jmex.game.state.GameStateManager;
import com.jmex.game.state.load.LoadingGameState;

import core.fight.Fight;

public abstract class JmeGame extends BaseGame {

	/** High resolution timer for jME. */
	private Timer timer;

	/** Simply an easy way to get at timer.getTimePerFrame(). */
	private float tpf;

	GameStateManager stateManager;

	LoadingGameState loading;
	GraphicFight fightState;
	StatusGameState status;
	TimedGameState notificationState;

	Fight fight;
	InputInterface input;

	protected abstract InputInterface getInputInterface();



	public LoadingGameState getLoading() {
		return loading;
	}

	public int getLoadingSteps() {
		return fightState.getLoadingSteps();
	}


	public void initFight(int numberOfPlayers) {
		this.fight = Fight.create(numberOfPlayers);
		this.fight.setPauseToggling(new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				checkPause();
				return null;
			}
		});
	}

	public Fight getFight() {
		return fight;
	}

	@Override
	protected void initGame() {
		/** Get a high resolution timer for FPS updates. */
		timer = Timer.getTimer();

		stateManager = GameStateManager.create();

		KeyBindingManager.getKeyBindingManager().set("exit", KeyInput.KEY_ESCAPE);

		input = getInputInterface();

		

		fightState = new GraphicFight(JmeGame.this);
		stateManager.attachChild(fightState);

		status = new StatusGameState(input.getPlayerID(), JmeGame.this);
		stateManager.attachChild(status);

		notificationState = new TimedGameState("notification");
		stateManager.attachChild(notificationState);

		loading = new LoadingGameState(getLoadingSteps(), true) {
			
			@Override
			public void setProgress(float progress) {
				super.setProgress(progress);
				if (progress==1)
					startFight();
			}
		};
		stateManager.attachChild(loading);
		loading.setActive(true);


		Runnable initiator = new Runnable() {

			@Override
			public void run() {
				fightState.initGame(input.getPlayerID());
				status.init();

			}
		};
		new Thread(initiator).start();
	}



	public void startFight() {

		fightState.setActive(true);
		status.setActive(true);
		CountdownState count = new CountdownState(fight, 3);
		stateManager.attachChild(count);
		count.setActive(true);

	}

	@Override
	protected void initSystem() {
		try {
			display=DisplaySystem.getDisplaySystem();
			display.createWindow(settings.getWidth(), 
					settings.getHeight(), 
					settings.getDepth(), 
					settings.getFrequency(), 
					settings.isFullscreen());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		display.setTitle("MagicDuels");
		display.getRenderer().setBackgroundColor(ColorRGBA.black);
		
		Camera camera = display.getRenderer().createCamera(display.getWidth(), 
													display.getHeight());
		float ratio = (float)display.getWidth()/(float)display.getHeight();
		camera.setFrustumPerspective(45.0f, ratio, 1, 5000);
		camera.update();
		display.getRenderer().setCamera(camera);
	}

	

	@Override
	protected void render(float interpolation) {
		if (fightState.isActive() || loading.isActive())
			display.getRenderer().clearBuffers();

		// Execute renderQueue item
		GameTaskQueueManager.getManager().getQueue(GameTaskQueue.RENDER).execute();

		// Render the GameStates
		GameStateManager.getInstance().render(tpf);

	}

	@Override
	protected void update(float interpolation) {
		if (KeyBindingManager.getKeyBindingManager().isValidCommand("exit"))
			this.finish();


		// Recalculate the framerate.
		timer.update();
		tpf = timer.getTimePerFrame();

		GameTaskQueueManager.getManager().getQueue(GameTaskQueue.UPDATE).execute();
		// Update the current game state.
		GameStateManager.getInstance().update(tpf);
	}


	@Override
	protected void cleanup() {
		GameStateManager.getInstance().cleanup();

		DisplaySystem.getDisplaySystem().getRenderer().cleanup();

		fight.end();
	}


	@Override
	protected void reinit() {
		// TODO Auto-generated method stub

	}

	public void checkPause() {
		if (!fightState.isActive() && !fight.paused()) {
			GameStateManager.getInstance().detachChild("message");
			fightState.setActive(true);
		}
		else if (fightState.isActive() && fight.paused()) {
			this.showMessage("pausa",true);
			fightState.setActive(false);
		}
	}

	public void showMessage(String text, boolean stopFight) {
		Notification notification = new Notification(text);
		notification.setPosition(HudObject.POSITION_CENTER);
		BasicGameState state = new BasicGameState("message");
		state.getRootNode().attachChild(notification);
		stateManager.attachChild(state);
		state.setActive(true);
		if (stopFight)
			fightState.setActive(false);
	}

	public void showNotification(String text) {
		Notification notification = new Notification(text);
		notification.setPosition(HudObject.POSITION_BOTTOM);
		notificationState.getRootNode().attachChild(notification);
		notificationState.show();
	}



}
