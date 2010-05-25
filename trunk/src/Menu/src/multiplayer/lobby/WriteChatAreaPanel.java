package Menu.src.multiplayer.lobby;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Menu.src.multiplayer.LobbyUtilsFactory;

public class WriteChatAreaPanel extends JPanel {

	Lobby graphicLobby;
	
	JTextField chatArea;
	
	public WriteChatAreaPanel(Lobby lobby) {

		super(new FlowLayout(FlowLayout.CENTER));
		super.setOpaque(false);
		super.setPreferredSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 150));		
		super.setBorder(LobbyUtilsFactory.createPanelBorder());
		
		this.graphicLobby = lobby;
		this.initWriteChatAreaPanel();
	
	}
	
	public void initWriteChatAreaPanel()
	{	
		this.chatArea = new JTextField();
		this.chatArea.setOpaque(false);
		this.chatArea.setBorder(BorderFactory.createEmptyBorder(0, 5, 20, 5));
		
		this.chatArea.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
		this.chatArea.setForeground(Color.BLACK);
		
		this.chatArea.setPreferredSize(new Dimension((int)super.getPreferredSize().getWidth(), 100));

		this.chatArea.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {}		
			@Override
			public void keyReleased(KeyEvent arg0) {}
			
			@Override
			public void keyPressed(KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					if(!WriteChatAreaPanel.this.chatArea.getText().trim().equals(""))
						WriteChatAreaPanel.this.graphicLobby.lobbyClient.sendChatMessage(WriteChatAreaPanel.this.chatArea.getText());
					
					WriteChatAreaPanel.this.chatArea.setText(null);
					
					WriteChatAreaPanel.this.chatArea.requestFocus();
				}
			}
		});
				
		super.add(this.chatArea);
	
		super.repaint();
		super.revalidate();
	}
	
}
