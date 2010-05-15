package jmegraphic.hud;


import utils.ProgressBar;
import core.fight.Character;


/*
 * BARRE DI STATO (vita e mana)
 */
public class StatusBars extends HudObject {
	ProgressBar lifeBar;
	ProgressBar manaBar;
	Character coreCharacter;

	
	public StatusBars(Character coreCharacter, boolean lifeVisible, boolean manaVisible) {
		super("bars");
		this.coreCharacter = coreCharacter;
		
		
		
		manaBar = new ProgressBar(display,"data/images/bluebar.png");
		lifeBar = new ProgressBar(display, "data/images/greenbar.png");
		
		setupLifeBar();
		setupManaBar();
		
		this.width = lifeBar.getWidth()*BORDER_OFFSET;
		this.height = (lifeBar.getHeight()+manaBar.getHeight()/2)*BORDER_OFFSET;
		
		if (lifeVisible)
			this.attachChild(lifeBar.getNode());
		if (manaVisible)
			this.attachChild(manaBar.getNode());
		this.setLightCombineMode(LightCombineMode.Off);
	}
	
	@Override
	public void loadModel() {
		
		
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

	@Override
	public void update() {
		manaBar.setValue(coreCharacter.getMana());
		lifeBar.setValue(coreCharacter.getLife());
	}

}