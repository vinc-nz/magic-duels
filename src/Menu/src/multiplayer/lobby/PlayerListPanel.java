package Menu.src.multiplayer.lobby;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class PlayerListPanel extends JScrollPane {

	Lobby graphicLobby;
	
	public PlayerListPanel(Lobby graphicLobby) {
		
		super();
		super.setOpaque(false);
		super.getViewport().setOpaque(false);
		super.setBorder(LobbyUtilsFactory.createPanelBorder());
		super.setPreferredSize(new Dimension(100, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		
		this.graphicLobby = graphicLobby;
		this.refreshPlayerList();
		
		super.repaint();
		super.revalidate();
	}

	/**
	 * Refreshes the game list panel
	 */
	public void refreshPlayerList()
	{
		DefaultListModel model = new DefaultListModel();

		List players = this.graphicLobby.lobbyClient.getPlayers();
		
		if(players != null)
			for (Iterator iterator = players.iterator(); iterator.hasNext();)
				model.addElement((String)iterator.next());
		
		JList list = new JList(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		list.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		list.setForeground(Color.black);
		list.setOpaque(false);
		
		list.repaint();
		list.revalidate();
		
		super.setViewportView(list);
	}

}
