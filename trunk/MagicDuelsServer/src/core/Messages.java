package core;

public class Messages {

	public static String WELCOME = "WLC";	
	public static String CLOSE = "BYE";
	public static String KILL = "KILL";
	
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
	
	//CREATE>NOMEGAME;NUMPLAYERS;NUMPORTA
	public static String CREATE = "CREATE>";
	
	public static String CREATEOK = "CREATEOK";
	public static String CREATEFAILED = "CREATEFAILED";
	
	//JOIN>GAMENAME
	public static String JOIN = "JOIN>";
	
	//JOINOK>NUMSLOT;TIPOSLOT1;SLOTCONTEMT1;TIPOSLOT2;SLOTCONTEMT2;ECC
	public static String JOINOK = "JOINOK>";
	public static String JOINFAILED = "JOINFAILED";
	
	public static String LEAVE = "LEAVE";
	
	//CHANGESLOTTYPE>SLOTINDEX;SLOTTYPE  (it's the same for server-> and client->server)
	public static String CHANGESLOTTYPE = "CHANGESLOTTYPE>";
	
	public static String PLAYERKICKED = "PLAYERKICKED";
	
	//KICKPLAYER>NOMEPLAYER -- IMPLEMENTARE!
	public static String KICKPLAYER = "KICKPLAYER>";
	
	public static String GAMEKILLED = "GAMEKILLED";
	
	public static String READYTOSTART = "READYTOSTART";
	
	//STARTSERVERGAME>NUMPLAYER;NUMIA
	public static String STARTSERVERGAME = "STARTSERVERGAME>";
	public static String SERVERGAMESTARTED = "SERVERGAMESTARTED";
	public static String STARTCLIENTGAME = "STARTCLIENTGAME";
	
	public static String OPEN = "Aperto";
	public static String CLOSED = "Chiuso";
	public static String IA = "Computer";
	public static String HUMAN = "HUMAN";
	
}