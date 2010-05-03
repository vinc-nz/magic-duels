package swingtest;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import core.fight.Character;
import core.fight.Fight;

public class TestListener implements KeyListener {
	
	Fight fight;
	
	

	public TestListener(Fight fight) {
		super();
		this.fight = fight;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("call");
		if (e.getKeyCode() == KeyEvent.VK_UP)
			fight.moveCharacter(0, "forward");
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
			fight.moveCharacter(0, "backward");

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		System.out.println("call");

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		System.out.println("call");

	}

}
