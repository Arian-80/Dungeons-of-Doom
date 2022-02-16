/**
 * Reads and contains in memory the map of the game.
 */
public class Map {

    /* Representation of the map */
    private final char[][] map;

    /* Map name */
    private final String mapName;

    /* Gold required for the human player to win */
    private final int goldRequired;

    /* Dimensions of the map */
    private int dimensionX;
    private int dimensionY;

    /**
     * Default constructor, creates the default map "Very small Labyrinth of doom".
     */
    public Map() {
        /*
         * Initialises the necessary fields.
         */
        this.mapName = "Very small Labyrinth of Doom";
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
        setDimensions();
    }

    /**
     * Constructor that accepts a representation of the map, the number of gold required and the map name.
     */
    public Map(char[][] map, int goldRequired, String name) {
        /*
         * Sets the necessary fields as well as the dimensions by calling the setDimensions method.
         */
        this.map = map;
        this.goldRequired = goldRequired;
        this.mapName = name;
        setDimensions();
    }

    /**
     * @return : Gold required to exit the current map.
     */
    protected int getGoldRequired() {
        return this.goldRequired;
    }

    /**
     * @return : The representation map as stored in memory.
     */
    protected char[][] getMap() {
        return this.map;
    }


    /**
     * @return : The name of the current map.
     */
    protected String getMapName() {
        return this.mapName;
    }

    /**
     * @return : The X dimension of the current map
     */
    protected int getDimensionX() {
        return dimensionX;
    }

    /**
     * @return : The Y dimension of the current map
     */
    protected int getDimensionY() {
        return dimensionY;
    }

    /**
     * Sets the dimensions of the map.
     */
    private void setDimensions() {
        this.dimensionY = this.map.length;
        this.dimensionX = this.map[0].length;
    }

}
