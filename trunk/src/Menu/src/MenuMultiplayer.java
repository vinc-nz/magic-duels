package Menu.src;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import jmegraphic.Game;

public class MenuMultiplayer extends JPanel  {
	
	JButton buttOne; 
	JButton buttTwo;
	JButton buttBack;
	
	Game game;
	MainMenu mainMenu;
	MainPanel mainPanel;
	Image background;

	public MenuMultiplayer(Game game, MainMenu mainMenu, MainPanel mainPanel) {
		
		background = new ImageIcon("src/Menu/data/sfida.jpg").getImage();
		
		this.game = game;
		this.mainMenu = mainMenu;
		this.mainPanel = mainPanel;
		
		buttOne = new JButton("SERVER"); 
		buttTwo = new JButton("CLIENT");
		buttBack = new JButton("BACK MENU");
		

		add(buttOne); 
		add(buttTwo);
		add(buttBack);
		
		buttOne.addActionListener(new ButtonListener(game, mainMenu, mainPanel, this));
		buttTwo.addActionListener(new ButtonListener(game, mainMenu, mainPanel, this));
		buttBack.addActionListener(new ButtonListener(game,  mainMenu, mainPanel, this));
		
		
		setVisible(true); 
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage( background, 0, 0, this.getWidth(), this.getHeight(), null);
	}
	
}

class ButtonListener implements ActionListener {
	LayoutServer ls;
	LayoutClient lc;
	
	Game game;
	MenuMultiplayer menuMultiplayer;
	MainMenu mainMenu;
	MainPanel mainPanel;
	
	public ButtonListener(Game game, MainMenu mainMenu, MainPanel mainPanel, MenuMultiplayer menuMultiplayer){
		this.game = game;
		this.mainMenu = mainMenu;
		this.mainPanel = mainPanel;
		this.menuMultiplayer = menuMultiplayer;
		
	}
	
	public void actionPerformed(ActionEvent e)
	{    
	
		if (e.getActionCommand().equals("SERVER")) {
			mainMenu.switchTo(new LayoutServer(game, mainMenu, mainPanel, menuMultiplayer));
	    }
	    
		if (e.getActionCommand().equals("CLIENT")) {
			mainMenu.switchTo(new LayoutClient(game, mainMenu, mainPanel, menuMultiplayer));
	    }
		
		if (e.getActionCommand().equals("BACK MENU")) {
			mainMenu.switchTo(mainPanel);
	    }
	 
	}
}