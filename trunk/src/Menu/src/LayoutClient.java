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

public class LayoutClient extends JPanel {

	JLabel ipLabel;
	JLabel port;
	
	JTextField ipText;
	JTextField portText;
	JButton buttOk;
	JButton buttCancel;
	
	public String numIp;
	public String numPort;
	
	MainMenu mainMenu;
	MainPanel mainPanel;
	MenuMultiplayer menuMultiplayer;
	Image background;
	Game game;

	public LayoutClient(Game game, MainMenu mainMenu, MainPanel mainPanel, MenuMultiplayer menuMultiplayer) {
		
		this.game = game;
		this.mainMenu = mainMenu;
		this.mainPanel = mainPanel;
		this.menuMultiplayer = menuMultiplayer;
		
		background = new ImageIcon("src/Menu/data/imageServer.jpg").getImage();
		
		JLabel ipLabel = new JLabel("IP  ", SwingConstants.LEFT);
		JLabel port = new JLabel("PORT  ", SwingConstants.LEFT);
		
		JTextField ipText = new JTextField("000.000.000.000");
		JTextField portText = new JTextField("00000");
		JButton buttOk = new JButton("OK");
		JButton buttCancel = new JButton("CANCEL");
		
		ipText.setEditable(true);
		portText.setEditable(true);
		
		add(ipLabel);
		add(ipText);
		add(port);
		add(portText);
		add(buttOk);
		add(buttCancel);
		
		buttOk.addActionListener(new Action(game, ipText, portText, this, mainMenu, mainPanel, menuMultiplayer));
		buttCancel.addActionListener(new Action(game, ipText, portText, this, mainMenu, mainPanel, menuMultiplayer));
		
		
		setVisible(true);
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage( background, 0, 0, this.getWidth(), this.getHeight(), null);
	}
	
	class Action implements ActionListener {
		Game game;
		JTextField ipText;
		JTextField portText;
		LayoutClient client;
		MainMenu mainMenu;
		MainPanel mainPanel;
		MenuMultiplayer menuMultiplayer;
		
		public Action(Game game, JTextField ipText, JTextField portText, LayoutClient client,
					  MainMenu mainMenu, MainPanel mainPanel, MenuMultiplayer menuMultiplayer){
			this.game = game;
			this.mainMenu = mainMenu;
			this.mainPanel = mainPanel;
			this.menuMultiplayer = menuMultiplayer;
			this.ipText = ipText;
			this.portText = portText;
			this.client = client;
		}
		
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("OK")) {
				game.initClientGame(ipText.getText(), Integer.parseInt(portText.getText()));
				game.start();
			}
			
			if (e.getActionCommand().equals("CANCEL")) {
				mainMenu.switchTo(menuMultiplayer);
			}
		}
	}
}


