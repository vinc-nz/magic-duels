package jmegraphic;

import com.jme.app.BaseGame;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.system.DisplaySystem;

public abstract class JmeGame extends BaseGame {
	private Node rootNode;


	@Override
	protected void initGame() {
		KeyBindingManager.getKeyBindingManager().set("exit", KeyInput.KEY_ESCAPE);
		this.rootNode = new Node("root node");

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
		display.getRenderer().draw(rootNode);

	}

	@Override
	protected void update(float interpolation) {
		if (KeyBindingManager.getKeyBindingManager().isValidCommand("exit"))
			finished=true;

		rootNode.updateGeometricState(interpolation, true);
		rootNode.updateRenderState();
	}
	
	protected void attach(Spatial spatial) {
		this.rootNode.attachChild(spatial);
	}
	
	protected void detach(Spatial spatial) {
		this.rootNode.detachChild(spatial);
	}

}
