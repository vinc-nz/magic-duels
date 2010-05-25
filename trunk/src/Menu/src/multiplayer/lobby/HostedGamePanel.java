package Menu.src.multiplayer.lobby;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import Menu.src.multiplayer.LobbyUtilsFactory;

import lobby.LobbyHostedGame;
import lobby.Messages;

public class HostedGamePanel extends JPanel {

	Lobby graphicLobby;
	
	JButton startGame;
	JButton closeGame;
	
	public HostedGamePanel(Lobby graphicLobby) {
		
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
				
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		
		LobbyHostedGame hostedGame = this.graphicLobby.lobbyClient.getHostedGame();
		
		SlotComboBox slot;
		int giocatore;
		ArrayList<SlotComboBox> slots = new ArrayList<SlotComboBox>();
		for (int i = 0; i < hostedGame.getNumSlots(); i++)
		{
			giocatore = i + 1;
			slot = new SlotComboBox(hostedGame, i, hostedGame.getSlot(i));
			slot.setBorder(LobbyUtilsFactory.createTitledBorder("Giocatore " + giocatore));
			slots.add(slot);
			panel.add(slot, c);
			
			c.gridy++;
			
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
	
}
