package lobby;

public class Messages {

	public static String WELCOME = "WLC";	
	public static String CLOSE = "BYE";
	
	public static String CHAT = "CHAT>";
	
	// NEWUSER>NOMEUTENTE;PASSWORD;MAIL
	public static String NEWUSER = "NEWUSER>";

	public static String NEWUSEROK = "NEWUSEROK";
	public static String NEWUSERFAILED = "NEWUSERFAILED";

	// LOGIN>UTENTE;PASSWORD
	public static String LOGIN = "LOGIN>";
	
	public static String LOGINOK = "LOGINOK";
	public static String LOGINFAILED = "LOGINFAILED";
	
	public static String LOBBYSTARTED = "LOBBYSTARTED";

	// CLIENTLIST>NOME1;NOME2;ECC
	public static String CLIENTLIST = "CLIENTLIST>";	
	
	// GAMELIST>NOMEGAME1;IP1;PORTA1;NOMEGAME2;IP2;PORTA2;ECC
	public static String GAMELIST = "GAMELIST>";
	
	//CREATE>IP;PORT
	public static String CREATE = "CREATE>";
	
	// JOIN>IP;PORT
	public static String JOIN = "JOIN>";
	
}
