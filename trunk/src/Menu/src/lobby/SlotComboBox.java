package Menu.src.lobby;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import lobby.LobbyHostedGame;
import lobby.Messages;

public class SlotComboBox extends JComboBox {

	LobbyHostedGame hostedGame;
	int index;
	
	public SlotComboBox(LobbyHostedGame hostedGame, int index) {
		
		super(new String[] {Messages.OPEN, Messages.CLOSED, Messages.IA});
		super.setOpaque(false);
		
		this.hostedGame = hostedGame;
		this.index = index;

		super.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent act)
			{
				JComboBox cb = (JComboBox)act.getSource();
				
				SlotComboBox.this.hostedGame.changeSlotState(SlotComboBox.this.index, (String)cb.getSelectedItem());

			}
		});

	}


	
}
