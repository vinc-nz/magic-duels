package Menu.src.lobby;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Menu.src.MainMenu;

public abstract class MultiplayerMenuTabs extends JPanel{

	MainMenu mainMenu;
	
	JLabel serverIp_;
	JLabel serverPort_;
	
	JTextField serverIp;
	JTextField serverPort;
	
	JButton connect;
	
	public MultiplayerMenuTabs(MainMenu mainMenu) {
	
		this.mainMenu = mainMenu;
		
		this.serverIp_ = new JLabel("IP Server:");
		this.serverPort_ = new JLabel("Porta Server:");
		
		this.serverIp = new JTextField("127.0.0.1");
		this.serverPort = new JTextField("7000");
		
		this.connect = new JButton("Connettiti al Server!");
		
		this.checkPanel();
				
	}
	
	public abstract void checkPanel();

}