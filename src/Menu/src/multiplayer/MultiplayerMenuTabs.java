package Menu.src.multiplayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Menu.src.MainMenu;
import Menu.src.multiplayer.lobby.LobbyUtilsFactory;

public abstract class MultiplayerMenuTabs extends JPanel{

	MainMenu mainMenu;
	
	/*
	JLabel serverIp_;
	JLabel serverPort_;
	*/
	
	JTextField serverIp;
	JTextField serverPort;
	
	JButton connect;
	
	public MultiplayerMenuTabs(MainMenu mainMenu)
	{
		super.setOpaque(false);
		super.setBorder(LobbyUtilsFactory.createPanelBorder());
		this.mainMenu = mainMenu;
		/*
		this.serverIp_ = new JLabel("IP Server:");
		this.serverIp_.setOpaque(false);
		this.serverPort_ = new JLabel("Porta Server:");
		this.serverPort_.setOpaque(false);
		*/
		this.serverIp = new JTextField("127.0.0.1");
		this.serverIp.setPreferredSize(new Dimension((int)super.getSize().getWidth()/3*2, 50));
		LobbyUtilsFactory.setLobbyTextFieldParameters(this.serverIp, "IP Server:");
		
		this.serverPort = new JTextField("7000");
		LobbyUtilsFactory.setLobbyTextFieldParameters(this.serverPort, "Porta Server:");
		
		this.connect = new JButton("Connettiti al Server!");
		
		this.checkPanel();		
	}
	
	public void showWarning(String warning) { JOptionPane.showMessageDialog(mainMenu, warning, "Magic Duels Game", JOptionPane.WARNING_MESSAGE); }
	
	public abstract void checkPanel();

}