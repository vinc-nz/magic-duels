package Menu.src.lobby;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import lobby.LobbyHostedGame;
import lobby.Messages;

public class HostedGamePanel extends JPanel {

	Lobby graphicLobby;
	
	JButton startGame;
	JButton closeGame;
	
	public HostedGamePanel(Lobby graphicLobby) {
		
		//super(new FlowLayout(FlowLayout.CENTER));
		super.setLayout(new GridLayout(1, 1));
		super.setOpaque(false);
		
		this.graphicLobby = graphicLobby;

		this.startGame = new JButton("Inizia la Partita!");
		this.startGame.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				HostedGamePanel.this.graphicLobby.lobbyClient.sendMessage(Messages.READYTOSTART);
			}
				
			});
		
		this.closeGame = new JButton("Chiudi Partita");
		this.closeGame.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				HostedGamePanel.this.graphicLobby.lobbyClient.sendMessage(Messages.GAMEKILLED);
			}
				
			});
		
		LobbyHostedGame hostedGame = this.graphicLobby.lobbyClient.getHostedGame();
		
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		
		SlotComboBox slot;
		int giocatore;
		ArrayList<SlotComboBox> slots = new ArrayList<SlotComboBox>();
		for (int i = 0; i < hostedGame.getNumSlots(); i++) {
			giocatore = i + 1;
			slot = new SlotComboBox(this.graphicLobby.lobbyClient.getHostedGame(), i);
			slot.setBorder(HostedGamePanel.createTitledBorder("Giocatore " + giocatore));
			slots.add(slot);
			panel.add(slot, c);
			
			c.gridy++;
			
			/*
			if(c.gridx % 2 == 0)
				c.gridx = 1;
			else
			{
				c.gridx = 0;
				c.gridy++;
			}*/
			
		}
		
		c.gridwidth = 1;
		
		c.gridx = 0;
		panel.add(this.startGame, c);
		c.gridx = 1;
		panel.add(this.closeGame, c);
		
		super.add(panel);
		super.repaint();
		super.revalidate();
				
	}
	
	public static Border createTitledBorder(String title)
	{
		Border border = BorderFactory.createLineBorder(Color.blue, 2);
		TitledBorder borderTitle = BorderFactory.createTitledBorder(border, title);
		borderTitle.setTitleColor(Color.black);
		borderTitle.setTitleFont( new Font("Arial", Font.BOLD, 18));
		
		return borderTitle;
	}
	
}
