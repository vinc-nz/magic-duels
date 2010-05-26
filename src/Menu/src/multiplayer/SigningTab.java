package Menu.src.multiplayer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Menu.src.MainMenu;
import Menu.src.multiplayer.lobby.LobbyUtilsFactory;

public class SigningTab extends MultiplayerMenuTabs {
	
	JTextField user;
	JPasswordField password;
	JTextField mail;
	
	JButton iscrivi;
	
	public SigningTab(MainMenu mainMenu) {
		super(mainMenu);

		this.user = new JTextField();
		LobbyUtilsFactory.setLobbyTextFieldParameters(this.user, "Nome Utente");
		
		this.password = new JPasswordField();
		LobbyUtilsFactory.setLobbyTextFieldParameters(this.password, "Password");

		this.mail = new JTextField();
		LobbyUtilsFactory.setLobbyTextFieldParameters(this.mail, "Email");
		
		this.iscrivi = new JButton("Crea Nuovo Utente!");	
		this.iscrivi.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if(SigningTab.this.mainMenu.lobbyClient.newUser(SigningTab.this.user.getText(), SigningTab.this.password.getText(), SigningTab.this.mail.getText()))
				{
					SigningTab.super.showWarning("Iscrizione Effettuata con Successo!");
					SigningTab.this.user.setText("");
					SigningTab.this.password.setText("");
					SigningTab.this.mail.setText("");
				} else
					SigningTab.super.showWarning("Iscrizione Fallita!");
			}
				
			});
	
	}

	public void connecting()
	{
		super.removeAll();
		super.setLayout(new GridBagLayout());
		
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
	
	public void formIscrizione()
	{
		super.removeAll();
		super.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;

		c.gridy = 0;
		super.add(this.user, c);
		
		c.gridy = 1;
		super.add(this.password, c);
		
		c.gridy = 2;
		super.add(this.mail, c);
		
		c.gridy = 3;
		super.add(this.iscrivi, c);

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
