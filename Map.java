import java.util.Random;

public class Map {

    /* Representation of the map */
    private char[][] map;

    /* Gold required for the human player to win */
    private int goldRequired;

    /* Current coordinates of the player */
    private int playerCoordinateX;
    private int playerCoordinateY;

    /* Current coordinates of the bot */
    private int botCoordinateX;
    private int botCoordinateY;

    /* Hold the value of the tile the player moves on to */
    private char currentTile;

    public Map() {
        this.goldRequired = 2;
        this.map = new char[][]{
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
                {'#', '.', '.', '.', '.', '.', '.', 'G', '.', '.', '.', '.', '.', '.', '.', '.', '.', 'E', '.', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
                {'#', '.', '.', 'E', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', 'G', '.', '.', '.', '.', '.', '.', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
        };
        int[] playerLocation = spawnPlayer(this.map, 'P');
        int[] botLocation = spawnPlayer(this.map, 'B');
        this.playerCoordinateX = playerLocation[1];
        this.playerCoordinateY = playerLocation[0];
        this.botCoordinateX = botLocation[1];
        this.botCoordinateY = botLocation[0];
        setCurrentTile(this.map[this.playerCoordinateY][this.playerCoordinateX]);
        setPlayerLocation(this.playerCoordinateX, this.playerCoordinateY);
        setBotLocation(botCoordinateX, botCoordinateY);
    }

    /**
     * @return : Gold required to exit the current map.
     */
    protected int getGoldRequired() {
        return this.goldRequired;
    }

    /**
     * @return : The map as stored in memory.
     */
    protected char[][] getMap() {
        return this.map;
    }

    /**
     * @return : The X coordinate of the player on the map.
     */
    protected int getPlayerCoordinateX() {
        return this.playerCoordinateX;
    }

    /**
     * @return : The Y coordinate of the player on the map.
     */
    protected int getPlayerCoordinateY() {
        return this.playerCoordinateY;
    }

    protected int getBotCoordinateX() {
        return this.botCoordinateX;
    }

    protected int getBotCoordinateY() {
        return this.botCoordinateY;
    }

    /**
     * @return : The value of the current tile that the player is on.
     */
    protected char getCurrentTile() {
        return this.currentTile;
    }

    private void setPlayerCoordinateX(int playerCoordinateX) {
        this.playerCoordinateX = playerCoordinateX;
    }

    private void setPlayerCoordinateY(int playerCoordinateY) {
        this.playerCoordinateY = playerCoordinateY;
    }

    private void setBotCoordinateX(int botCoordinateX) {
        this.botCoordinateX = botCoordinateX;
    }

    private void setBotCoordinateY(int botCoordinateY) {
        this.botCoordinateY = botCoordinateY;
    }

    protected void setCurrentTile(char currentTile) {
        this.currentTile = currentTile;
    }

    private void resetTile() {
        this.map[this.playerCoordinateY][this.playerCoordinateX] = this.currentTile;
    }

    private void resetTileBot() {
        this.map[this.botCoordinateY][this.botCoordinateX] = this.currentTile;
    }

    protected int[] spawnPlayer(char[][] map, char enemy) {
        Random randNumber = new Random();
        int[] randomLocation = new int[2];
        int randomRow;
        int randomColumn;
        char location;
        do {
            randomRow = randNumber.nextInt(map.length);
            randomColumn = randNumber.nextInt(map[0].length);
            location = this.map[randomRow][randomColumn];
            randomLocation[0] = randomRow;
            randomLocation[1] = randomColumn;
        } while (location == '#' || location == 'G' || location == enemy);
        return randomLocation;
    }

    protected void setPlayerLocation(int coordinateX, int coordinateY) {
        resetTile();
        setCurrentTile(this.map[coordinateY][coordinateX]);
        this.map[coordinateY][coordinateX] = 'P';
        setPlayerCoordinateX(coordinateX);
        setPlayerCoordinateY(coordinateY);
    }

    protected void setBotLocation(int coordinateX, int coordinateY) {
        resetTileBot();
        setCurrentTile(this.map[coordinateY][coordinateX]);
        this.map[coordinateY][coordinateX] = 'B';
        setBotCoordinateX(coordinateX);
        setBotCoordinateY(coordinateY);
    }

    protected int[] getPlayerLocation() {
        return new int[]{this.playerCoordinateX, this.playerCoordinateY};
    }

    protected int[] getBotLocation() {
        return new int[]{this.botCoordinateX, this.botCoordinateY};
    }
}
