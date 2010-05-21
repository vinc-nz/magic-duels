package Menu.src.lobby;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Menu.src.MainMenu;

import wiiMoteInput.PlayerMote;

import javazoom.jl.player.Player;

import net.ServerGame;

import lobby.HostedGameInfo;
import lobby.LobbyHostedGame;
import lobby.Messages;

public class HostedGamePanel extends JPanel {

	Lobby graphicLobby;
	
	JButton startGame;
	JButton closeGame;
	
	public HostedGamePanel(Lobby graphicLobby) {
		
		super(new FlowLayout(FlowLayout.CENTER));
				
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
		
		JPanel panel = new JPanel(new GridLayout(hostedGame.getNumSlots()+1, 2));

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
		
		panel.add(this.startGame);
		panel.add(this.closeGame);
		
		super.add(panel);
		super.repaint();
		super.revalidate();
				
	}
	
}
