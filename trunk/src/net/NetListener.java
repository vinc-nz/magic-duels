package net;

import input.CharacterController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class NetListener extends Thread {
	CharacterController controller;
	BufferedReader input;
	
	public NetListener(CharacterController controller, InputStream is) {
		this.controller = controller;
		input = new BufferedReader(new InputStreamReader(is));
	}
	
	public boolean waitReadySignal() throws IOException {
		String message = input.readLine();
		return message.equals("ready");
	}
	
	@Override
	public void run() {
		super.run();
		String trigger;
		while (true) {
			try {
				trigger = input.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
			controller.performAction(trigger);
		}
	}

}
