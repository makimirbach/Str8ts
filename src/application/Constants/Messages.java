package application.Constants;

public class Messages {
	
	public static final String CLICK_START ="Start";
	public static final String START_GAME="Do you want to play? Select a field size, click on " + CLICK_START + " and have fun!";
	public static final String OVER_MENU = "Game over!";
	public static final String OVER_HEADER = "So much fun!";
	public static final String GAME_OVER = "Yay, you won. Wanna play again?";
	public static final String CHOOSE_SIZE = "Size";
	public static final String HELP_MENU = "Rules";
	public static final String HINTS = "Hints";
	public static final String RULES = "Every row and column can contain the numbers from 1 to n (size!) at most once. \nWhite numbers from black cells are also blocked for this row and column. "
			+ "Between black blocks, there are so called str8ts. They have to contain consecutive numbers. \n"
			+ "For example \"2,4,3\" is valid, \"2,1,4\" not.\n"
			+ "If you check the box on \""+HINTS+"\", correct numbers appear green, false ones in red.";
	public static final String RULES_HEADER = "How does the game work?";
	
}
