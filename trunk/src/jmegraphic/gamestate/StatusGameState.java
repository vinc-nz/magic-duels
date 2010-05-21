package jmegraphic.gamestate;

import jmegraphic.hud.HudObject;
import jmegraphic.hud.Notification;
import jmegraphic.hud.StatusBars;

import com.jmex.game.state.BasicGameState;

import core.fight.Character;
import core.fight.Fight;

public class StatusGameState extends BasicGameState {
	Fight fight;
	Character player;
	StatusBars playerBars;
	StatusBars enemyBar;
	Notification noMana;
	
	public StatusGameState(int playerId, Fight fight) {
		super("status");
		this.fight = fight;
		this.player = fight.getPlayer(playerId);
		
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
	}
	
	

}
