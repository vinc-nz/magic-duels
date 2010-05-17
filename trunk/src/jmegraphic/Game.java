package jmegraphic;

import input.CharacterController;

import java.io.IOException;
import java.net.UnknownHostException;

import net.NetGame;
import net.client.ClientGame;
import net.server.ServerGame;
import wiiMoteInput.PlayerMote;
import Menu.src.MainMenu;
import core.fight.Fight;


public class Game extends GraphicFight {
	
	NetGame net = null;
	PlayerMote playerMote;
	MainMenu mainMenu;

	
	public Game(PlayerMote playerMote, MainMenu mainMenu) {
		super();
		this.playerMote = playerMote;
		this.mainMenu = mainMenu;
	}

	@Override
	protected void initSystem() {
		this.creanteDisplay(mainMenu.WIDTH, mainMenu.HEIGHT, mainMenu.fullscreen);
	}
	
	
	public void initNetGame(NetGame game) {
		this.net = game;
		this.fight = game.getFight();
		CharacterController local = null;
		try {
			local = game.getController();
			game.buildListening();
			this.initInput(local);
		} catch (IOException e) {
			System.out.println("errore initNetGame");
			e.printStackTrace();
			System.exit(5);
		}
	}
	
	public void initSingleGame() {
		this.initSingleGame("Player1", 2);
	}	
	
	public void initSingleGame(String name, int numberOfPlayers) {
		this.fight = new Fight(numberOfPlayers);
		this.fight.getPlayer(1).setName(name);
		CharacterController human = new CharacterController(1, fight);
		this.initInput(human);
	}
	
	public void initServerGame(int port) throws IOException {
		this.initNetGame(new ServerGame(port));
	}
	
	public void initServerGame(String name, int numberOfPlayers, int port) throws IOException {
		this.initNetGame(new ServerGame(name, numberOfPlayers, port));
	}
	
	public void initClientGame(String server, int port) throws UnknownHostException, IOException {
		this.initNetGame(new ClientGame(server, port));
	}
	
	public void initClientGame(String name, int numberOfPlayers, int localId, String address, int port) throws UnknownHostException, IOException {
		this.initNetGame(new ClientGame(name, numberOfPlayers, localId, address, port));
	}

	protected void initInput(CharacterController playerController) {
		//this.input = new KeyboardInput(playerController);
		if(playerMote.getMote() == null)
		{
			this.playerMote.findMote();
			this.playerMote.getMote().rumble(1000);
		}
		
		this.playerMote.createPlayingMote(playerController);
/*		
		this.playerMote.getPlayingMote().createButtonListener();
		this.playerMote.getPlayingMote().createAccellerometerListener();
		this.playerMote.getPlayingMote().openAccellerometerListener();
*/
		this.input = this.playerMote.getPlayingMote();
	}
	

	
	@Override
	protected void initGame() {
		super.initGame();
		if (net != null) {
			try {
				net.sayReady();
				net.waitOthers();
			} catch (IOException e) {
				System.out.println("errore initGame");
				e.printStackTrace();
				System.exit(1);
			}
		}
		super.startFight();
	}
	
	@Override
	protected void cleanup() {
		super.cleanup();
		this.playerMote.removePlayingMote();

	}

}
