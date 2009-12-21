package jmegraphic;


import utils.ProgressBar;

import com.jme.renderer.Renderer;
import com.jme.system.DisplaySystem;

import core.PlayingCharacter;


/*
 * BARRE DI STATO (vita e mana)
 */
public class StatusBars extends GraphicObject {
	DisplaySystem display;
	ProgressBar lifeBar;
	ProgressBar manaBar;
	PlayingCharacter coreCharacter;

	
	public StatusBars(PlayingCharacter coreCharacter, boolean lifeVisible, boolean manaVisible) {
		super("bars");
		display = DisplaySystem.getDisplaySystem();
		this.coreCharacter = coreCharacter;
		
		manaBar = new ProgressBar(display,"data/images/bluebar.png");
		lifeBar = new ProgressBar(display, "data/images/greenbar.png");
		
		setupLifeBar();
		setupManaBar();
		
		
		this.setRenderQueueMode(Renderer.QUEUE_ORTHO);
		if (lifeVisible)
			this.attachChild(lifeBar.getNode());
		if (manaVisible)
			this.attachChild(manaBar.getNode());
		this.setLightCombineMode(LightCombineMode.Off);
		
	}
	
	protected void setupManaBar() {
		manaBar.setMinimum(0);
		manaBar.setMaximum(coreCharacter.getMana());
		manaBar.setValue(manaBar.getMaximum());
		manaBar.setScale((display.getWidth() / 200),
                (display.getHeight() / 500));
		manaBar.setPosition(lifeBar.getWidth(), manaBar.getHeight());
	}
	
	protected void setupLifeBar() {
		lifeBar.setMinimum(0);
		lifeBar.setMaximum(coreCharacter.getLife());
		lifeBar.setValue(lifeBar.getMaximum());
		lifeBar.setScale((display.getWidth() / 150), 
					(display.getHeight() / 300));
		lifeBar.setPosition(lifeBar.getWidth(), lifeBar.getHeight());
	}

	@Override
	public void update() {
		manaBar.setValue(coreCharacter.getMana());
		lifeBar.setValue(coreCharacter.getLife());
	}

}
