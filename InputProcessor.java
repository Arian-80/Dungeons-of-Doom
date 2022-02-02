import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputProcessor {
    /* Get the user input from the console */
    Scanner scanner;

    private GameLogic logic;


    public InputProcessor(GameLogic logic) {
        this.scanner = new Scanner(System.in);
        this.logic = logic;
    }

    /**
     * Reads player's input from the console.
     * return : A string containing the input the player entered.
     */
    protected String getInputFromConsole() {
        try {
            return scanner.nextLine().toUpperCase();
        } catch (IllegalStateException | NoSuchElementException e) {
            System.exit(-1);
            return null;
        }
    }

    protected String getNextAction() {
        String input = getInputFromConsole();
        if (input.startsWith("MOVE")) {
            if (input.length() == 6) {
                char direction = input.substring(5, 6).charAt(0);
                if (Character.isLetter(direction)) {
                    return this.logic.move(input.substring(5, 6).charAt(0));
                }
                return "Invalid";
            }
            return "Invalid";
        }
        switch (input) {
            case ("HELLO"):
                return this.logic.hello();
            case ("GOLD"):
                return this.logic.gold();
            case ("PICKUP"):
                return this.logic.pickup();
            case ("LOOK"):
                return this.logic.look();
            case ("QUIT"):
                this.logic.quitGame();
                return null;
            default:
                return "Invalid";
        }
    }
}
