package Menu.src;

import game.SingleGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import wiiMoteInput.PlayerMote;


public class InitSingleGame extends JPanel {
	
	/** Class ID */
	private static final long serialVersionUID = 1L;
	
	/** MainMenu Pointer */
	MainMenu mainMenu;
	MainPanel mainPanel;
	JTextField namePlayer;
	Image background;
	PlayerMote playerMote;
	
	/** Container */
	Vector<String> numberPlayer;
	
	public InitSingleGame(final MainMenu mainMenu, final MainPanel mainPanel, final PlayerMote playerMote){
		super();
		this.mainMenu = mainMenu;
		this.playerMote = playerMote;
		this.mainPanel = mainPanel;
				
		this.background = new ImageIcon("src/Menu/data/mainMenu2.jpg").getImage();
		
		// Get screen size informations
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// apply screen size informations
		setBounds(0,0,screenSize.width, screenSize.height);
		
		// apply layout to main panel
		setLayout(new BorderLayout());
		setOpaque(false);
		
		//Create a new sub panel that divide frame
		JPanel dividePanel = new JPanel();
		dividePanel.setLayout(new BorderLayout());
		dividePanel.setOpaque(false);
		add(dividePanel,BorderLayout.CENTER);
		
		//Create first visible panel 
		JPanel grid = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		grid.setLayout(layout);
		grid.setOpaque(true);
		grid.setPreferredSize(new Dimension(screenSize.width*6/8, screenSize.height/4));
		//add a label to panel
		
		TitledBorder titleBorder = new TitledBorder("SINLGE GAME OPTIONS");
		grid.setBorder(titleBorder);
		dividePanel.add(grid, BorderLayout.NORTH);
		
		GridBagConstraints lim = new GridBagConstraints();
		
		//Create a label that show text
		JLabel namePlayerLabel = new JLabel("Name Player");
		lim.gridx = 0;
		lim.gridy = 0;
		lim.weightx = 0.5;
		lim.weighty = 0.5;
		layout.setConstraints(namePlayerLabel, lim);
		grid.add(namePlayerLabel);
		
		String name = "Player 1";
		namePlayer = new JTextField( name );
		grid.add(namePlayer);
				
		//Create a Label that show text
		JLabel numberLabel = new JLabel("Number players");
		lim.gridx = 0;
		lim.gridy = 1;
		lim.weightx = 0.5;
		lim.weighty = 0.5;
		layout.setConstraints(numberLabel, lim);
		grid.add(numberLabel);
		
		numberPlayer = new Vector<String>();
		numberPlayer.add("2");
		numberPlayer.add("3");
		numberPlayer.add("4");
		numberPlayer.add("5");
		numberPlayer.add("6");
		numberPlayer.add("7");
		numberPlayer.add("8");
		
		final JComboBox numberCombo = new JComboBox(numberPlayer);
		
		lim.gridx = 1;
		lim.gridy = 1;
		lim.weightx = 0.5;
		lim.weighty = 0.5;
		layout.setConstraints(numberCombo, lim);
		grid.add(numberCombo);
		
		//useful buttons in the third visible panel
		JPanel flow = new JPanel();
		flow.setLayout(new FlowLayout());
		flow.setOpaque(false);
		dividePanel.add(flow,BorderLayout.SOUTH);
		
		JButton buttonCancel = new JButton("CANCEL");
		flow.add(buttonCancel);
		JButton buttonOk = new JButton("OK");
		flow.add(buttonOk);
		
		// cancel return to main panel
		buttonCancel.addActionListener(
		    new ActionListener() {
				@Override
		        public void actionPerformed(ActionEvent e) {
					mainMenu.ok.play();
		            mainMenu.switchTo(mainPanel);	
		        }
		    }
		);
		
		buttonOk.addActionListener(
			    new ActionListener() {
			    	@Override
			        public void actionPerformed(ActionEvent e) {
			    		String indexString = null;
			    		int index = 1;
			    		indexString = (String) numberCombo.getSelectedItem();
			    		index =  Integer.parseInt(indexString);
			    		
			    		SingleGame singleGame = new SingleGame(playerMote, mainMenu);
			    		String name = namePlayer.getText();
			    		singleGame.init(name, index);
						singleGame.start();
				       	mainMenu.ok.play();
			            mainMenu.switchTo(mainPanel);
			        }
			    }
			);
		
		//add left vertical empty panel
		JPanel pVerticalEmpty1 = new JPanel();
		pVerticalEmpty1.setOpaque(false);
		pVerticalEmpty1.setPreferredSize(new Dimension(screenSize.width/8, 1));
		add(pVerticalEmpty1,BorderLayout.WEST);
		
		//add right vertical empty panel
		JPanel pVerticalEmpty2 = new JPanel();
		pVerticalEmpty2.setOpaque(false);
		pVerticalEmpty2.setPreferredSize(new Dimension(screenSize.width/8, 1));
		add(pVerticalEmpty2,BorderLayout.EAST);
			
		//add lower horizontal empty panel
		JPanel pHorizontalEmpty1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pHorizontalEmpty1.setOpaque(false);
		pHorizontalEmpty1.setPreferredSize(new Dimension(1, screenSize.height/8));
		add(pHorizontalEmpty1,BorderLayout.SOUTH);
		
		//add upper horizontal empty panel
		JPanel pHorizontalEmpty2 = new JPanel();
		pHorizontalEmpty2.setOpaque(false);
		pHorizontalEmpty2.setPreferredSize(new Dimension(1, screenSize.height/8));
		add(pHorizontalEmpty2,BorderLayout.NORTH);
		
		this.setFocusable(true);
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage( background, 0, 0, this.getWidth(), this.getHeight(), null);
	}
	
}