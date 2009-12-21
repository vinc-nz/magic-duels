package ia;

import java.util.Random;

import core.Fight;

import input.CharacterController;

public class IAStub extends Thread {
	static final int UPTIME=9;
	CharacterController controller;
	Fight fight;
	
	
	public IAStub(CharacterController controller, Fight fight) {
		super();
		this.controller = controller;
		this.fight = fight;
	}
	
	@Override
	public void run() {
		super.run();
		try {
			while (!fight.running)
				sleep(UPTIME);
			this.act();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void act() throws InterruptedException {
		while (fight.running) {
			if (Math.random() > 0.8)
				controller.performAction("move>left");
			controller.performAction("move>forward");
			sleep(UPTIME);
		}
	}
	
	
	
	

}
