package Menu.src.multiplayer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Menu.src.MainMenu;
import Menu.src.multiplayer.lobby.Lobby;
import Menu.src.multiplayer.lobby.LobbyUtilsFactory;

public class LogingTab extends MultiplayerMenuTabs {
	
	JTextField user;
	JPasswordField password;
	
	JButton login;
	
	public LogingTab(MainMenu mainMenu) {
		super(mainMenu);
		
		this.user = new JTextField("Neb");
		this.password = new JPasswordField("lalala");
		
		LobbyUtilsFactory.setLobbyTextFieldParameters(this.user, "Nome Utente");
		LobbyUtilsFactory.setLobbyTextFieldParameters(this.password, "Password");
		
		this.login = new JButton("Entra nella Lobby");

	}

	public void connecting()
	{
		super.removeAll();
		super.setLayout(new GridBagLayout());
		
		this.connect.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {

				String ip = LogingTab.this.serverIp.getText();
				int port = Integer.parseInt(LogingTab.this.serverPort.getText());
				if(LogingTab.this.mainMenu.lobbyClient.connect(ip, port))
					LogingTab.this.checkPanel();
				else
					LogingTab.super.showWarning("Connessione Fallita!");
			}
				
		});
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;
		
		//super.add(this.serverIp_);
		c.gridy = 0;
		super.add(this.serverIp, c);
		//super.add(this.serverPort_);
		c.gridy = 1;
		super.add(this.serverPort, c);
		
		c.gridy = 2;
		super.add(this.connect, c);
		
		c.gridy = 3;
		super.add(this.connect, c);
		
		super.repaint();
		super.revalidate();
	}
	
	public void logging()
	{
		super.removeAll();
		super.setLayout(new GridBagLayout());

		this.login.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {

				String nome = LogingTab.this.user.getText();
				String password = LogingTab.this.password.getText();
				
				if(LogingTab.this.mainMenu.lobbyClient.logIn(nome, password))
					LogingTab.this.mainMenu.switchTo(new Lobby(LogingTab.this.mainMenu));
				else
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
		
		super.repaint();
		super.revalidate();
	}

	public void checkPanel()
	{
		if(this.mainMenu.lobbyClient.isConnected())
			this.logging();
		else
			this.connecting();
	}
}
