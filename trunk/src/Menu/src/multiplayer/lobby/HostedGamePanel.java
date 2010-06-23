package Menu.src.multiplayer.lobby;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import lobby.LobbyHostedGame;
import lobby.Messages;

public class HostedGamePanel extends JPanel {

	Lobby graphicLobby;
	
	JButton startGame;
	JButton closeGame;
	
	public HostedGamePanel(Lobby graphicLobby) {
		
		super.setLayout(new BorderLayout() );
		super.setOpaque(false);
		
		this.graphicLobby = graphicLobby;

		this.startGame = LobbyUtilsFactory.createAnimatedButton("Menu/data/multiplayer/start1.gif", "Menu/data/multiplayer/start2.gif");
		this.startGame.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				HostedGamePanel.this.graphicLobby.lobbyClient.sendMessage(Messages.READYTOSTART);
			}
				
			});
		
		this.closeGame = LobbyUtilsFactory.createAnimatedButton("Menu/data/multiplayer/back1.gif", "Menu/data/multiplayer/back2.gif");
		this.closeGame.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				HostedGamePanel.this.graphicLobby.lobbyClient.sendMessage(Messages.GAMEKILLED);
			}
				
		});
		
		LobbyHostedGame hostedGame = this.graphicLobby.lobbyClient.getHostedGame();
		
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setOpaque(false);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 10, 5, 10);
		c.gridx = 0;
		c.gridy = 0;

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
			
			if(c.gridx % 2 == 0)
				c.gridx = 1;
			else
			{
				c.gridx = 0;
				c.gridy++;
			}	
		}
				
		c.gridx = 0;
		c.gridwidth = 2;
		panel.add(this.startGame, c);
		
		c.gridy++;
		panel.add(this.closeGame, c);
		
		super.add(LobbyUtilsFactory.createGameTitleLabel(hostedGame.getGameName()), BorderLayout.NORTH);
		super.add(panel, BorderLayout.CENTER);
		
		super.repaint();
		super.revalidate();
				
	}
	
}
