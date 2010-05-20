package jmegraphic;

import core.fight.Fight;
import game.Error;

public class FightStateChecker extends Thread {
	
	GraphicFight graphicFight;
	Fight coreFight;

	public FightStateChecker(GraphicFight graphicFight) {
		super();
		this.graphicFight = graphicFight;
		this.coreFight = graphicFight.fight;
	}
	
	@Override
	public void run() {
		while (coreFight.running) {
			coreFight.checkState();
			graphicFight.checkPause();
			this.checkErrors();
		}
	}

	private void checkErrors() {
		Error e = coreFight.getFightError();
		if (e==Error.HOST_UNREACHABLE)
			graphicFight.notifyHostUnreachable();
		else if (e==Error.WIIMOTE_DISCONNECTED)
			graphicFight.notifyWiimoteDisconnected();
	}

}
