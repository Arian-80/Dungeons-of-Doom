import java.util.Random;

public class BotPlayer extends Player {

    /* Current view of the map from the bot's perspective */
    private final StringBuilder botMapPerspective;
    /* The coordinates of the human player as the bot spots the player */
    private final int[] humanPlayerCoordinates = new int[2];
    /* Boolean flag to check whether the human player is in the view of the bot */
    boolean isPlayerInSight = false;
    /* Counter to keep a track of how many times the bot has moved thinking it knows the human player's location */
    int moveCounter = 0;
    /* Boolean flag to check whether the bot has moved already without knowing where the player is */
    private boolean playerMoved = true;

    /**
     * Constructor which takes a Map object.
     * Runs the necessary methods and initialises the necessary field.
     *
     * @param map : Map object
     */
    public BotPlayer(Map map) {
        super(map);
        this.botMapPerspective = new StringBuilder();
        int[] location = spawnPlayer(map.getMap(), 'P');
        setPlayerLocation(location[1], location[0], 'B');
    }

    /**
     * Processes the bot's next action based on multiple factors.
     * Runs different methods based on the value of multiple boolean flags.
     */
    protected void processAction() {
        /*
         * If the human player is in sight, check if the bot has moved without looking at the map more than twice.
         * If so, look at the map again and reset the moveCounter back to 0.
         * Otherwise, run the processNextMove method and pass on the human player's coordinates and increment the moveCounter.
         */
        if (isPlayerInSight) {
            if (moveCounter % 2 == 0 && moveCounter != 0) {
                isPlayerInSight = this.look();
                moveCounter = 0;
            } else {
                processNextMove(this.humanPlayerCoordinates[1], this.humanPlayerCoordinates[0]);
                moveCounter++;
            }
            /*
             * If the human is player is not in sight, check whether the playerMoved boolean flag is true or not.
             * If it is true, that means the bot has moved without vision of the human player already, so look at the map again and set the flag to false.
             * Otherwise, the moveRandomly method is executed which makes the bot move randomly, and the flag is set to true.
             */
        } else {
            if (this.playerMoved) {
                isPlayerInSight = this.look();
                this.playerMoved = false;
            } else {
                moveRandomly(new Random());
                this.playerMoved = true;
            }
        }
    }

    /**
     * Overloads the move method in the superclass.
     * Depending on the boolean flag moveTowardsPlayer, this method runs the appropriate method.
     *
     * @param moveTowardsPlayer : Boolean flag set to determine whether the bot should move towards the player or not.
     * @param rand              : A Random object.
     * @param diffInCoordX      : Difference in the x coordinates of the human and the bot player.
     * @param diffInCoordY      : Difference in the y coordinates of the human and the bot player.
     */
    private void move(boolean moveTowardsPlayer, Random rand, int diffInCoordX, int diffInCoordY) {
        /*
         * If the moveTowardsPlayer field is true, then run the moveTowardsPlayer method and pass the appropriate parameters.
         * Otherwise, run the moveRandomly method so the bot moves at random.
         */
        if (moveTowardsPlayer) {
            moveTowardsPlayer(diffInCoordX, diffInCoordY);
        } else {
            moveRandomly(rand);
        }
    }


    /**
     * Method which causes the bot to move randomly on the map.
     *
     * @param rand : A Random object.
     */
    private void moveRandomly(Random rand) {
        /*
         * The randInt field stores a number chosen at random between 0 to 3.
         * The move method in the superclass is called; however, the direction depends on the random number.
         */
        int randInt = rand.nextInt(4);
        switch (randInt) {
            case 0:
                super.move('N', 'B');
                break;
            case 1:
                super.move('E', 'B');
                break;
            case 2:
                super.move('S', 'B');
                break;
            case 3:
                super.move('W', 'B');
                break;
        }
    }

    /**
     * Executes the appropriate procedure based on the value of the parameters passed on to it.
     *
     * @param diffInCoordX : Difference in the x coordinates of the human and the bot player.
     * @param diffInCoordY : Difference in the y coordinates of the human and the bot player.
     */
    private void moveTowardsPlayer(int diffInCoordX, int diffInCoordY) {
        /*
         * This method is run when the bot is supposed to move towards the player.
         * If the difference in the x coordinates of the human and the bot player is less than 0, move to the left.
         * If the difference in the x coordinates is 0, check if the difference in the y coordinates is less than 0.
         * If so, move up. Otherwise, move down.
         * If the difference in the x coordinates is bigger than 0, move right.
         */
        if (diffInCoordX < 0) {
            move('W', 'B');
        } else if (diffInCoordX == 0) {
            if (diffInCoordY < 0) {
                move('N', 'B');
            } else {
                move('S', 'B');
            }
        } else {
            move('E', 'B');
        }
    }

    /**
     * Processes the bot's next move - sets a 0.5 probability that the bot moves towards the player given that it has sight of the player.
     * Calculates the difference between the bot and the human's x & y coordinates.
     * Calls the overloaded move method and passes the appropriate parameters on.
     *
     * @param humanCoordX : The x coordinates of the human player.
     * @param humanCoordY : The y coordinates of the human player.
     */
    private void processNextMove(int humanCoordX, int humanCoordY) {
        /*
         * Create a new Random object.
         * The randInt field stores a number, either 0 or 1.
         * Calculate the difference in the x and y coordinates of the human and the bot player.
         * Call the overloaded local move method and pass on the appropriate values.
         * If the number in randInt is 0, then the moveTowardsPlayer flag is set as true. Otherwise, it's set as false.
         */
        Random rand = new Random();
        int randInt = rand.nextInt(2);
        int diffInCoordY = humanCoordY - getPlayerCoordinateY();
        int diffInCoordX = humanCoordX - getPlayerCoordinateX();
        move(randInt == 0, rand, diffInCoordX, diffInCoordY);
    }

    /**
     * Checks whether the human player is in view of the bot.
     *
     * @return : Boolean flag determining whether the bot can see the human player.
     */
    private boolean isPlayerLocated() {
        /*
         * Initialises the isPlayerInSight boolean flag and sets it to false.
         * Goes through every tile in the map which the bot player can see and checks if the player is in view.
         * If so, gets the coordinates of the human players and sets the value of the isPlayerInSight boolean flag to true.
         */
        boolean isPlayerInSight = false;
        for (char aChar : this.botMapPerspective.toString().toCharArray()) {
            if (aChar == 'P') {
                getHumanCoordinates();
                isPlayerInSight = true;
            }
        }
        return isPlayerInSight;
    }

    /**
     * Overloading the look method in the superclass.
     * Calls the look method in the superclass.
     * Stores the map which the bot can see in the botMapPerspective array.
     *
     * @return : Boolean flag to determine whether the player is in sight of the bot or not.
     */
    private boolean look() {
        /*
         * observedStringMap stores the string 'map' returned when the look method in the superclass is called.
         * Go through every element in the string and add it to the StringBuilder object botMapPerspective.
         * Out of bound elements are added as '#'.
         * Try and locate the player by running the isPlayerLocated method and return the value returned by it.
         */
        String observedStringMap = look('B');
        for (int i = 0; i < super.getMap().getDimensionY(); i++) {
            for (int j = 0; j < super.getMap().getDimensionX(); j++) {
                try {
                    this.botMapPerspective.append(observedStringMap.charAt((i * super.getMap().getDimensionX()) + j));
                } catch (StringIndexOutOfBoundsException e) {
                    this.botMapPerspective.append('#');
                }
            }
        }
        return isPlayerLocated();
    }

    /**
     * Runs a small loop to find the human player coordinates on the map.
     * Proceeds to store the human player coordinates in the humanPlayerCoordinates array.
     */
    private void getHumanCoordinates() {
        /*
         * Go through every element in the map.
         * Store the coordinates of the human player.
         */
        for (int i = 0; i < super.getMap().getMap().length; i++) {
            for (int j = 0; j < super.getMap().getMap()[i].length; j++) {
                if (super.getMap().getMap()[i][j] == 'P') {
                    humanPlayerCoordinates[0] = i;
                    humanPlayerCoordinates[1] = j;
                }
            }
        }
    }

}
