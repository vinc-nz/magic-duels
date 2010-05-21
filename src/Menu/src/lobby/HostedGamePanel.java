package Menu.src.lobby;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import lobby.LobbyHostedGame;

public class HostedGamePanel extends JPanel {

	Lobby graphicLobby;
	
	public HostedGamePanel(Lobby graphicLobby) {
		
		super(new FlowLayout(FlowLayout.CENTER));
		
		System.out.println("PANNELLO CREATO");
		
		this.graphicLobby = graphicLobby;

		LobbyHostedGame hostedGame = this.graphicLobby.lobbyClient.getHostedGame();
		
		JPanel panel = new JPanel(new GridLayout(hostedGame.getNumSlots(), 2));

		SlotComboBox slot;
		int giocatore;
		ArrayList<SlotComboBox> slots = new ArrayList<SlotComboBox>();
		for (int i = 0; i < hostedGame.getNumSlots(); i++) {
			giocatore = i + 1;
			slot = new SlotComboBox(this.graphicLobby.lobbyClient.getHostedGame(), i);
			slots.add(slot);
			panel.add(new JLabel("Giocatore " + giocatore));
			panel.add(slot);
		}
		
		super.add(panel);
		super.repaint();
		super.revalidate();
				
	}
	
}
