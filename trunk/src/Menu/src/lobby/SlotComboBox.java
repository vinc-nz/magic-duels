package Menu.src.lobby;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import lobby.LobbyHostedGame;
import lobby.LobbyHostedGameSlot;
import lobby.Messages;

public class SlotComboBox extends JComboBox {

	LobbyHostedGame hostedGame;
	int index;
	
	String human;
	
	public SlotComboBox(LobbyHostedGame hostedGame, int index, LobbyHostedGameSlot hostedGameSlot) {
		
		super(new String[] {Messages.OPEN, Messages.CLOSED, Messages.IA});
		super.setOpaque(false);
				
		this.hostedGame = hostedGame;
		this.index = index;

		if(hostedGameSlot.type == Messages.HUMAN)
		{
			if(this.index == 0)
			{
				super.removeAllItems();
				super.addItem(hostedGameSlot.human);
			} 
			else
			{
				super.addItem(hostedGameSlot.human);
				super.setSelectedIndex(3);
			}
		} else
		{
			super.setSelectedItem(hostedGameSlot.type);
		}
		
		super.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent act)
			{
				JComboBox cb = (JComboBox)act.getSource();
				SlotComboBox.this.hostedGame.changeSlotState(SlotComboBox.this.index, (String)cb.getSelectedItem());
				if(cb.getItemCount() == 4)
					cb.removeItemAt(3);
			}
		});

	}
	
}