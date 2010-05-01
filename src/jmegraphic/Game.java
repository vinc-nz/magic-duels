package jmegraphic;

import ia.IAStub;
import input.CharacterController;
import input.KeyboardInput;

import java.io.IOException;

import Menu.src.MainMenu;

import wiiMoteInput.PlayerMote;
import wiiMoteInput.PlayerMoteButtonListener;

import net.ClientGame;
import net.NetGame;
import net.ServerGame;
import core.Fight;
import core.PlayingCharacter;
import core.Spell;

public class Game extends GraphicFight {
	
	NetGame net = null;
	PlayerMote playerMote;

	
	public Game(PlayerMote playerMote, MainMenu mainMenu) {
		super(mainMenu);
		this.playerMote = playerMote;
	}

	protected void initCharacters() {
		Spell fireball = new Spell("Fireball", 5, 5, false, 0, 10);
		PlayingCharacter p1 = new PlayingCharacter("RedDwarf", 100, 50, 5, 5, 2);
		p1.addSpell(fireball);
		PlayingCharacter p2 = new PlayingCharacter("WhiteDwarf", 100, 50, 5, 5, 2);
		p2.addSpell(fireball);
		this.fight = new Fight(p1, p2);
	}
	
	public void initNetGame(NetGame game) {
		this.net = game;
		this.initCharacters();
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
		this.initCharacters();
		CharacterController human = new CharacterController(Fight.ID_P1, fight);
		CharacterController ia = new CharacterController(Fight.ID_P2, fight);
		new IAStub(ia,fight).start();
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
