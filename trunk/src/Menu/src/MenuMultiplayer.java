package Menu.src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import jmegraphic.Game;

public class MenuMultiplayer extends JFrame {
	
	JButton buttOne; 
	JButton buttTwo;
	
	Game game;

	public MenuMultiplayer(Game game) {
		super("Setting Multiplayer");
		buttOne = new JButton("SERVER"); 
		buttTwo = new JButton("CLIENT");
		
		this.game = game;
		
		setSize(300, 80);
		setLocation(500,500);

		JPanel panel = new JPanel();
		panel.add(buttOne); 
		panel.add(buttTwo);
		buttOne.addActionListener(new ButtonListener(game));
		buttTwo.addActionListener(new ButtonListener(game));
		
		setContentPane(panel); 
		show(); 
	}

}

class ButtonListener implements ActionListener {
	LayoutServer ls;
	LayoutClient lc;
	
	Game game;
	
	public ButtonListener(Game game){
		this.game = game;
	}
	
	public void actionPerformed(ActionEvent e) {    
	
		if (e.getActionCommand().equals("SERVER")) {
			ls = new LayoutServer(game);
	    }
	    
		if (e.getActionCommand().equals("CLIENT")) {
			lc = new LayoutClient(game);
	    }
	 }
}
