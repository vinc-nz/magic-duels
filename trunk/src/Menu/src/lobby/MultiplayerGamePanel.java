package Menu.src.lobby;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MultiplayerGamePanel extends JPanel{
	
	Lobby graphicLobby;
	
	JTextField newGameName;
	JTextField newGamePortNumber;
	JComboBox newGameSlotNumber;
	JButton createNewGame;
	JButton joinGame;
	
	public MultiplayerGamePanel(Lobby graphicLobby) {
		super();
		this.graphicLobby = graphicLobby;

		this.newGameName = new JTextField();
		this.newGamePortNumber = new JTextField();
		
		String []slots = {"1", "2", "3", "4", "5", "6", "7", "8"};
		this.newGameSlotNumber = new JComboBox(slots);
		
		JPanel panel = new JPanel(new GridLayout(4, 4, 50, 50));

		this.createNewGame = new JButton("Crea una Partita");
		this.createNewGame.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {

				String gameName = MultiplayerGamePanel.this.newGameName.getText();
				int numSlot = Integer.parseInt((String)MultiplayerGamePanel.this.newGameSlotNumber.getSelectedItem());
				int numPorta = Integer.parseInt(MultiplayerGamePanel.this.newGamePortNumber.getText());
				MultiplayerGamePanel.this.graphicLobby.lobbyClient.createGame(gameName, numSlot, numPorta);
			}
				
			});
		
		this.joinGame = new JButton("Unisciti alla Partita");
		this.joinGame.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {

				if(MultiplayerGamePanel.this.graphicLobby.gameListPanel.selectedTableRow == -1) return;
				
				String gameName = (String)MultiplayerGamePanel.this.graphicLobby.gameListPanel.gameTable.getValueAt(MultiplayerGamePanel.this.graphicLobby.gameListPanel.selectedTableRow, 0);
				String ip = (String)MultiplayerGamePanel.this.graphicLobby.gameListPanel.gameTable.getValueAt(MultiplayerGamePanel.this.graphicLobby.gameListPanel.selectedTableRow, 2);
				int porta = (Integer)MultiplayerGamePanel.this.graphicLobby.gameListPanel.gameTable.getValueAt(MultiplayerGamePanel.this.graphicLobby.gameListPanel.selectedTableRow, 3);
				
				MultiplayerGamePanel.this.graphicLobby.lobbyClient.tryJoiningGame(gameName, ip, porta);
			}
				
			});

		panel.add(this.createNewGame);
		panel.add(this.joinGame);
		panel.add(new JLabel("Nome Partita:"));
		panel.add(this.newGameName);
		panel.add(new JLabel("Numero Slots"));
		panel.add(this.newGameSlotNumber);
		panel.add(new JLabel("Numero Porta"));
		panel.add(this.newGamePortNumber);
		
		super.setLayout(new FlowLayout(FlowLayout.CENTER));

		super.add(panel);
	}
	
}
