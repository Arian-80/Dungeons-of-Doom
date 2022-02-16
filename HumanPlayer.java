/**
 * Runs the game with a human player and contains code needed to read inputs.
 */
public class HumanPlayer extends Player {

    /* Number of gold owned by the player */
    private int goldOwned;

    /**
     * Constructor which takes a Map object.
     *
     * @param map : Map object
     */
    public HumanPlayer(Map map) {
        /*
         * Spawns the player and proceeds to run methods to set the value of the currentTile field and the location of the player on the map.
         * Lastly, initialises the goldOwned field and sets it to 0.
         */
        super(map);
        int[] location = spawnPlayer(super.getMap().getMap(), 'B');
        setCurrentTile(super.getMap().getMap()[location[0]][location[1]]);
        setPlayerLocation(location[1], location[0], 'P');
        this.goldOwned = 0;
    }

    /**
     * @return : The gold owned by the player.
     */
    protected int getGoldOwned() {
        return this.goldOwned;
    }

    /**
     * Increments the gold owned by the player.
     */
    protected void incrementGoldOwned() {
        this.goldOwned++;
    }

    /**
     * Processes the player's pickup command, updating the map and the player's gold amount.
     *
     * @return : String stating whether the player successfully picked-up gold or not.
     */
    protected String pickup() {
        /*
         * If the player is standing on a tile containing a gold, then the program does the following.
         * Resets the value of the tile which the gold lies on to '.' as the gold has been picked up.
         * Increments the gold owned by the player.
         * Returns success and the gold owned by the player.
         * Otherwise, returns fail and the gold owned by the player.
         */
        if (getCurrentTile() == 'G') {
            setCurrentTile('.');
            incrementGoldOwned();
            return "Success. Gold owned: " + getGoldOwned();
        }
        return "Fail. Gold owned: " + getGoldOwned();
    }

    /**
     * Quits the game, shutting down the application. Outcome of the game based on winning conditions.
     */
    protected void quitGame() {
        /*
         * If the command has been called when the player has enough gold and is on an exit tile then output "WIN".
         * Otherwise, output "LOSE".
         * Quit the program regardless of the outcome with status 0.
         */
        if (getCurrentTile() == 'E' && getGoldOwned() >= super.getMap().getGoldRequired()) {
            System.out.println("WIN");
        } else {
            System.out.println("LOSE");
        }
        System.exit(0);
    }

}