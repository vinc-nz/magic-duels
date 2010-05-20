package jmegraphic;

import input.InputInterface;

public class TestGame extends GraphicFight {
	
	public TestGame() {
		// TODO Auto-generated constructor stub
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
	
	

}
