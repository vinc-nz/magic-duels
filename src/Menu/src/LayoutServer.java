package Menu.src;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import jmegraphic.Game;


public class LayoutServer extends JPanel {

	JLabel port;
	JTextField portText;
	JButton buttOK;
	JButton buttCancel;
	
	MainMenu mainMenu;
	MainPanel mainPanel;
	MenuMultiplayer menuMultiplayer;
	Image background;
	Game game;
	
	public String numPort;

	public LayoutServer(Game game, MainMenu mainMenu, MainPanel mainPanel, MenuMultiplayer menuMultiplayer) { 	
		
		this.game = game;
		this.mainMenu = mainMenu;
		this.mainPanel = mainPanel;
		this.menuMultiplayer = menuMultiplayer;
		
		background = new ImageIcon("src/Menu/data/imageServer.jpg").getImage();
		port = new JLabel("PORT  ", SwingConstants.RIGHT);
		portText = new JTextField("00000");
	
		
		buttOK = new JButton("OK");
		buttCancel = new JButton("CANCEL");
	
		portText.setEditable(true);
		
		add(port);
		add(portText);
		
		add(buttOK);
		add(buttCancel);
		
		buttOK.addActionListener(new Action(game, portText, this, mainMenu, mainPanel, menuMultiplayer));
		buttCancel.addActionListener(new Action(game, portText, this, mainMenu, mainPanel, menuMultiplayer));
		
		setVisible(true);
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage( background, 0, 0, this.getWidth(), this.getHeight(), null);
	}
	
	class Action implements ActionListener{
		Game game;
		JTextField portText;
		LayoutServer serv;
		MainMenu mainMenu;
		MainPanel mainPanel;
		MenuMultiplayer menuMultiplayer;
		
		public Action(Game game, JTextField portText, LayoutServer serv, MainMenu mainMenu, MainPanel mainPanel,
				      MenuMultiplayer menuMultiplayer){
			this.game = game;
			this.mainMenu = mainMenu;
			this.mainPanel = mainPanel;
			this.portText = portText;
			this.serv = serv;
			this.menuMultiplayer = menuMultiplayer;
		}
		
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("OK")) {
				game.initServerGame(Integer.parseInt(portText.getText()));
				mainMenu.switchTo(mainPanel);
				game.start();
				
				
			}
			if (e.getActionCommand().equals("CANCEL")) {
				mainMenu.switchTo(menuMultiplayer);
			}
		}
	}
}





