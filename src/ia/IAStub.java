package ia;

import java.util.Random;

import input.CharacterController;

public class IAStub extends Thread {
	static final int UPTIME=9;
	CharacterController controller;
	
	
	public IAStub(CharacterController controller) {
		super();
		this.controller = controller;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true) {
			if (Math.random() > 0.8)
				controller.performAction("move>left");
			controller.performAction("move>forward");
			try {
				sleep(UPTIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	

}
