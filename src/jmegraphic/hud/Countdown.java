package jmegraphic.hud;

import com.jme.util.Timer;

import core.fight.Fight;



public class Countdown extends Notification {
	int seconds;
	float lastTime;
	boolean started;
	boolean expired;
	Fight fight;
	
	
	public Countdown(Fight fight, int seconds) {
		super("countdown");
		this.seconds = seconds+1;
		this.lastTime = 0;
		this.expired = false;
		this.fight = fight;
		this.setText(Integer.toString(seconds));
		this.setPosition(HudObject.POSITION_CENTER);
	}
	
	public void start() {
		lastTime = Timer.getTimer().getTimeInSeconds();
		started = true;
	}
	
	
	public void update() {
		float currentTime = Timer.getTimer().getTimeInSeconds();
		if (started && !expired && currentTime-lastTime>=1) {
			seconds--;
			if (seconds == 0) {
				this.setText("Fight!!");
				this.setExpireTime(3);
				expired=true;
				fight.start();
			}
			else {
				lastTime = currentTime;
				this.setText(Integer.toString(seconds));
			}
		}
		super.update();
	}
	
	

}
