package jmegraphic.hud;

import java.util.LinkedList;
import java.util.List;

import jmegraphic.GraphicObject;

import com.jme.scene.Node;
import com.jme.util.Timer;

import core.Fight;

public class Countdown {
	Notification text;
	int seconds;
	float lastTime;
	boolean started;
	boolean expired;
	Fight fight;
	
	
	public Countdown(Fight fight, int seconds) {
		super();
		this.seconds = seconds+1;
		this.lastTime = 0;
		this.expired = false;
		this.fight = fight;
		text = new Notification("countdown");
		text.setText(Integer.toString(seconds));
		text.setPosition(HudObject.POSITION_CENTER);
	}
	
	public void start() {
		lastTime = Timer.getTimer().getTimeInSeconds();
		started = true;
	}
	
	
	public void update(Timer timer) {
		if (started && !expired && timer.getTimeInSeconds()-lastTime>=1) {
			seconds--;
			if (seconds == 0) {
				text.setText("Fight!!");
				text.setExpireTime(3);
				expired=true;
				fight.start();
			}
			else {
				lastTime = timer.getTimeInSeconds();
				text.setText(Integer.toString(seconds));
			}
		}
	}

	public Notification getNotification() {
		return text;
	}
	
	

}
