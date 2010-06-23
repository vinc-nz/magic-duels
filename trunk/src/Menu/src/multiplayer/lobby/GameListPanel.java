package Menu.src.multiplayer.lobby;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


import lobby.HostedGameInfo;

public class GameListPanel extends JScrollPane {

	Lobby graphicLobby;

	JTable gameTable;
	DefaultTableModel gameTableModel;
	int selectedTableRow;
	
	public GameListPanel(Lobby graphicLobby) {
		
		super();
		super.setOpaque(false);
		super.getViewport().setOpaque(false);
		super.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
		super.setBorder(BorderFactory.createEmptyBorder(40, 5, 5, 5));
		
		this.graphicLobby = graphicLobby;
		
		this.initGameListArePanel();
	}

	/**
	 * Initializes the game list panel
	 */
	public void initGameListArePanel()
	{
		Vector<String> header = new Vector<String>();
		
		header.add("Nome Partita");
		header.add("Nome Host");
		header.add("Ip Host");
		header.add("Porta Host");

		this.gameTableModel = new DefaultTableModel()
		{ 
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		this.gameTableModel.setColumnIdentifiers(header);
		this.gameTable = new JTable(this.gameTableModel);
		this.gameTable.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		this.gameTable.setForeground(Color.black);
		this.gameTable.setGridColor(Color.black);
		this.gameTable.setShowGrid(false);
		
		this.gameTable.setOpaque(false);
		
		this.gameTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.gameTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		
			@Override
			public void valueChanged(ListSelectionEvent e) {

				ListSelectionModel lsm = (ListSelectionModel)e.getSource();
				
		        if (lsm.isSelectionEmpty())
		        	GameListPanel.this.selectedTableRow = -1;
		        else {
		            int minIndex = lsm.getMinSelectionIndex();
		            int maxIndex = lsm.getMaxSelectionIndex();
		            for (int i = minIndex; i <= maxIndex; i++)
		                if (lsm.isSelectedIndex(i))
		                	GameListPanel.this.selectedTableRow = i;
		        }
			}
		});
		
		this.refreshGameListArePanel();
		
	}

	/**
	 * Refreshes the game list panel
	 */
	public void refreshGameListArePanel()
	{

		List<HostedGameInfo> gameList = this.graphicLobby.lobbyClient.getHostedGameList();
		
		if(gameList != null)
		{
			for(int i = 0; i < this.gameTableModel.getRowCount(); i++)
			{
				this.gameTableModel.removeRow(i);
				this.gameTable.revalidate();
			}
			
			for (Iterator iterator = gameList.iterator(); iterator.hasNext();)
			{
				HostedGameInfo info = (HostedGameInfo) iterator.next();
				this.gameTableModel.insertRow(this.gameTableModel.getRowCount(), new Object[]{info.getGameName(), info.getHostName(), info.getIp(), info.getPorta()});
				//this.gameTableModel.addRow(new Object[]{info.getGameName(), info.getHostName(), ""});
			}		
			
		}
		
		super.setViewportView(this.gameTable);

		this.repaint();
		this.revalidate();		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		Image img = new ImageIcon(GameListPanel.class.getClassLoader().getResource("Menu/data/lala3.gif")).getImage(); 
		
		g.drawImage(img , super.getSize().width / 5, 0, img.getWidth(null), img.getHeight(null), null);
	}
	
}