package jmegraphic;

import java.io.IOException;

import com.jme.app.AbstractGame.ConfigShowMode;

import ia.IAStub;
import input.CharacterController;
import input.InputInterface;
import input.KeyboardInput;
import net.ClientGame;
import net.NetGame;
import net.ServerGame;
import core.Fight;
import core.PlayingCharacter;
import core.Spell;

public class Game extends GraphicFight {
	
	protected void initCharacters() {
		Spell fireball = new Spell("fireball", 5, 5, false, 0, 10);
		PlayingCharacter p1 = new PlayingCharacter("dwarf_red", 100, 50, 5, 5, 2);
		p1.addSpell(fireball);
		PlayingCharacter p2 = new PlayingCharacter("dwarf_white", 100, 50, 5, 5, 2);
		p2.addSpell(fireball);
		this.fight = new Fight(p1, p2);
	}
	
	public void initNetGame(NetGame game) {
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
		this.input = new KeyboardInput(playerController);
	}
	
	//per i settaggi video
	public void videoSettings() {
		this.setConfigShowMode(ConfigShowMode.AlwaysShow);
		this.getAttributes();
		this.setConfigShowMode(ConfigShowMode.ShowIfNoConfig);
	}

	/**
	 * @param args
	 */
	/*public static void main(String[] args) {
		Game game = new Game();
		game.initSingleGame();
		game.start();

	}*/

}
