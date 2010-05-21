package jmegraphic.gamestate;

import jmegraphic.CountdownTimer;

import com.jmex.game.state.BasicGameState;

public class TimedGameState extends BasicGameState {
	
	private static final float DURATION = 3;
	
	CountdownTimer compareTimer = new CountdownTimer();
	
	
	
	public TimedGameState(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public void show() {
		compareTimer.start(DURATION);
		setActive(true);
	}
	
	public void show(float expireTime) {
		compareTimer.start(expireTime);
		setActive(true);
	}
	
	@Override
	public void update(float tpf) {
		super.update(tpf);
		if (compareTimer.expired()) {
			getRootNode().detachAllChildren();
			setActive(false);
		}
	}

}
