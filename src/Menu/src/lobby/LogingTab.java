package Menu.src.lobby;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Menu.src.MainMenu;

public class LogingTab extends MultiplayerMenuTabs {
	
	JTextField user;
	JPasswordField password;
	
	JButton login;
	
	public LogingTab(MainMenu mainMenu) {
		super(mainMenu);
		
		this.user = new JTextField("Neb");
		this.password = new JPasswordField("lalala");
		
		this.login = new JButton("Entra nella Lobby");

	}

	public void connecting()
	{
		super.removeAll();
		super.setLayout(new BorderLayout());
		
		JPanel centerPanel = new JPanel(new GridLayout(2, 2));
		
		this.connect.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {

				String ip = LogingTab.this.serverIp.getText();
				int port = Integer.parseInt(LogingTab.this.serverPort.getText());
				if(LogingTab.this.mainMenu.lobbyClient.connect(ip, port))
					LogingTab.this.checkPanel();
				else
					System.out.println("NON CONNESSO");
			}
				
			});
		
		centerPanel.add(this.serverIp_);
		centerPanel.add(this.serverIp);
		centerPanel.add(this.serverPort_);
		centerPanel.add(this.serverPort);
		
		super.add(centerPanel, BorderLayout.CENTER);
		super.add(this.connect, BorderLayout.SOUTH);
		
		super.repaint();
		super.revalidate();
	}
	
	public void logging()
	{
		super.removeAll();
		super.setLayout(new BorderLayout());
		
		JPanel centerPanel = new JPanel(new GridLayout(2, 2));
		
		centerPanel.add(new JLabel("Nome Utente:"));
		centerPanel.add(this.user);
		centerPanel.add(new JLabel("Password:"));
		centerPanel.add(this.password);

		this.login.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {

				String nome = LogingTab.this.user.getText();
				String password = LogingTab.this.password.getText();
				
				if(LogingTab.this.mainMenu.lobbyClient.logIn(nome, password))
					LogingTab.this.mainMenu.switchTo(new Lobby(LogingTab.this.mainMenu));
				else
					System.out.println("NOT LOGGED");
			}
				
			});
		
		super.add(centerPanel, BorderLayout.CENTER);
		super.add(this.login, BorderLayout.SOUTH);
		
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
