package jmegraphic.gamestate;

import jmegraphic.CountdownTimer;
import jmegraphic.hud.HudObject;
import jmegraphic.hud.Notification;

import com.jmex.game.state.BasicGameState;
import com.jmex.game.state.GameStateManager;

import core.fight.Fight;



public class CountdownState extends BasicGameState {
	int seconds;
	Fight fight;
	Notification count;
	CountdownTimer timer;
	boolean finish;
	
	public CountdownState(Fight fight, int seconds) {
		super("countdown");
		this.seconds = seconds+1;
		this.fight = fight;
		this.finish = false;
		
		count = new Notification(Integer.toString(seconds));
		count.setPosition(HudObject.POSITION_CENTER);
		getRootNode().attachChild(count);
		timer = new CountdownTimer();
	}
	
	@Override
	public void setActive(boolean active) {
		super.setActive(active);
		if (active)
			timer.start(1);
	}
	
	
	@Override
	public void update(float tpf) {
		super.update(tpf);
		if (timer.expired()) {
			if (!finish) {
				seconds--;
				if (seconds == 0) {
					count.setText("Fight!!");
					finish = true;
					timer.start(3);
					fight.start();
				}
				else {
					timer.start(1);
					count.setText(Integer.toString(seconds));
				}
				count.update(tpf);
			}
			else GameStateManager.getInstance().detachChild(this);
		}
	}
	
	

}
