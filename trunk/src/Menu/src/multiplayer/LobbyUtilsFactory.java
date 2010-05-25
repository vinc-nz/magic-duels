package Menu.src.multiplayer;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class LobbyUtilsFactory {

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
	
	public static void setLobbyTextFieldParameters(JTextField field, String borderTitle)
	{
		field.setOpaque(false);
		field.setBorder(LobbyUtilsFactory.createTitledBorder(borderTitle));
		field.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		field.setForeground(Color.BLACK);
	}
	
}
