package Menu.src.multiplayer.lobby;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class TopPanel extends JPanel {	
	
	public TopPanel()
	{	
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		int textWidth = (int) screenSize.getWidth();
		int textHeight = (int) screenSize.getHeight() / 6 * 1;
		
		super.setPreferredSize(new Dimension(textWidth, textHeight));
		super.setOpaque(false);	
	}

	public void paintComponent(Graphics g) {
		g.drawImage( new ImageIcon(TopPanel.class.getClassLoader().getResource("Menu/data/multiplayer/welcome.gif")).getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
	}
	
}
