import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Main m = new Main();
        m.playGame();
    }

    /**
     * Starts the game.
     */
    private void playGame() {
        /*
         * Creates a GameLogic object.
         * Calls the setMap method to set the map.
         * Creates a new inputProcessor object.
         * States which map has been selected and that the game has started.
         * Proceeds to execute an infinite loop which asks the user for their input and processes the bot's action.
         */
        GameLogic logic;
        logic = setMap();
        InputProcessor inputProcessor = new InputProcessor(logic);
        System.out.println("\u001B[35mMap: " + logic.getMap().getMapName() + "\u001B[0m");
        System.out.println("\u001B[34mGame started!\u001B[0m");
        while (true) {
            System.out.println(inputProcessor.getNextHumanAction());
            inputProcessor.getNextBotAction();
        }
    }

    /**
     * Sets the map as requested.
     *
     * @return : A GameLogic object.
     */
    private GameLogic setMap() {
        try {
            /* Declares and initialises the necessary local variables */
            Map map = new Map();
            String individualLine;
            BufferedReader br = null;
            boolean isInputValid = false;
            char[][] mapRepresentation;
            int lineCounter = 0;
            Scanner scanner = new Scanner(System.in);
            int goldRequired = 0;
            int xDimension;
            int yDimension;
            String mapInput;
            int invalidInputCounter = 0;
            /* End of variable declaration and initialisation - loop through the following procedure if the boolean flag isInputValid is false.
             * Ask the user to enter a file name or press enter to continue and play the game on the default map.
             * If the value of invalidInputCounter is greater than 0, run scanner.nextLine() in order to consume the '\n' which isn't consumed by scanner.nextInt().
             * Store the user's input in the mapInput field.
             * Check if the input is empty, and if so, do the following:
             * Create a new Map object, calling the default constructor in order to play the game on the default map.
             * Print out a message stating that the default map has been selected and set the isInputValid boolean flag to true.
             * Otherwise, ask the user to enter the x and y dimensions of the map and store them in the xDimension and yDimension fields respectively.
             * If the input is invalid (not an integer), output a message stating that the dimensions are invalid and ask the user to try again.
             * Proceed to increment the invalidInputCounter and start the loop again.
             */
            while (!isInputValid) {
                System.out.println("If you have a custom map type in the file name, otherwise press enter to continue. The README file guides you towards selecting a custom map.");
                if (invalidInputCounter > 0) {
                    scanner.nextLine();
                }
                mapInput = scanner.nextLine();
                if (mapInput.length() == 0) {
                    map = new Map();
                    System.out.println("\u001B[32mDefault map selected!\u001B[0m");
                    isInputValid = true;
                } else {
                    try {
                        System.out.println("Enter the X dimension of the map: ");
                        xDimension = scanner.nextInt();
                        System.out.println("Enter the Y dimension of the map: ");
                        yDimension = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("\u001B[31mInvalid dimensions entered! Try again.\u001B[0m");
                        invalidInputCounter++;
                        continue;
                    }
                    /*
                     * If the input is valid, initialise the mapRepresentation field with the appropriate length.
                     * Try to read the map file entered by the user.
                     * Take the name of the map by cutting out the first 5 characters as the format must be 'name {map name}'.
                     * Try to take the stated number of gold required by the player to win by cutting out the first 4 characters as the format must be 'win [gold]'.
                     * If the obtained value is not a number, output a message stating that the format of the custom map file is invalid followed by an informative message.
                     * Exit the program with status -1.
                     */
                    mapRepresentation = new char[yDimension][xDimension];
                    try {
                        br = new BufferedReader(new FileReader(mapInput));
                        String mapName = br.readLine().substring(5);
                        try {
                            goldRequired = Integer.parseInt(br.readLine().substring(4));
                        } catch (NumberFormatException e) {
                            System.out.println("\u001B[31mInvalid custom map file format - the number of gold required by the player to win the game must be on the second line of the text file! Exiting program.\u001B[0m");
                            System.exit(-1);
                        }
                        /*
                         * Read each line from the third line onward until the value of the line being read is null.
                         * Try to store the custom map in the mapRepresentation array.
                         * If there is an ArrayIndexOutOfBoundsException exception, then that means the dimensions entered were incorrect.
                         * Output this, informing the user and proceed to exit the program with status -1.
                         */
                        while ((individualLine = br.readLine()) != null) {
                            for (int i = 0; i < individualLine.length(); i++) {
                                try {
                                    mapRepresentation[lineCounter][i] = individualLine.charAt(i);
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    System.out.println("\u001B[31mIncorrect dimensions entered or wrong map format! Exiting program.\u001B[0m");
                                    System.exit(-1);
                                }
                            }
                            lineCounter++;
                        }
                        /*
                         * Create a new Map object with the appropriate parameters passed on to its appropriate constructor.
                         * Set the isInputValid boolean flag to true.
                         * Inform the user that a custom map has been selected.
                         * If the file is not found, inform the user and increment the invalidInputCounter.
                         * If there is an IOException exception, inform the user an error has occurred and exit the program with status -1.
                         */
                        map = new Map(mapRepresentation, goldRequired, mapName);
                        isInputValid = true;
                        System.out.println("\u001B[32mCustom map selected!\u001B[0m");
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found.");
                        invalidInputCounter++;
                    } catch (IOException e) {
                        System.out.println("Error. Please try again later.");
                        System.exit(-1);
                    } finally {
                        /* Try closing the stream if the BufferedReader object is not null. */
                        if (br != null) {
                            br.close();
                        }
                    }
                }
            }
            /* Return a GameLogic object with the map passed on to it as the parameter. */
            return new GameLogic(map);
        } catch (IOException e) {
            System.out.println("Error. Please try again later.");
            System.exit(-1);
        }
        return null;
    }

}
