package Menu.src;

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

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * Class Options
 * Pannello per la scelta delle opzioni di gioco
 * @author Luigi Marino
 *
 */
public class Options extends JPanel {
	
	/** Class ID */
	private static final long serialVersionUID = 1L;
	
	/** MainMenu Pointer */
	MainMenu mainMenu;
	
	MainPanel mainPanel;
	
	Image background;
	JButton mote;
	
	boolean wiiMoteFlashing;
	Image flashing;
	Thread flashingThread;
	AniMaker animaker;
	/** Container */
	Vector<String> fullscreenTrueFalse;
	
	/** Resolution's container */
	Vector<String> resolutions;
	
	/**
	 * Vengono creati i vari pannelli contenenti i botton, label e tutto
	 * ciò che serve per la scelta delle impostazioni di gioco
	 * @param mainMenu
	 * @param mainPanel
	 */
	public Options(final MainMenu mainMenu, final MainPanel mainPanel){
		super();
		this.mainMenu = mainMenu;
		this.mainPanel = mainPanel;
				
		this.background = new ImageIcon(Options.class.getClassLoader().getResource("Menu/data/mainMenu2.jpg")).getImage();
		//this.startFlashing();
		this.wiiMoteFlashing = false;
		
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
		TitledBorder titleBorder = new TitledBorder("GAME OPTIONS");
		grid.setBorder(titleBorder);
		dividePanel.add(grid, BorderLayout.NORTH);
		
		GridBagConstraints lim = new GridBagConstraints();
		
		//Create a label that show text
		JLabel resolutionLabel = new JLabel("Resolution");
		lim.gridx = 0;
		lim.gridy = 0;
		lim.weightx = 0.5;
		lim.weighty = 0.5;
		layout.setConstraints(resolutionLabel, lim);
		grid.add(resolutionLabel);
		
		// Create comboBox about screen resolution settings
		resolutions = new Vector<String>();
		
		// add only resolutions supported by the device
		for( String resolution : mainMenu.displayModes.keySet() ) {
			resolutions.add( resolution );
		}
		
		final JComboBox resolutionC = new JComboBox(resolutions);
		lim.gridx = 1;
		lim.gridy = 0;
		lim.weightx = 0.5;
		lim.weighty = 0.5;
		layout.setConstraints(resolutionC, lim);
		grid.add(resolutionC);
		
		//Create a Label that show text
		JLabel fullscreenLabel = new JLabel("Fullscreen");
		lim.gridx = 0;
		lim.gridy = 1;
		lim.weightx = 0.5;
		lim.weighty = 0.5;
		layout.setConstraints(fullscreenLabel, lim);
		grid.add(fullscreenLabel);
		
		// Create a comboBox that allow to choose fullscreen true or false
		fullscreenTrueFalse = new Vector<String>();
		fullscreenTrueFalse.add("true");
		fullscreenTrueFalse.add("false");
		
		final JComboBox fullscreenCombo = new JComboBox(fullscreenTrueFalse);
		
		lim.gridx = 1;
		lim.gridy = 1;
		lim.weightx = 0.5;
		lim.weighty = 0.5;
		layout.setConstraints(fullscreenCombo, lim);
		grid.add(fullscreenCombo);
		
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
			    		
			    		String[] resolutionSetting = resolutionC.getSelectedItem().toString().split("x");
			        	mainMenu.WIDTH = Integer.parseInt(resolutionSetting[0]);
			        	mainMenu.HEIGHT= Integer.parseInt(resolutionSetting[1]);
			    		
			        	if ( fullscreenCombo.getSelectedItem().toString() == "true")
			        			mainMenu.fullscreen = true;
			        		else
			        			mainMenu.fullscreen = false;
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
		
		mote = new JButton(new WiiMoteIcon(this));
		
		//this.startFlashing();

		mote.setBorderPainted(true);
		mote.setContentAreaFilled(false);
		pVerticalEmpty2.add(mote);
		
		mote.addActionListener(new WiiMoteButtonListener(mainMenu, this));
			
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
	
	/**
	 * Disegna l'immagine di sfondo
	 */
	public void paintComponent(Graphics g) {
		g.drawImage( background, 0, 0, this.getWidth(), this.getHeight(), null);
		mote.setSize(73,307);
	}
	
	/**
	 * Fa partire il falshing del wiiMote
	 */
	public void startFlashing()
	{
		this.wiiMoteFlashing = true;
		mainMenu.ok.play();
		this.animaker = AniMaker.bindAnimation("Menu/data/clips.png", 73, 307, 80, mote);
		this.animaker.startAnimation();
	}
	
	/**
	 * Stoppa il falshing del wiiMote
	 */
	public void stopFlashing()
	{
		this.wiiMoteFlashing = false;
		this.animaker.stopAnimation();
		this.animaker = null;
		this.mote.setIcon(new WiiMoteIcon(this));
	}

}