package lobby;

public class HostedGameInfo {

	String hostName;
	String gameName;
	String ip;
	int porta;
	
	public HostedGameInfo(String hostName, String gameName, String ip, int porta)
	{
		this.hostName = hostName;
		this.gameName = gameName;
		this.ip = ip;
		this.porta = porta;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}

	@Override
	public String toString() {
		return this.hostName;
	}
	
}
