import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputProcessor {

    private final HumanPlayer humanPlayer;
    private final BotPlayer botPlayer;
    private final GameLogic logic;
    /* Get the user input from the console */
    private final Scanner scanner;

    /**
     * Constructor which takes a GameLogic object.
     *
     * @param logic : A GameLogic object.
     */
    public InputProcessor(GameLogic logic) {
        /*
         * Initialises the appropriate fields.
         */
        this.scanner = new Scanner(System.in);
        this.logic = logic;
        this.humanPlayer = logic.getHumanPlayer();
        this.botPlayer = logic.getBot();
    }

    /**
     * Reads player's input from the console.
     * <p>
     * return : A string containing the input the player entered.
     */
    protected String getInputFromConsole() {
        /*
         * Take the user's input and turn it into uppercase.
         * Exit the program if there is an IllegalStateException or NoSuchElementException exception.
         */
        try {
            return scanner.nextLine().toUpperCase();
        } catch (IllegalStateException | NoSuchElementException e) {
            System.exit(-1);
            return null;
        }
    }

    /**
     * Processes the command. It should return a reply in form of a String, as the protocol dictates.
     * Otherwise it should return the string "Invalid".
     *
     * @return : Processed output or Invalid if the @param command is wrong.
     */
    protected String getNextHumanAction() {
        /*
         * The input field stores the value returned when the getInputFromConsole method is called, which is the user's input.
         * Check if the input starts with the word 'move', and if so, ensure that it is 6 letters long (e.g MOVE D - where D stands for direction)
         * Take the final character, and if it is a letter then class it as the direction and do the following.
         * Return the value returned when the move method is called, with the said direction and the appropriate player being passed on as parameters.
         * Otherwise, if it's not a letter, return "Invalid".
         * If the length of the input is not 6 characters, then return "Invalid".
         */
        String input = getInputFromConsole();
        if (input.startsWith("MOVE")) {
            if (input.length() == 6) {
                char direction = input.substring(5, 6).charAt(0);
                if (Character.isLetter(direction)) {
                    return this.humanPlayer.move(input.substring(5, 6).charAt(0), 'P');
                }
                return "Invalid";
            }
            return "Invalid";
        }
        /*
         * Run the appropriate method based on the input and return the value returned by said methods.
         * If the input does not match any of the set expected inputs, return "Invalid".
         */
        switch (input) {
            case ("HELLO"):
                return this.logic.hello();
            case ("GOLD"):
                return this.logic.gold();
            case ("PICKUP"):
                return this.humanPlayer.pickup();
            case ("LOOK"):
                return this.humanPlayer.look('P');
            case ("QUIT"):
                this.humanPlayer.quitGame();
                return null;
            default:
                return "Invalid";
        }
    }

    /**
     * Processes the bot player's next action.
     */
    protected void getNextBotAction() {
        this.botPlayer.processAction();
    }

}
