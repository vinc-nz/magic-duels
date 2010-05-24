package Menu.src.lobby;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class LobbyBorderFactory {

	public static Border createPanelBorder()
	{
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		
		return BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
	}
	
	public static Border createTitledBorder(String title)
	{
		Border border = BorderFactory.createLineBorder(Color.blue, 2);
		TitledBorder borderTitle = BorderFactory.createTitledBorder(border, title);
		borderTitle.setTitleColor(Color.black);
		borderTitle.setTitleFont( new Font("Arial", Font.BOLD, 18));
		
		return borderTitle;
	}
	
}
