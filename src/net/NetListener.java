package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public abstract class NetListener extends Thread {
	
	BufferedReader input;
	boolean running = false;
	
	public NetListener(InputStream is) {
		input = new BufferedReader(new InputStreamReader(is));
	}
	
	public boolean waitReadySignal() throws IOException {
		String message = input.readLine();
		return message.equals("ready");
	}
	
	@Override
	public synchronized void start() {
		this.running = true;
		super.start();
	}
	
	@Override
	public void run() {
		super.run();
		String trigger = null;
		while (listening(true)) {
			try {
				trigger = input.readLine();
				this.performAction(trigger);
			} catch (IOException e) {
				e.printStackTrace();
				running = false;
				System.out.println("errore del listener");
				System.exit(2);
			}
		}
	}
	
	private synchronized boolean listening(boolean continueRunning) {
		if (running && !continueRunning)
			running = false;
		return running;
	}
	
	

	protected abstract void performAction(String trigger) throws IOException;

}
