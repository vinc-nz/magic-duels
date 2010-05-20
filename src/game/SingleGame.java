package game;

import input.CharacterController;
import wiiMoteInput.PlayerMote;
import Menu.src.MainMenu;

public class SingleGame extends Game {

	public SingleGame(PlayerMote playerMote, MainMenu mainMenu) {
		super(playerMote, mainMenu);
	}
	
	public void init(String name, int numberOfPlayers) {
		super.initFight(numberOfPlayers);
		this.getFight().getPlayer(1).setName(name);
	}
	
	@Override
	protected CharacterController buildCharacterController() {
		return new CharacterController(1, this.getFight());
	}
	
	@Override
	protected void initGame() {
		super.initGame();
		this.startFight();
	}

}
