package game;

import ia.Ia;
import input.CharacterController;
import wiiMoteInput.PlayerMote;
import Menu.src.MainMenu;

public class SingleGame extends Game {
	String name;

	public SingleGame(PlayerMote playerMote, MainMenu mainMenu) {
		super(playerMote, mainMenu);
	}
	
	public void init(String name, int numberOfPlayers) {
		super.initFight(numberOfPlayers);
		
		for(int i=2; i<=numberOfPlayers; i++){
			new Ia(new CharacterController(i)).start();
		}
		
		this.name = name;
	}
	
	@Override
	protected CharacterController buildCharacterController() {
		return new CharacterController(1);
	}
	
	@Override
	protected void initGame() {
		super.initGame();
		this.getFight().getPlayer(1).setName(name);
		this.startFight();
	}

}
