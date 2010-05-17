package Menu.src;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import lobby.LobbyClient;

public class LobbyLogin extends JPanel {

	MainMenu mainMenu;
	
	JTextField serverIp;
	JTextField serverPort;
	JTextField user;
	JPasswordField password;
	
	JButton connect;
	JButton login;
	
	JPanel centerPanel;
	
	LobbyClient lobbyClient;
	
	public LobbyLogin(MainMenu mainMenu) {
			
		this.mainMenu = mainMenu;
		
		this.lobbyClient = new LobbyClient();
		
		this.serverIp = new JTextField("127.0.0.1");
		this.serverPort = new JTextField("7000");
		this.user = new JTextField("Neb");
		this.password = new JPasswordField("lalala");
		
		this.login = new JButton("Effettua il LogIn");
		this.connect = new JButton("Connetti al Server");
		
		this.centerPanel = new JPanel();
		this.connecting();
		
		super.setPreferredSize(new Dimension(480, 100));
	}
	
	public void connecting()
	{
		super.setLayout(new BorderLayout());
		super.add(new JLabel("WELCOME TO THE LOBBY!"), BorderLayout.NORTH);

		this.centerPanel.removeAll();
		this.centerPanel = new JPanel(new GridLayout(2, 2));
		
		GridBagConstraints c = new GridBagConstraints();
		//c.insets = new Insets(30, 30, 30, 30);
		
		//this.centerPanel.getLayout().
		
		this.centerPanel.add(new JLabel("Server ip:"), c);
		this.centerPanel.add(this.serverIp, c);
		this.centerPanel.add(new JLabel("Server Port:"));
		this.centerPanel.add(this.serverPort);
			
		this.addConnectButtonListener();
		
		super.add(centerPanel, BorderLayout.CENTER);
		super.add(this.connect, BorderLayout.SOUTH);	
	}

	public void logging()
	{
		this.centerPanel.removeAll();
		this.centerPanel = new JPanel(new GridLayout(2, 2));
		this.centerPanel.add(new JLabel("Nome Utente:"));
		this.centerPanel.add(this.user);
		this.centerPanel.add(new JLabel("Password:"));
		this.centerPanel.add(this.password);
		
		super.removeAll();
		super.setLayout(new BorderLayout());
		super.add(new JLabel("WELCOME TO THE LOBBY!"), BorderLayout.NORTH);

		this.addLoginButtonListener();
		
		super.add(this.centerPanel, BorderLayout.CENTER);
		super.add(this.login, BorderLayout.SOUTH);
	
		this.centerPanel.revalidate();
		this.centerPanel.repaint();
		
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
			if(LobbyLogin.this.lobbyClient.connect(ip, port))
			{
				System.out.println("true");
				LobbyLogin.this.logging();
			}
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
			if(LobbyLogin.this.lobbyClient.logIn(nome, password))
			{
				System.out.println("LOGGATO!");
				LobbyLogin.this.mainMenu.switchTo(new Lobby(LobbyLogin.this.lobbyClient));
			}
			else
				System.out.println("NOT LOGGED");
		}
			
		});

	}
	
}