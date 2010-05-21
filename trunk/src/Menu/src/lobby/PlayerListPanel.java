package Menu.src.lobby;

import java.awt.Color;
import java.awt.Dimension;
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
		
		this.graphicLobby = graphicLobby;
		
		this.refreshPlayerList();
		
		super.setPreferredSize(new Dimension(100, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		super.setBackground(Color.green);
		super.repaint();
		super.revalidate();
	}

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
		list.setBackground(Color.green);
		
		list.repaint();
		list.revalidate();
		
		//super.removeAll();
		super.setViewportView(list);
		//super.getViewport().add(list);
		//this.playerList.setViewportView(list);
		//this.playerList.add(list);

	}



}
