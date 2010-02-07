package wiiMoteInput;

import main.java.motej.StatusInformationReport;

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

	@Override
	public void statusInformationReceived(main.java.motej.event.StatusInformationReport report) {
		// TODO Auto-generated method stub
		
	}

}
