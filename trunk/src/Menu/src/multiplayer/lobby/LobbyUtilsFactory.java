package Menu.src.multiplayer.lobby;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class LobbyUtilsFactory {

	public static JLabel createGameTitleLabel(String text)
	{
		JLabel label = new JLabel(text, JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.BOTTOM);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
		label.setForeground(Color.black);
		label.setBorder(LobbyUtilsFactory.createPanelBorder());
		
		return label;
	}
	
	/**
	 * @return a Compound Border
	 */
	public static Border createPanelBorder()
	{
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		
		return BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
	}
	
	/**
	 * Returns a titled border
	 * @param title the title o the border to return
	 * @return a titled border
	 */
	public static Border createTitledBorder(String title)
	{
		Border border = BorderFactory.createLineBorder(Color.black, 3);
		TitledBorder borderTitle = BorderFactory.createTitledBorder(border, title);
		borderTitle.setTitleColor(Color.black);
		borderTitle.setTitleFont( new Font("Verdana", Font.BOLD, 18));
		
		return borderTitle;
	}
	
	/**
	 * Returns an animated JButton
	 * @param mainImage the button background
	 * @param rollOverImage the button image to be shown as the the mouse rolls over the button
	 * @return an animated JButton
	 */
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
	
	/**
	 * Sets customized parameters to the text field given
	 * @param field the text field to which set the parameters
	 * @param borderTitle the title of the border to add to the text field
	 */
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