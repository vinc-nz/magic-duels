package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public abstract class NetListener extends Thread {
	
	BufferedReader input;
	
	public NetListener(InputStream is) {
		input = new BufferedReader(new InputStreamReader(is));
	}
	
	public boolean waitReadySignal() throws IOException {
		String message = input.readLine();
		return message.equals("ready");
	}
	
	
	
	@Override
	public void run() {
		boolean running = true;
		String trigger = null;
		while (running) {
			try {
				trigger = input.readLine();
				this.performAction(trigger);
			} catch (IOException e) {
				running = false;
			}
		}
	}
	
	public abstract NetGame getGame();
	

	protected void performAction(String message) {
		if (Message.someoneLeaves(message))
			this.getGame().notifyLeaving(Message.getPlayerId(message));
		
	}

}
