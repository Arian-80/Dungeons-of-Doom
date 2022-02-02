import java.util.Arrays;
import java.util.Random;

/**
 * Contains the main logic part of the game, as it processes.
 */
public class GameLogic {

    private Map map;
    private HumanPlayer player;
    private Random random;

    /**
     * Default constructor
     */
    public GameLogic() {
        this.map = new Map();
        this.player = new HumanPlayer();
        random = new Random();
    }

    /**
     * Checks if the game is running
     *
     * @return if the game is running.
     */
    protected boolean gameRunning() {
        return false;
    }

    /**
     * Returns the gold required to win.
     *
     * @return : Gold required to win.
     */
    protected String hello() {
        return ("Gold to win: " + this.map.getGoldRequired());
    }

    /**
     * Returns the gold currently owned by the player.
     *
     * @return : Gold currently owned.
     */
    protected String gold() {
        return "Gold owned: " + this.player.getGoldOwned();
    }

    /**
     * Checks if movement is legal and updates player's location on the map.
     *
     * @param direction : The direction of the movement.
     * @return : Protocol if success or not.
     */
    protected String move(char direction) {
        int newLocation;
        switch (direction) {
            case 'N':
                newLocation = this.map.getPlayerCoordinateY() - 1;
                if (falseConditions(this.map.getPlayerCoordinateX(), newLocation)) {
                    return "Fail";
                }
                this.map.setPlayerLocation(this.map.getPlayerCoordinateX(), newLocation);
                break;
            case 'S':
                newLocation = this.map.getPlayerCoordinateY() + 1;
                if (falseConditions(this.map.getPlayerCoordinateX(), newLocation)) {
                    return "Fail";
                }
                this.map.setPlayerLocation(this.map.getPlayerCoordinateX(), newLocation);
                break;
            case 'E':
                newLocation = this.map.getPlayerCoordinateX() + 1;
                if (falseConditions(newLocation, this.map.getPlayerCoordinateY())) {
                    return "Fail";
                }
                this.map.setPlayerLocation(newLocation, this.map.getPlayerCoordinateY());
                break;
            case 'W':
                newLocation = this.map.getPlayerCoordinateX() - 1;
                if (falseConditions(newLocation, this.map.getPlayerCoordinateY())) {
                    return "Fail";
                }
                this.map.setPlayerLocation(newLocation, this.map.getPlayerCoordinateY());
                break;
            default:
                return "Fail";
        }
        return "Success";
    }

    /**
     * Converts the map from a 2D char array to a single string.
     *
     * @return : A String representation of the game map.
     */
    protected String look() {
        char[][] currentMap = this.map.getMap();
        StringBuilder arrayToString = new StringBuilder();
        int xMapLength = currentMap[0].length;
        int yMapLength = currentMap.length;
        int xView;
        xView = xMapLength % 2 == 0 ? (xMapLength / 2) : (xMapLength / 2) + 1;
        int yView;
        yView = yMapLength % 2 == 0 ? (yMapLength / 2) : (yMapLength / 2) + 1;
        int xCoord = -1;
        int yCoord = -1;
        outerLoop:
        for (int i = 0; i < currentMap.length; i++) {
            for (int j = 0; j < currentMap[i].length; j++) {
                if (currentMap[i][j] == 'P') {
                    xCoord = j;
                    yCoord = i;
                    break outerLoop;
                }
            }
        }
        int yUpperLimit = yCoord - yView;
        int yLowerLimit = yCoord + yView;
        int xLeftLimit = xCoord - xView;
        int xRightLimit = xCoord + xView;

        if ((yCoord - yUpperLimit) > (yLowerLimit - yCoord)) {
            yLowerLimit++;
        } else {
            yUpperLimit++;
        }
        if ((xCoord - xLeftLimit) > (xRightLimit - xCoord)) {
            xRightLimit++;
        } else {
            xLeftLimit++;
        }
        for (int i = yUpperLimit; i < yLowerLimit; i++) {
            for (int j = xLeftLimit; j < xRightLimit; j++) {
                try {
                    arrayToString.append(currentMap[i][j]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    arrayToString.append('#');
                }
            }
            arrayToString.append("\n");
        }
        return arrayToString.toString();
    }

    /**
     * Processes the player's pickup command, updating the map and the player's gold amount.
     *
     * @return If the player successfully picked-up gold or not.
     */
    protected String pickup() {
        if (this.map.getCurrentTile() == 'G') {
            this.map.setCurrentTile('.');
            this.player.incrementGoldOwned();
            return "Success. Gold owned: " + this.player.getGoldOwned();
        }
        return "Fail. Gold owned: " + this.player.getGoldOwned();
    }

    /**
     * Quits the game, shutting down the application.
     */
    protected void quitGame() {
        if (this.map.getCurrentTile() == 'E' && this.player.getGoldOwned() >= this.map.getGoldRequired()) {
            System.out.println("WIN");
        } else {
            System.out.println("LOSE");
        }
        System.exit(0);
    }

    private boolean falseConditions(int coordinateX, int coordinateY) {
        boolean falseCondition = false;
        char locationContent = this.map.getMap()[coordinateY][coordinateX];
        if (locationContent == '#') {
            falseCondition = true;
        }
        return falseCondition;
    }

    protected void botNextAction() {
        int randNum = random.nextInt(4);
        switch (randNum) {
            case 0:
                botMove('N');
                break;
            case 1:
                botMove('S');
                break;
            case 2:
                botMove('E');
                break;
            case 3:
                botMove('W');
        }
        int[] playerLocation = this.map.getPlayerLocation();
        int[] botLocation = this.map.getBotLocation();
        if (Arrays.equals(playerLocation, botLocation)) {
            System.out.println("Captured by the bot! LOSE");
            System.exit(0);
        }
    }

    private void botMove(char direction) {
        int newLocation;
        switch (direction) {
            case 'N':
                newLocation = this.map.getBotCoordinateY() - 1;
                if (falseConditions(this.map.getBotCoordinateX(), newLocation)) {
                    return;
                }
                this.map.setBotLocation(this.map.getBotCoordinateX(), newLocation);
                break;
            case 'S':
                newLocation = this.map.getBotCoordinateY() + 1;
                if (falseConditions(this.map.getBotCoordinateX(), newLocation)) {
                    return;
                }
                this.map.setBotLocation(this.map.getBotCoordinateX(), newLocation);
                break;
            case 'E':
                newLocation = this.map.getBotCoordinateX() + 1;
                if (falseConditions(newLocation, this.map.getBotCoordinateY())) {
                    return;
                }
                this.map.setBotLocation(newLocation, this.map.getBotCoordinateY());
                break;
            case 'W':
                newLocation = this.map.getBotCoordinateX() - 1;
                if (falseConditions(newLocation, this.map.getBotCoordinateY())) {
                    return;
                }
                this.map.setBotLocation(newLocation, this.map.getBotCoordinateY());
                break;
        }
    }
}