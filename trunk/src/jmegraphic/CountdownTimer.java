package jmegraphic;

import com.jme.util.Timer;

public class CountdownTimer {
	float lastTime = 0;
	float expireTime;
	
	public void start(float expireTime) {
		this.expireTime = expireTime;
		lastTime = Timer.getTimer().getTimeInSeconds();
	}
	
	public boolean expired() {
		if (lastTime==0)
			return false;
		return Timer.getTimer().getTimeInSeconds() - lastTime >= expireTime;
	}
	
	public synchronized void notifyExpire() {
		if (expired())
			notify();
	}
	
	public synchronized void waitExpire() {
		if (lastTime>0 && !expired())
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public boolean isActive() {
		return lastTime!=0;
	}

	public void deactivate() {
		lastTime=0;
		
	}
	
	

}
