package Menu.src;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jmegraphic.Game;

public class LayoutClient extends JFrame {

	JLabel ipLabel = new JLabel("Ip  ", SwingConstants.LEFT);
	JLabel port = new JLabel("Port  ", SwingConstants.LEFT);
	
	JTextField ipText = new JTextField();
	JTextField portText = new JTextField();
	JButton pulsante = new JButton("OK");
	
	public String numIp;
	public String numPort;
	
	Game game;


	void impostaLimite(GridBagConstraints gbc, int gx, int gy, int gw, int gh, int wx, int wy) {
		gbc.gridx = gx;
		gbc.gridy = gy;
		gbc.gridwidth = gw;
		gbc.gridheight = gh;
		gbc.weightx = wx;
		gbc.weighty = wy;
	}

	public LayoutClient(Game game) {
		super("Setting Client");
		setSize(300, 120);
		setLocation(500, 300);
		
		this.game = game;

		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		
		// impostiamo le proprietà dei componenti
		
		ipText.setEditable(true);
		portText.setEditable(true);
		
		GridBagLayout grigliaAvanzata = new GridBagLayout();		
		GridBagConstraints limite = new GridBagConstraints();
		
		panel.setLayout(grigliaAvanzata);
		
		//definiamo i limiti di ogni componente e lo aggiungiamo al pannello
		
		impostaLimite(limite,0,0,1,1,35,0); //etichetta IP host
		limite.fill = GridBagConstraints.NONE;
		limite.anchor = GridBagConstraints.EAST;
		grigliaAvanzata.setConstraints(ipLabel,limite);
		
		panel.add(ipLabel);
		
		impostaLimite(limite,1,0,1,1,65,100); //campo IP host
		limite.fill = GridBagConstraints.HORIZONTAL;
		grigliaAvanzata.setConstraints(ipText,limite);
		panel.add(ipText);
		
		impostaLimite(limite,0,1,1,1,0,0); //etichetta port
		limite.fill = GridBagConstraints.NONE;
		limite.anchor = GridBagConstraints.EAST;
		grigliaAvanzata.setConstraints(port,limite);
		panel.add(port);
		
		impostaLimite(limite,1,1,1,1,0,100); //campo port text
		limite.fill = GridBagConstraints.HORIZONTAL;
		grigliaAvanzata.setConstraints(portText,limite);
		panel.add(portText);
		
		impostaLimite(limite,0,3,2,1,0,50); // Pulsante
		limite.fill = GridBagConstraints.NONE;
		limite.anchor = GridBagConstraints.CENTER;
		grigliaAvanzata.setConstraints(pulsante,limite);
		panel.add(pulsante);
		
		pulsante.addActionListener(new Action(game, ipText, portText));
		
		setContentPane(panel); // rendiamo il pannello parte del nostro frame
		show(); // Visualizziamo il tutto!
	}
	
	class Action implements ActionListener {
		Game game;
		JTextField ipText;
		JTextField portText;
		
		
		public Action(Game game, JTextField ipText, JTextField portText){
			this.game = game;
			this.ipText = ipText;
			this.portText = portText;
		}
		
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("OK")) {
				game.initClientGame(ipText.getText(), Integer.parseInt(portText.getText()));
				game.start();
			}
		}
	}
}


