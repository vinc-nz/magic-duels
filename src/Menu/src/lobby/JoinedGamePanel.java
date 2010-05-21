package Menu.src.lobby;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import lobby.LobbyJoinedGame;

public class JoinedGamePanel extends JPanel {

	Lobby graphicLobby;

	public JoinedGamePanel(Lobby graphicLobby) {
		
		super(new FlowLayout(FlowLayout.CENTER));
		
		this.graphicLobby = graphicLobby;
	
		LobbyJoinedGame joinedGame = this.graphicLobby.lobbyClient.getJoinedGame();

		JPanel panel = new JPanel(new GridLayout(joinedGame.getNumSlots(), 2));

		int giocatore;
		List<String> slots = joinedGame.getSlots();
		
		for (int i = 0; i < joinedGame.getNumSlots(); i++) {
			giocatore = i + 1;
			panel.add(new JLabel("Giocatore " + giocatore + " "));
			panel.add(new JLabel(slots.get(i)));
		}
		
		super.add(panel);
		super.repaint();
		super.revalidate();
		
	}
}
