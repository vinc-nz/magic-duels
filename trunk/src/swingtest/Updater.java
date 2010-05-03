package swingtest;

public class Updater extends Thread {
	
	Battlefiled b;
	
	
	
	public Updater(Battlefiled b) {
		super();
		this.b = b;
	}



	@Override
	public void run() {
		
		while (b.fight.running) {
			b.update();
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
