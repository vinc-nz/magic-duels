package lobby;

public class HostedGameInfo {

	String hostName;
	String gameName;
	String ip;
	int porta;
	
	public HostedGameInfo(String hostName, String gameName, String ip, int porta) {
		super();
		this.hostName = hostName;
		this.gameName = gameName;
		this.ip = ip;
		this.porta = porta;
	}

	@Override
	public String toString() {
		return this.hostName;
	}
	
}
