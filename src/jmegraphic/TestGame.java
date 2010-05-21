package jmegraphic;

import input.InputInterface;

public class TestGame extends JmeGame {
	
	public TestGame() {
		this.initFight(2);
	}


	@Override
	protected InputInterface getInputInterface() {
		
		return new InputInterface() {
			
			@Override
			public int getPlayerID() {
				return 1;
			}
		};
	}
	
	@Override
	protected void initGame() {
		super.initGame();
		this.startFight();
	}
	
	public static void main(String[] args) {
		TestGame game = new TestGame();
		game.setConfigShowMode(ConfigShowMode.AlwaysShow);
		game.start();
	}

}
