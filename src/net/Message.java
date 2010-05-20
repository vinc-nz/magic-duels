package net;


/**
 * builds or parse protocol messages
 * @author spax
 *
 */
public class Message {

	
	/**
	 * checks an incoming message to see if notifies a ready client
	 * @param message
	 * @return true if it's a client ready message
	 */
	public static boolean clientReady(String message) {
		return message.equals("#ready#");
	}
	
	
	/**
	 * builds the message to notify the server that the client is ready
	 * @param id the player id
	 * @return the message
	 */
	public static String getClientReadyMessage() {
		return "#ready#";
	}
	
	
	/**
	 * checks an incoming message to see if notifies a client join request
	 * @param message
	 * @return
	 */
	public static boolean clientJoin(String message) {
		return message.contains("join>");
	}
	
	/**
	 * builds the message to notify the server that the client wants to join
	 * @param clientName the name of client's player
	 * @return the message
	 */
	public static String getJoinClientMessage(String clientName) {
		return "join>"+clientName;
	}
	
	
	/**
	 * given a client join message gets it's player name
	 * @param message
	 * @return the name of client's player
	 */
	public static String getClientName(String message) {
		return message.split(">")[1];
	}
	
	
	/**
	 * builds a message to notify a client that has been accepted
	 * @param id the player id that will be assigned to the client
	 * @param numberOfPlayers number of players
	 * @return the message
	 */
	public static String getClientAcceptedMessage(int id, int numberOfPlayers) {
		return "ok>"+Integer.toString(id)+">"+Integer.toString(numberOfPlayers);
	}
	
	public static int getNumberOfPlayers(String message) {
		return Integer.parseInt(message.split(">")[2]);
	}
	
	
	/**
	 * check if the message notifies that the client has been accepted
	 * @param message
	 * @return the id to assign to the player, -1 if the message isn't a client accepted one
	 */
	public static int clientAccepted(String message) {
		if (message.contains("ok>"))
			return Integer.parseInt(message.split(">")[1]);
		return -1;
	}
	 
	
	/**
	 * builds the message that notifies the server is ready
	 * @param names the names of all players
	 * @return the message
	 */
	public static String getServerReadyMessage(String[] names) {
		String message = "ready>";
		for (int i = 0; i < names.length; i++) {
			message = message + "-" + names[i];
		}
		return message;
	}
	
	
	/**
	 * checks an incoming message to see if notifies the server is ready
	 * @param message
	 * @return true if it's a server ready message
	 */
	public static boolean serverReady(String message) {
		if (message.contains("ready>"))
			return message.split(">")[1].split("-").length>0;
		return false;
	}
	
	
	/**
	 * given a server ready message gets the name of all players
	 * @param message
	 * @return an array of all names
	 */
	public static String[] getPlayersName(String message) {
		String names = message.split(">")[1];
		return names.split("-");
	}
	
	
	/**
	 * builds a message for moving a player
	 * @param id the id of the player to move
	 * @param direction the parameter of the move method
	 * @return the message
	 */
	public static String getMoveMessage(int id, String direction) {
		return Integer.toString(id) + ">move>" + direction;
	}
	
	
	/**
	 * checks if the message notifies a movement
	 * @param message
	 * @return true if it's a movement message
	 */
	public static boolean isMovementMessage(String message) {
		return message.contains(">move>"); 
	}
	
	
	/**
	 * checks if the message notifies a spell casting
	 * @param message
	 * @return true if it's a spell casting message
	 */
	public static boolean isSpellMessage(String message) {
		return message.contains(">spell>"); 
	}
	
	
	/**
	 * gets the player id from a message
	 * @param message
	 * @return the player id
	 */
	public static int getPlayerId(String message) {
		String id = message.split(">")[0];
		return Integer.parseInt(id);
	}
	
	
	/**
	 * given a spell casting message gets the spell name
	 * @param message
	 * @return the spell name
	 */
	public static String getSpellName(String message) {
		return message.split(">")[2];
	}
	
	
	public static String getSpellMessage(int id, String spellName) {
		return Integer.toString(id) + ">spell>" + spellName;
	}
	
	
	/**
	 * given a movement message gets the direction
	 * @param message
	 * @return the direction
	 */
	public static String getMovementDirection(String message) {
		return message.split(">")[2];
	}
	
	
	/**
	 * builds a message to notify that the peer is leaving
	 * @param id the player id
	 * @return the message
	 */
	public static String getLeaveMessage(int id) {
		return Integer.toString(id) + ">leave";
	}
	
	
	/**
	 * checks an incoming message to see if notifies a peer that leaves
	 * @param message
	 * @return true if it's a leaving message
	 */
	public static boolean someoneLeaves(String message) {
		return message.contains(">leave");
	}

}
