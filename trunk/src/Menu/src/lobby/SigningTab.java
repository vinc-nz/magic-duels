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

public class SigningTab extends MultiplayerMenuTabs {

	JLabel nome_;
	JLabel password_;
	JLabel mail_;
	
	JTextField user;
	JPasswordField password;
	JTextField mail;
	
	JButton iscrivi;
	
	public SigningTab(MainMenu mainMenu) {
		super(mainMenu);
	}

	public void connecting()
	{
		super.removeAll();
		super.setLayout(new BorderLayout());
		
		JPanel centerPanel = new JPanel(new GridLayout(2, 2));
		
		this.connect.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {

				String ip = SigningTab.this.serverIp.getText();
				int port = Integer.parseInt(SigningTab.this.serverPort.getText());
				if(SigningTab.this.mainMenu.lobbyClient.connect(ip, port))
					SigningTab.this.checkPanel();
				else
					SigningTab.super.showWarning("Connessione Fallita!");
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
	
	public void formIscrizione()
	{
		super.removeAll();
		super.setLayout(new BorderLayout());
		
		JPanel centerPanel = new JPanel(new GridLayout(3, 2));
		
		this.nome_ = new JLabel("Nome Utente:");
		this.password_ = new JLabel("Password:");
		this.mail_ = new JLabel("Email Utente:");
		
		this.user = new JTextField();
		this.password = new JPasswordField();
		this.mail = new JTextField();
		
		this.iscrivi = new JButton("Crea Nuovo Utente!");
		this.iscrivi.setPreferredSize(new Dimension(100, 30));
		
		this.iscrivi.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if(SigningTab.this.mainMenu.lobbyClient.newUser(SigningTab.this.user.getText(), SigningTab.this.password.getText(), SigningTab.this.mail.getText()))
					SigningTab.super.showWarning("Iscrizione Effettuata con Successo!");
				else
					SigningTab.super.showWarning("Iscrizione Fallita!");
			}
				
			});
		
		centerPanel.add(this.nome_);
		centerPanel.add(this.user);
		centerPanel.add(this.password_);
		centerPanel.add(this.password);
		centerPanel.add(this.mail_);
		centerPanel.add(this.mail);
		
		super.add(centerPanel, BorderLayout.CENTER);
		super.add(this.iscrivi, BorderLayout.SOUTH);
		
		super.repaint();
		super.revalidate();
	}
	
	public void checkPanel()
	{
		if(this.mainMenu.lobbyClient.isConnected())
			this.formIscrizione();
		else
			this.connecting();
	}
	
}
