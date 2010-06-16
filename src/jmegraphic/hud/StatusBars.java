package jmegraphic.hud;


import utils.ProgressBar;

import com.jme.renderer.ColorRGBA;

import core.fight.Character;


/*
 * BARRE DI STATO (vita e mana)
 */
public class StatusBars extends HudObject {
	ProgressBar lifeBar;
	ProgressBar manaBar;
	Notification characterName;
	Character coreCharacter;

	
	public StatusBars(Character coreCharacter, boolean lifeVisible, boolean manaVisible, boolean nameVisible) {
		super("bars");
		this.coreCharacter = coreCharacter;
		
		
		
		manaBar = new ProgressBar(display,"data/images/bluebar.png");
		lifeBar = new ProgressBar(display, "data/images/greenbar.png");
		characterName = new Notification(coreCharacter.getName());
		
		setupLifeBar();
		setupManaBar();
		setupName();
		
		this.width = lifeBar.getWidth()*BORDER_OFFSET;
		this.height = (lifeBar.getHeight()+manaBar.getHeight()/2)*BORDER_OFFSET;
		
		if (lifeVisible)
			this.attachChild(lifeBar.getNode());
		if (manaVisible)
			this.attachChild(manaBar.getNode());
		if (nameVisible)
			this.attachChild(characterName);
		this.setLightCombineMode(LightCombineMode.Off);
	}
	
	protected void setupName() {
		characterName.setLocalScale(0.5f);
		characterName.setColour(ColorRGBA.black);
		characterName.getLocalTranslation().y-=lifeBar.getHeight()/4;
	}

	
	
	protected void setupManaBar() {
		manaBar.setMinimum(0);
		manaBar.setMaximum(coreCharacter.getMana());
		manaBar.setValue(manaBar.getMaximum());
		manaBar.setScale((display.getWidth() / 200),
                (display.getHeight() / 500));
		manaBar.setPosition(0, -manaBar.getHeight());
	}
	
	protected void setupLifeBar() {
		lifeBar.setMinimum(0);
		lifeBar.setMaximum(coreCharacter.getLife());
		lifeBar.setValue(lifeBar.getMaximum());
		lifeBar.setScale((display.getWidth() / 150), 
					(display.getHeight() / 300));
	}
	
	

	

	public void setCoreCharacter(Character coreCharacter) {
		this.coreCharacter = coreCharacter;
	}

	@Override
	public void update(float tpf) {
		super.update(tpf);
		manaBar.setValue(coreCharacter.getMana());
		lifeBar.setValue(coreCharacter.getLife());
		characterName.setText(coreCharacter.getName());
		characterName.update(tpf);
	}

}
