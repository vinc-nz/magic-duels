package jmegraphic;

import core.fight.Fight;
import game.Event;

public class FightStateChecker extends Thread {
	
	JmeGame game;
	Fight fight;

	public FightStateChecker(JmeGame game) {
		super();
		this.game = game;
		this.fight = game.fight;
	}
	
	@Override
	public void run() {
		while (fight.running) {
			fight.checkState();
			game.checkPause();
			this.checkEvents();
		}
	}

	private void checkEvents() {
		Event e = fight.getFightProblem();
		if (e==Event.WIIMOTE_DISCONNECTED)
			game.showMessage("Wiimote disconnesso");
		else if (e==Event.NO_MANA)
			game.showNotification("No mana!");
		
	}

}
