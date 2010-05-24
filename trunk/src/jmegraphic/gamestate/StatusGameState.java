package jmegraphic.gamestate;

import jmegraphic.JmeGame;
import jmegraphic.hud.HudObject;
import jmegraphic.hud.StatusBars;

import com.jmex.game.state.BasicGameState;

import core.fight.Character;
import core.fight.Fight;

public class StatusGameState extends BasicGameState {
	Fight fight;
	Character player;
	StatusBars playerBars;
	StatusBars enemyBar;
	JmeGame game;
	
	public StatusGameState(int playerId, JmeGame game) {
		super("status");
		this.game = game;
		this.fight = game.getFight();
		this.player = fight.getPlayer(playerId);

	}
	
	public void init() {
		playerBars = new StatusBars(player,true,true,false);
		playerBars.setPosition(HudObject.POSITION_BOTTOM_LEFT);
		getRootNode().attachChild(playerBars);
		
		enemyBar = new StatusBars(fight.getEnemy(player), true, false, true);
		enemyBar.setPosition(HudObject.POSITION_UPPER_RIGHT);
		getRootNode().attachChild(enemyBar);
	}
	
	@Override
	public void update(float tpf) {
		super.update(tpf);
		playerBars.update(tpf);
		enemyBar.setCoreCharacter(fight.getEnemy(player));
		enemyBar.update(tpf);
		
		if (player.notEnoughMana())
			game.showNotification("No mana");
	}
	
	

}
