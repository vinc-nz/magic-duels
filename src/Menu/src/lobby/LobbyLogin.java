package Menu.src.lobby;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Menu.src.MainMenu;

public class LobbyLogin extends JPanel {

	MainMenu mainMenu;
	
	JTextField serverIp;
	JTextField serverPort;
	JTextField user;
	JPasswordField password;
	
	JButton connect;
	JButton login;
	
	JPanel centerPanel;
		
	public LobbyLogin(MainMenu mainMenu) {
			
		this.mainMenu = mainMenu;
				
		this.serverIp = new JTextField("127.0.0.1");
		this.serverPort = new JTextField("7000");
		this.user = new JTextField("Neb");
		this.password = new JPasswordField("lalala");
		
		this.login = new JButton("Effettua il LogIn");
		this.connect = new JButton("Connetti al Server");
		
		this.checkPanel();
		
	}
	
	public void connecting()
	{
		super.removeAll();
		super.setLayout(new BorderLayout());

		this.centerPanel = new JPanel();
		this.centerPanel = new JPanel(new GridLayout(2, 2));
		
		this.centerPanel.add(new JLabel("IP Server:"));
		this.centerPanel.add(this.serverIp);
		this.centerPanel.add(new JLabel("Porta Server:"));
		this.centerPanel.add(this.serverPort);

		this.addConnectButtonListener();
		
		super.add(centerPanel, BorderLayout.CENTER);
		super.add(this.connect, BorderLayout.SOUTH);
		
		super.repaint();
		super.revalidate();
	}

	public void logging()
	{
		super.removeAll();
		super.setLayout(new BorderLayout());
		
		this.centerPanel = new JPanel();
		this.centerPanel = new JPanel(new GridLayout(2, 2));
		
		this.centerPanel.add(new JLabel("Nome Utente:"));
		this.centerPanel.add(this.user);
		this.centerPanel.add(new JLabel("Password:"));
		this.centerPanel.add(this.password);
		
		super.removeAll();
		super.setLayout(new BorderLayout());

		this.addLoginButtonListener();
		
		super.add(this.centerPanel, BorderLayout.CENTER);
		super.add(this.login, BorderLayout.SOUTH);
		
		super.repaint();
		super.revalidate();
	}
	
	public void addConnectButtonListener()
	{	

		this.connect.addMouseListener(new MouseAdapter() {
			
		@Override
		public void mouseClicked(MouseEvent e) {

			String ip = LobbyLogin.this.serverIp.getText();
			int port = Integer.parseInt(LobbyLogin.this.serverPort.getText());
			if(LobbyLogin.this.mainMenu.lobbyClient.connect(ip, port))
				LobbyLogin.this.logging();
			else
				System.out.println("NON CONNESSO");
		}
			
		});

	}
	
	public void addLoginButtonListener()
	{	

		this.login.addMouseListener(new MouseAdapter() {
			
		@Override
		public void mouseClicked(MouseEvent e) {

			String nome = LobbyLogin.this.user.getText();
			String password = LobbyLogin.this.password.getText();
			if(LobbyLogin.this.mainMenu.lobbyClient.logIn(nome, password))
				LobbyLogin.this.mainMenu.switchTo(new Lobby(LobbyLogin.this.mainMenu.lobbyClient));
			else
				System.out.println("NOT LOGGED");
		}
			
		});

	}
	
	public void checkPanel()
	{
		if(this.mainMenu.lobbyClient.isConnected())
			this.logging();
		else
			this.connecting();
	}
	
}