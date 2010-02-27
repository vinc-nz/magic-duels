package Menu.src;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import jmegraphic.Game;

public class Mouse extends MouseAdapter{
	
	Image fantasy;
	Image newGame;
	Image multiplayer;
	Image credits;
	Image options;
	Image exit;
	int position[];
	MainPanel mainPanel;
	Game game;
	MenuMultiplayer menuMultiplayer;
	MainMenu mainMenu;
	Options optionsPanel;
	
	public Mouse(int initialPosition[], Image fantasy, Image newGame, Image multiplayer, Image credits, Image options,
			     Image exit, Game game, MainMenu mainMenu, MainPanel mainPanel ){
		
		this.game = game;
		this.mainMenu = mainMenu;
		this.fantasy = fantasy;
		this.newGame = newGame;
		this.multiplayer = multiplayer;
		this.credits = credits;
		this.options = options;
		this.exit = exit;
		this.position = initialPosition;
		
		this.mainPanel = mainPanel;
		
	}
	
	public void mouseMoved(MouseEvent e){
		super.mouseMoved(e);		
		
		if(e.getX() >= position[0] && e.getX() <= position[0]*2 
			&& e.getY() >= position[1] && e.getY() <= position[1]+position[1]){
			mainPanel.sNewGame = true; 
			mainPanel.repaint();
		}
		else {
			if( mainPanel.sNewGame == true ) {
				mainPanel.sNewGame=false;
				mainPanel.repaint(); 
			}
		}
		if(e.getX() >= position[2] && e.getX() <= position[2]*2 
		     && e.getY() >= position[3] && e.getY() <= position[3]+position[1]){
				mainPanel.sMultiplayer = true;
				mainPanel.repaint();
			}
			else
				if( mainPanel.sMultiplayer == true ){
					mainPanel.sMultiplayer=false;
					mainPanel.repaint();
				}
			
		if(e.getX() >= position[4] && e.getX() <= position[4]*2 
			     && e.getY() >= position[5] && e.getY() <= position[5]+position[1]){
				mainPanel.sOptions = true;
				mainPanel.repaint();
			}
			else
				if( mainPanel.sOptions == true ){
					mainPanel.sOptions = false;
					mainPanel.repaint();
				}
				
		
		if(e.getX() >= position[6] && e.getX() <= position[6]*2 
			     && e.getY() >= position[7] && e.getY() <= position[7]+position[1]){
				mainPanel.sCredits = true;
				mainPanel.repaint();
			}
			else
				if( mainPanel.sCredits == true ){
					mainPanel.sCredits = false;
					mainPanel.repaint();
				}
				
		
		if(e.getX() >= position[8] && e.getX() <= position[8]*2 
			     && e.getY() >= position[9] && e.getY() <= position[9]+position[1]){
				mainPanel.sExit = true;
				mainPanel.repaint();
			}
			else
				if( mainPanel.sExit == true ){
					mainPanel.sExit = false;
					mainPanel.repaint();
				}
						
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		
		if(mainPanel.sExit){
			mainMenu.close();
		}
		
		if(mainPanel.sNewGame){
			game.initSingleGame();
			game.start();
			mainMenu.setVisible(false);
		}
		
		if(mainPanel.sMultiplayer){
			mainMenu.switchTo(new MenuMultiplayer(game, mainMenu, mainPanel));
		}
		
		if(mainPanel.sOptions){
			mainMenu.switchTo( new Options(mainMenu, mainPanel));
		}
		
		if(mainPanel.sCredits){
			mainMenu.switchTo( new Credits(mainMenu, mainPanel));
		}
	}
}
