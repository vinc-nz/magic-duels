package Menu.src;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Credits extends JPanel{

	MainMenu mainMenu;
	MainPanel mainPanel;
	
	Image background;
	
	final int SCREEN_WIDTH = 1280;
	final int SCREEN_HEIGHT = 800;
	
	final int IMAGE_WIDTH = 211;
	final int IMAGE_HEIGTH = 122;
	
	public Credits( final MainMenu mainMenu, final MainPanel mainPanel){
		super();
		this.mainMenu = mainMenu;
		this.mainPanel = mainPanel;
		
		background = new ImageIcon("src/Menu/data/imageCredits.jpg").getImage();
			
		addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mainMenu.switchTo(mainPanel);
			}
		});
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage( background, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	
	public int imageGetWidth(){
		return (IMAGE_WIDTH * this.getWidth()) / SCREEN_WIDTH;
	}

	public int imageGetHeight(){
		return (IMAGE_HEIGTH * this.getHeight()) / SCREEN_HEIGHT;
	}
	
	public int konst(int i){
		return (i * this.getHeight()) / SCREEN_HEIGHT; 
	}

	public int konst2(int i){
		return (i * this.getWidth()) / SCREEN_WIDTH; 
	}

}
