package jmegraphic;

import input.CharacterController;

import java.io.IOException;

import net.ClientGame;
import net.NetGame;
import net.ServerGame;
import wiiMoteInput.PlayerMote;
import Menu.src.MainMenu;
import core.fight.Fight;


public class Game extends GraphicFight {
	
	NetGame net = null;
	PlayerMote playerMote;

	
	public Game(PlayerMote playerMote, MainMenu mainMenu) {
		super(mainMenu);
		this.playerMote = playerMote;
	}

	
	
	public void initNetGame(NetGame game) {
		this.net = game;
		
		CharacterController local = null;
		try {
			local = game.getController(this.fight);
			game.buildListener(this.fight);
			this.initInput(local);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initSingleGame() {
		
		CharacterController human = new CharacterController(Fight.ID_P1, fight);
		//CharacterController ia = new CharacterController(Fight.ID_P2, fight);
		//new IAStub(ia,fight).start();
		this.initInput(human);
	}
	
	public void initServerGame(int port) {
		this.initNetGame(new ServerGame(port));
	}
	
	public void initClientGame(String server, int port) {
		this.initNetGame(new ClientGame(server, port));
	}

	protected void initInput(CharacterController playerController) {
		//this.input = new KeyboardInput(playerController);
		this.playerMote.findMote();
		this.playerMote.getMote().rumble(1000);
		this.playerMote.createPlayingMote(playerController);
/*		
		this.playerMote.getPlayingMote().createButtonListener();
		this.playerMote.getPlayingMote().createAccellerometerListener();
		this.playerMote.getPlayingMote().openAccellerometerListener();
*/
		this.input = this.playerMote.getPlayingMote();
	}
	
	//per i settaggi video
	public void videoSettings() {
		this.setConfigShowMode(ConfigShowMode.AlwaysShow);
		
		Thread otherThread = new Thread() {
			@Override
			public void run() {
				Game.this.getAttributes();
			}
		};
	
		otherThread.start();
		
		this.setConfigShowMode(ConfigShowMode.ShowIfNoConfig);
	}
	
	@Override
	protected void initGame() {
		super.initGame();
		if (net != null) {
			net.sayReady();
			net.waitOther();
		}
		super.startFight();
	}
	
	@Override
	protected void cleanup() {
		super.cleanup();
		this.playerMote.removePlayingMote();

	}

}
