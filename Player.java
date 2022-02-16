import java.util.Random;

public class Player {

	private final Map map;
	/* Current coordinates of the player */
	private int playerCoordinateX;
	private int playerCoordinateY;
	/* Hold the value of the tile the player moves on to */
	private char currentTile;
	/* Boolean flag to check whether at least one player has spawned */
	private boolean playerSpawned;

	/**
	 * Constructor which takes a Map object.
	 *
	 * @param map : a Map object passed on to it
	 */
	public Player (Map map) {
		/*
		 * Initialises the map field.
		 */
		this.map = map;
	}

	/**
	 * @return : The X coordinate of the player on the map.
	 */
	protected int getPlayerCoordinateX () {
		return this.playerCoordinateX;
	}

	/**
	 * Sets the current x coordinate of the player.
	 *
	 * @param playerCoordinateX : the current x coordinate of the player
	 */
	private void setPlayerCoordinateX (int playerCoordinateX) {
		this.playerCoordinateX = playerCoordinateX;
	}

	/**
	 * @return : The Y coordinate of the player on the map.
	 */
	protected int getPlayerCoordinateY () {
		return this.playerCoordinateY;
	}

	/**
	 * Sets the current y coordinate of the player.
	 *
	 * @param playerCoordinateY : the current y coordinate of the player
	 */
	private void setPlayerCoordinateY (int playerCoordinateY) {
		this.playerCoordinateY = playerCoordinateY;
	}

	/**
	 * @return : The value of the current tile that the player is on.
	 */

	protected char getCurrentTile () {
		return this.currentTile;
	}

	/**
	 * Stores the value of the tile the player is currently on.
	 *
	 * @param currentTile : the current tile the player is on
	 */
	protected void setCurrentTile (char currentTile) {
		this.currentTile = currentTile;
	}

	/**
	 * @return : The Map object.
	 */
	protected Map getMap () {
		return this.map;
	}

	/**
	 * Resets the tile the player was just on.
	 */
	private void resetTile () {
		this.map.getMap()[this.playerCoordinateY][this.playerCoordinateX] = this.currentTile;
	}

	/**
	 * Sets the location of the player on the map as the player is spawned.
	 *
	 * @param coordinateX : the x coordinate the player is spawned on
	 * @param coordinateY : the y coordinate the player is spawned on
	 * @param player      : the player (bot or human - i.e 'B' or 'P' respectively)
	 */
	protected void setPlayerLocation (int coordinateX, int coordinateY, char player) {
		/*
		 * Ensure that at least one player has spawned on the map before resetting a tile.
		 * Proceeds to store the value of the current tile.
		 * Changes the value that the player is currently on to display the player.
		 * Sets the coordinates of the player.
		 * Sets the boolean flag playerSpawned to true as a player has successfully been spawned.
		 */
		if (playerSpawned) {
			resetTile();
		}
		setCurrentTile(this.map.getMap()[coordinateY][coordinateX]);
		this.map.getMap()[coordinateY][coordinateX] = player;
		setPlayerCoordinateX(coordinateX);
		setPlayerCoordinateY(coordinateY);
		playerSpawned = true;
	}

	/**
	 * Spawns the player somewhere on the map randomly, whilst making sure they are not spawned in a wall, on a gold tile or in the same location as the other player.
	 *
	 * @param map   : map representation
	 * @param enemy : the enemy of the player (the bot's enemy is the human player, 'P' and the human player's enemy is the bot, 'B')
	 *
	 * @return : An array containing the player's y and x coordinates respectively.
	 */
	protected int[] spawnPlayer (char[][] map, char enemy) {
		/*
		 * Creates a new Random object.
		 * Declares and initialises the necessary fields.
		 */
		Random randNumber = new Random();
		int[] randomLocation = new int[2];
		int randomRow;
		int randomColumn;
		char location;
		/*
		 * Finds a random location on the map and spawns the player there, storing the coordinates in the randomLocation array.
		 * Ensures that the player is not spawned in a wall, on a tile holding a gold or on the same tile as the other player.
		 * Returns the randomLocation array which stores the coordinates which the player has spawned on.
		 */
		do {
			randomRow = randNumber.nextInt(map.length);
			randomColumn = randNumber.nextInt(map[0].length);
			location = map[randomRow][randomColumn];

			randomLocation[0] = randomRow;
			randomLocation[1] = randomColumn;
		} while (location == '#' || location == 'G' || location == enemy);
		return randomLocation;
	}

	/**
	 * Checks if movement is legal and updates player's location on the map.
	 *
	 * @param direction : The direction of the movement.
	 * @param player    : The player - human or bot player, 'P' or 'B' respectively.
	 *
	 * @return : Protocol if success or not.
	 */
	protected String move (char direction, char player) {
		/*
		 * Declares the necessary field
		 * Executes a set procedure based on the direction passed on to it as one of the parameters.
		 * This tries to move the player in the requested direction.
		 * Ensures that the player is not moving into a wall by calling the falseConditions method.
		 * If the call to falseConditions returns true, then this method returns "Fail" and the player does not move.
		 */
		int newLocation;
		switch (direction) {
			case 'N':
				newLocation = getPlayerCoordinateY() - 1;
				if (falseConditions(getPlayerCoordinateX(), newLocation)) {
					return "Fail";
				}
				setPlayerLocation(getPlayerCoordinateX(), newLocation, player);
				break;
			case 'S':
				newLocation = getPlayerCoordinateY() + 1;
				if (falseConditions(getPlayerCoordinateX(), newLocation)) {
					return "Fail";
				}
				setPlayerLocation(getPlayerCoordinateX(), newLocation, player);
				break;
			case 'E':
				newLocation = getPlayerCoordinateX() + 1;
				if (falseConditions(newLocation, getPlayerCoordinateY())) {
					return "Fail";
				}
				setPlayerLocation(newLocation, getPlayerCoordinateY(), player);
				break;
			case 'W':
				newLocation = getPlayerCoordinateX() - 1;
				if (falseConditions(newLocation, getPlayerCoordinateY())) {
					return "Fail";
				}
				setPlayerLocation(newLocation, getPlayerCoordinateY(), player);
				break;
			default:
				return "Fail";
		}
		/*
		 * After every move made by either player, check whether the bot has caught the human player or not by calling the assessEndConditions method.
		 * Proceed to return "Success" if the game has not ended.
		 */
		assessEndConditions();
		return "Success";
	}

	/**
	 * Checks whether it is legal for the player to move in the requested direction or not.
	 *
	 * @param coordinateX : the x coordinate of the tile the player is requesting to move to
	 * @param coordinateY : the y coordinate of the tile the player is requesting to move to
	 *
	 * @return : boolean value stating whether the move is illegal or not (true -> illegal, false -> legal).
	 */
	private boolean falseConditions (int coordinateX, int coordinateY) {
		/*
		 * Initialises the local boolean flag falseCondition to false.
		 * Tries to check the tile which the player is requesting to move on to.
		 * If the tile is a wall, set the falseCondition flag to true.
		 * If the tile is out of bounds (not in the original map), set the falseConditions flag to true as areas outside the map are walls.
		 * Return the falseCondition boolean flag.
		 */
		boolean falseCondition = false;
		try {
			char locationContent = this.map.getMap()[coordinateY][coordinateX];
			if (locationContent == '#') {
				falseCondition = true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			falseCondition = true;
		}
		return falseCondition;
	}

	/**
	 * Converts the map from a 2D char array to a single string.
	 * Ensures that the player only views the appropriate portion of the map - the player is in the middle and they only see a set number of tiles around them.
	 *
	 * @return : A String representation of the game map.
	 */
	protected String look (char player) {
		/*
		 * Initialise a StringBuilder object arrayToString.
		 * Get the x and y dimensions of the map and store them in the dimensionX and dimensionY local fields respectively.
		 * Declare and initialise the necessary values.
		 * Try to get the view of the player on the x and y axis by dividing the appropriate dimension by 2.
		 * If the number of tiles in the view is even, store it in the appropriate field.
		 * Otherwise, add 1 and then store it in the appropriate field.
		 */
		StringBuilder arrayToString = new StringBuilder();
		int dimensionX = this.map.getMap()[0].length;
		int dimensionY = this.map.getMap().length;
		int xView;
		xView = dimensionX % 2 == 0 ? (dimensionX / 2) : (dimensionX / 2) + 1;
		int yView;
		yView = dimensionY % 2 == 0 ? (dimensionY / 2) : (dimensionY / 2) + 1;
		/* Initialise xCoord and yCoord and give them the value of -1 */
		int xCoord = -1;
		int yCoord = -1;
		/*
		 * Go through every element in the map.
		 * Once the player is found, store the x and y values in the xCoord and yCoord fields which have already been initialised respectively.
		 * Break out of the outer loop, given the label 'outerLoop'.
		 */
		outerLoop:
		for (int i = 0; i < this.map.getMap().length; i++) {
			for (int j = 0; j < this.map.getMap()[i].length; j++) {
				if (this.map.getMap()[i][j] == player) {
					xCoord = j;
					yCoord = i;
					break outerLoop;
				}
			}
		}
		/* Calculate the limits on the player's view. */
		int yUpperLimit = yCoord - yView;
		int yLowerLimit = yCoord + yView;
		int xLeftLimit = xCoord - xView;
		int xRightLimit = xCoord + xView;

		/* Ensure that there are equal tiles on either side of the player as the player is supposed to be in the middle. */
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
		/*
		 * Go through every element in the map which is meant to be visible to the player.
		 * Store it in the arrayToString StringBuilder object.
		 * If ArrayIndexOutOfBoundsException exception occurs it means that the element is a wall.
		 */
		for (int i = yUpperLimit; i < yLowerLimit; i++) {
			for (int j = xLeftLimit; j < xRightLimit; j++) {
				try {
					arrayToString.append(this.map.getMap()[i][j]);
				} catch (ArrayIndexOutOfBoundsException e) {
					arrayToString.append('#');
				}
			}
			arrayToString.append('\n');
		}

		/*
		 * Ensure that there are no empty characters in the string representation of the map.
		 * If so, replace it with a '#'.
		 */
		for (int i = 0; i < arrayToString.toString().length(); i++) {
			if (arrayToString.toString().charAt(i) == '\0') {
				arrayToString.setCharAt(i, '#');
			}
		}
		/* Convert the arrayToString StringBuilder object to string and return it. */
		return arrayToString.toString();
	}

	/**
	 * This game is implemented in a way such that there are always 2 players on the map.
	 * Once called, checks how many players are on the whole map. If there are less than 2, then that means the human and the bot player are on the same tile.
	 * If both players are on the same tile, the user is informed that they've lost and the game ends as the bot has caught the human player.
	 */
	private void assessEndConditions () {
		/*
		 * Initialise the local field playersFound to 0.
		 * Go through every character in the map, and whenever either player is found, increment the playersFound field.
		 */
		int playersFound = 0;
		for (char[] chars : this.map.getMap()) {
			for (char aChar : chars) {
				if (aChar == 'P' || aChar == 'B') {
					playersFound++;
				}
			}
		}
		if (playersFound < 2) {
			System.out.println("\u001B[31mLOSE - Caught by the bot! Game over.\u001B[0m");
			System.exit(0);
		}
	}

}
