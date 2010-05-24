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
	Image luigi;
	Image daniele;
	Image vincenzo;
	boolean name;
	int cont;
	
	final int SCREEN_WIDTH = 1280;
	final int SCREEN_HEIGHT = 800;
	
	final int IMAGE_WIDTH = 211;
	final int IMAGE_HEIGTH = 122;
	
	public Credits( final MainMenu mainMenu, final MainPanel mainPanel){
		super();
		this.mainMenu = mainMenu;
		this.mainPanel = mainPanel;
		
		background = new ImageIcon("src/Menu/data/mainMenu2.jpg").getImage();
		luigi = new ImageIcon("src/Menu/data/luigi.png").getImage();
		daniele = new ImageIcon("src/Menu/data/daniele.png").getImage();
		vincenzo = new ImageIcon("src/Menu/data/vincenzo.png").getImage();
		
		name = false;
		cont = 0;
		
		addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(cont == 0)
					showCredits();
				else
					mainMenu.switchTo(mainPanel);
				mainMenu.ok.play();
			}
		});
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage( background, 0, 0, this.getWidth(), this.getHeight(), null);
		
		if(name){
			g.drawImage( luigi, konst2(200), konst(100), imageGetWidth() + 100, imageGetHeight(), null);
			g.drawImage( vincenzo, konst2(400), konst(300), imageGetWidth() + 100, imageGetHeight(), null);
			g.drawImage( daniele, konst2(600), konst(500), imageGetWidth() + 100, imageGetHeight(), null);
		}
			
	}
	
	public void showCredits(){
		name = true;
		this.repaint();
		cont++;
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