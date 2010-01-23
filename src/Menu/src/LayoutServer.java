package Menu.src;

import javax.swing.*;
import java.io.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import jmegraphic.Game;


public class LayoutServer extends JFrame {

	JLabel port = new JLabel("Port  ", SwingConstants.LEFT);
	JTextField portText = new JTextField();
	JButton pulsante = new JButton("OK");
	
	Game game;
	
	public String numPort;
	
	void impostaLimite(GridBagConstraints gbc, int gx, int gy, int gw, int gh, int wx, int wy) {
		gbc.gridx = gx;	
		gbc.gridy = gy;
		gbc.gridwidth = gw;
		gbc.gridheight = gh;
		gbc.weightx = wx;
		gbc.weighty = wy;
	}

	public LayoutServer(Game game) { 	
	
		super("Setting Server");
		setSize(300, 120);
		setLocation(500, 300);
		this.game = game;
	
		JPanel panel = new JPanel();
	
		portText.setEditable(true);
		
		GridBagLayout grigliaAvanzata = new GridBagLayout();
		GridBagConstraints limite = new GridBagConstraints();
		panel.setLayout(grigliaAvanzata);
		
		//definiamo i limiti di ogni componente e lo aggiungiamo al pannello
		
		impostaLimite(limite,0,0,1,1,35,0); //etichetta port
		
		limite.fill = GridBagConstraints.NONE;
		limite.anchor = GridBagConstraints.EAST;
		grigliaAvanzata.setConstraints(port,limite);		
		panel.add(port);

		impostaLimite(limite,1,0,1,1,65,100); //campo port text
		limite.fill = GridBagConstraints.HORIZONTAL;
		grigliaAvanzata.setConstraints(portText,limite);		
		panel.add(portText);
		
		impostaLimite(limite,0,3,2,1,0,50); // Pulsante
		limite.fill = GridBagConstraints.NONE;
		limite.anchor = GridBagConstraints.CENTER;
		grigliaAvanzata.setConstraints(pulsante,limite);
		
		panel.add(pulsante);
		pulsante.addActionListener(new Action(game, portText));
				
		setContentPane(panel); // rendiamo il pannello parte del nostro frame
		show(); // Visualizziamo il tutto!
	}
	
	class Action implements ActionListener{
		Game game;
		JTextField portText;
		
		public Action(Game game, JTextField portText){
			this.game = game;
			this.portText = portText;
		}
		
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("OK")) {
				game.initServerGame(Integer.parseInt(portText.getText()));
				game.start();
			}
		}
	}
}





