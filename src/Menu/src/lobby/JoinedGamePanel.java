package Menu.src.lobby;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import lobby.LobbyJoinedGame;

public class JoinedGamePanel extends JPanel {

	Lobby graphicLobby;

	public JoinedGamePanel(Lobby graphicLobby) {
		
		super(new FlowLayout(FlowLayout.CENTER));
		super.setOpaque(false);
		
		this.graphicLobby = graphicLobby;
	
		LobbyJoinedGame joinedGame = this.graphicLobby.lobbyClient.getJoinedGame();

		JPanel panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		//c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 0;
		
		int giocatore;
		List<String> slots = joinedGame.getSlots();
		
		JLabel player;
		for (int i = 0; i < joinedGame.getNumSlots(); i++) {
			giocatore = i + 1;
			
			player = new JLabel(slots.get(i));
			player.setBorder(JoinedGamePanel.createTitledBorder("Giocatore " + giocatore));
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
