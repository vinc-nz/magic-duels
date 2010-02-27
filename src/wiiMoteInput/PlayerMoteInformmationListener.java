package wiiMoteInput;

import motej.StatusInformationReport;
import motej.event.StatusInformationListener;

public class PlayerMoteInformmationListener implements StatusInformationListener {

	PlayerMote playerMote;
	
	public PlayerMoteInformmationListener(PlayerMote playerMote) {
		this.playerMote = playerMote;
	}

	@Override
	public void statusInformationReceived(final StatusInformationReport report) {
		if (report != null) {

			playerMote.setBatteryLevel(report.getBatteryLevel() & 0xff);

		}
		
	}


}
