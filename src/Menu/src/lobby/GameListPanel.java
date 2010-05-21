package Menu.src.lobby;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
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
		
		this.graphicLobby = graphicLobby;
	}

	public void initGameListArePanel()
	{
		if(this.gameTable != null)
			return;
		
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
		
		this.refreshGameListArePanel();
		
		super.setViewportView(this.gameTable);

	}

	public void refreshGameListArePanel()
	{
		
		if(this.gameTable == null)
			this.initGameListArePanel();
			
		this.gameTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//this.gameTable.setRowSelectionAllowed(true);
		//this.gameTable.setColumnSelectionAllowed(false);
		//this.gameTable.setCellSelectionEnabled(false);
		
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

}
