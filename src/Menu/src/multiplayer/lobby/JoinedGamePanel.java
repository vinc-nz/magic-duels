package Menu.src.multiplayer.lobby;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


import lobby.LobbyJoinedGame;

public class JoinedGamePanel extends JPanel {

	Lobby graphicLobby;

	JButton back;
	
	public JoinedGamePanel(Lobby graphicLobby) {
		
		super(new FlowLayout(FlowLayout.CENTER));
		super.setOpaque(false);
		
		this.graphicLobby = graphicLobby;
	
		LobbyJoinedGame joinedGame = this.graphicLobby.lobbyClient.getJoinedGame();

		JPanel panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		
		c.gridwidth = 2;
		
		panel.add(LobbyUtilsFactory.createGameTitleLabel(this.graphicLobby.lobbyClient.joinedGame.getGameName()));
		
		c.gridy = 1;
		c.gridwidth = 1;
		
		int giocatore;
		List<String> slots = joinedGame.getSlots();
		
		JLabel player;
		for (int i = 0; i < joinedGame.getNumSlots(); i++) {
			giocatore = i + 1;
			
			player = new JLabel(slots.get(i));
			player.setBorder(LobbyUtilsFactory.createTitledBorder("Giocatore " + giocatore));
			player.setPreferredSize(new Dimension(200, 50));
			player.setOpaque(false);
			
			panel.add(player, c);

			if(c.gridx % 2 == 0)
				c.gridx = 1;
			else
			{
				c.gridx = 0;
				c.gridy++;
			}			
		}
		
		back = LobbyUtilsFactory.createAnimatedButton("src/Menu/data/multiplayer/back1.gif", "src/Menu/data/multiplayer/back2.gif");
		back.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				JoinedGamePanel.this.graphicLobby.lobbyClient.joinedGame = null;
				JoinedGamePanel.this.graphicLobby.lobbyClient.leaveSlot();
				JoinedGamePanel.this.graphicLobby.multiplayerGame();
			}
		});

		if(c.gridx == 1)
		{
			c.gridx = 0;
			c.gridy++;
		}
		c.gridwidth = 2;

		panel.add(back, c);
		
		super.add(panel);
		super.repaint();
		super.revalidate();
		
	}	
}