package Menu.src;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import jmegraphic.Game;

public class ImageTest {
  
	public static void main(String[] args) {
		
	    ImagePanel panel = new ImagePanel(new ImageIcon("Menu/data/fantasy.jpg").getImage(),
	    								  new ImageIcon("Menu/data/newGame.png").getImage(),
	    								  new ImageIcon("Menu/data/multiplayer.png").getImage(),
	    								  new ImageIcon("Menu/data/options.png").getImage(),
	    								  new ImageIcon("Menu/data/credits.png").getImage(),
	    								  new ImageIcon("Menu/data/exit.png").getImage(),
	    								  new ImageIcon("Menu/data/selectNewGame.png").getImage(),
	    								  new ImageIcon("Menu/data/selectMultiplayer.png").getImage(),
	    								  new ImageIcon("Menu/data/selectOptions.png").getImage(),
	    								  new ImageIcon("Menu/data/selectCredits.png").getImage(),
	    								  new ImageIcon("Menu/data/selectExit.png").getImage(),
	    								  new ImageIcon("Menu/data/play.png").getImage(),
	    								  new ImageIcon("Menu/data/pause.png").getImage(),
	    								  new Game() /*null*/
	    								  );
	    JFrame frame = new JFrame();
	    frame.getContentPane().add(panel);
	    frame.pack();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);      
  }
}

