package game;

import input.CharacterController;
import input.InputInterface;
import jmegraphic.JmeGame;
import wiiMoteInput.PlayerMote;
import Menu.src.MainMenu;


public abstract class Game extends JmeGame {
	PlayerMote playerMote = null;
	MainMenu mainMenu = null;

	
	
	public Game(PlayerMote playerMote, MainMenu mainMenu) {
		super();
		this.playerMote = playerMote;
		this.mainMenu = mainMenu;
	}

	@Override
	protected void initSystem() {
		if (mainMenu!=null) {
			settings.setWidth(mainMenu.WIDTH);
			settings.setHeight(mainMenu.HEIGHT);
			settings.setFullscreen(mainMenu.fullscreen);
		}
		super.initSystem();
	}
	

//	protected void initInput(CharacterController playerController) {
//		//this.input = new KeyboardInput(playerController);
//		if(playerMote.getMote() == null)
//		{
//			this.playerMote.findMote();
//			this.playerMote.getMote().rumble(1000);
//		}
//		
//		this.playerMote.createPlayingMote(playerController);
///*		
//		this.playerMote.getPlayingMote().createButtonListener();
//		this.playerMote.getPlayingMote().createAccellerometerListener();
//		this.playerMote.getPlayingMote().openAccellerometerListener();
//*/
//		this.input = this.playerMote.getPlayingMote();
//	}
	

	protected void initInput() {
		if (playerMote!=null)
			this.playerMote.createPlayingMote(this.buildCharacterController());
	}
	
	protected abstract CharacterController buildCharacterController();

	@Override
	protected InputInterface getInputInterface() {
		return this.playerMote.getPlayingMote();
	}
	
	
	@Override
	protected void cleanup() {
		super.cleanup();
		this.playerMote.removePlayingMote();

	}
	
	@Override
	protected void initGame() {
		this.initInput();
		super.initGame();
	}

}
