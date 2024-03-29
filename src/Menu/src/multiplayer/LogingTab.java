package Menu.src.multiplayer;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Menu.src.MainMenu;
import Menu.src.multiplayer.lobby.Lobby;
import Menu.src.multiplayer.lobby.LobbyUtilsFactory;

/**
 * The class represents the tab used by the player to login to the lobby
 * @author Neb
 *
 */
public class LogingTab extends MultiplayerMenuTabs {
	
	Image top;
	
	Lobby lobby;
	
	JTextField user;
	JPasswordField password;
	JButton login;
	
	public LogingTab(MainMenu mainMenu) {
		super(mainMenu);
		
		this.user = new JTextField("Neb");
		this.password = new JPasswordField("lalala");
		
		LobbyUtilsFactory.setLobbyTextFieldParameters(this.user, "Nome Utente");
		LobbyUtilsFactory.setLobbyTextFieldParameters(this.password, "Password");
			
	}

	/**
	 * Creates the panel showing the connecting form
	 */
	public void connecting()
	{
		super.removeAll();
		
		this.top = new ImageIcon(LogingTab.class.getClassLoader().getResource("Menu/data/multiplayer/connect.gif")).getImage();
		
		this.connect = LobbyUtilsFactory.createAnimatedButton("Menu/data/multiplayer/connect1.gif", "Menu/data/multiplayer/connect2.gif");
		this.connect.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				try{					
					String ip = LogingTab.this.serverIp.getText();
					int port = Integer.parseInt(LogingTab.this.serverPort.getText());
					if(LogingTab.this.mainMenu.lobbyClient.connect(ip, port))
						LogingTab.this.checkPanel();
					else
						LogingTab.super.showWarning("Connessione Fallita!");
			
				} catch (UnknownHostException exc) {
					LogingTab.super.showWarning("L'Host sembra essere sconosciuto!");
				} catch (IOException exc) {
					LogingTab.super.showWarning("Errore durante la connessione all'Host!");
				}
				
			}
				
		});
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor =  GridBagConstraints.CENTER;
		c.insets = new Insets(10, 20, 10, 20);
		c.gridx = 0;
		
		c.gridy = 0;
		super.add(this.serverIp, c);

		c.gridy = 1;
		super.add(this.serverPort, c);
		
		c.gridy = 2;
		super.add(this.connect, c);
		
		c.gridy = 3;
		super.add(this.back, c);
		
		super.repaint();
		super.revalidate();
	}
	
	/**
	 * Creates the panel showing the logging form
	 */
	public void logging()
	{
		super.removeAll();
		super.setLayout(new GridBagLayout());

		this.top = new ImageIcon(LogingTab.class.getClassLoader().getResource("Menu/data/multiplayer/login.gif")).getImage();
		
		this.login = LobbyUtilsFactory.createAnimatedButton("Menu/data/multiplayer/login1.gif", "Menu/data/multiplayer/login2.gif");		
		this.login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				System.out.println("EVENTO LOGIN");
				
				String nome = LogingTab.this.user.getText();
				String password = LogingTab.this.password.getText();
				
				if(LogingTab.this.mainMenu.lobbyClient.logIn(nome, password))
				{
					LogingTab.this.lobby = new Lobby(LogingTab.this.mainMenu);					
					LogingTab.this.mainMenu.switchTo(LogingTab.this.lobby);
				} else
					LogingTab.super.showWarning("Login Fallito!");				
			}
			
		});
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;		
		
		c.gridy = 0;
		super.add(this.user, c);
		
		c.gridy = 1;
		super.add(this.password, c);
		
		c.gridy = 2;
		super.add(this.login, c);
		
		c.gridy = 3;
		super.add(this.back, c);
		
		super.repaint();
		super.revalidate();
	}

	/**
	 * Checks if the client is connected to a server a sets the panel to show
	 */
	public void checkPanel()
	{
		if(this.mainMenu.lobbyClient.isConnected())
			this.logging();
		else
			this.connecting();
	}

	public void paintComponent(Graphics g) {
		g.drawImage(this.top, 5, 5, super.getWidth()-10, this.top.getHeight(null), null);
	}

}