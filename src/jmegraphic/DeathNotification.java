package jmegraphic;

import java.util.concurrent.Callable;

import core.fight.Character;

public class DeathNotification implements Callable<Void> {
	JmeGame game;
	Character player;
	
	

	public DeathNotification(JmeGame game, Character player) {
		super();
		this.game = game;
		this.player = player;
		player.atDeath(this);
	}



	@Override
	public Void call() throws Exception {
		game.showNotification(player.getName()+"è stato sconfitto");
		//game.getFight().
		return null;
	}

}
