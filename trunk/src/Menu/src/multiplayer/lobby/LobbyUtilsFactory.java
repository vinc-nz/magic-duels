package Menu.src.multiplayer.lobby;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
		Border border = BorderFactory.createLineBorder(Color.black, 3);
		TitledBorder borderTitle = BorderFactory.createTitledBorder(border, title);
		borderTitle.setTitleColor(Color.black);
		borderTitle.setTitleFont( new Font("Verdana", Font.BOLD, 18));
		
		return borderTitle;
	}
	
	public static JButton createAnimatedButton(String mainImage, String rollOverImage)
	{
		JButton button = new JButton(new ImageIcon(mainImage));
		button.setRolloverIcon(new ImageIcon(rollOverImage));
		button.setFocusPainted(false);
		button.setFocusable(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		return button;
	}
	
	public static void setLobbyTextFieldParameters(JTextField field, String borderTitle)
	{
		field.setOpaque(false);
		field.setPreferredSize(new Dimension(300, 60));
		field.setMaximumSize(new Dimension(300, 60));
		field.setBorder(LobbyUtilsFactory.createTitledBorder(borderTitle));
		field.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		field.setForeground(Color.BLACK);
	}
	
}