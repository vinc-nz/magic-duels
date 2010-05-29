package lobby;

/**
 * The class stores information about a game
 * @author Neb
 *
 */
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

	/**
	 * @return a string containing the game host name
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * Sets the ggame host name
	 * @param hostName the game host name
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * @return the game name
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * sets the name of the game
	 * @param gameName the name of the game
	 */
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	/**
	 * @return the ip of the game
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the ip of the game
	 * @param ip the ip of the game
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the port of the game
	 */
	public int getPorta() {
		return porta;
	}

	/**
	 * Sets the port of the game
	 * @param porta the port of the game
	 */
	public void setPorta(int porta) {
		this.porta = porta;
	}

	@Override
	public String toString() {
		return this.hostName;
	}
	
}
