/**
 * Contains the main logic part of the game, as it processes.
 */
public class GameLogic {

    private final Map map;
    private final HumanPlayer humanPlayer;
    private final BotPlayer bot;

    /**
     * Constructor taking a Map object.
     * Initialises the necessary fields.
     */
    public GameLogic(Map map) {
        this.map = map;
        this.humanPlayer = new HumanPlayer(this.map);
        this.bot = new BotPlayer(this.map);
    }

    /**
     * @return : The map object
     */
    public Map getMap() {
        return this.map;
    }

    /**
     * @return : The human player object.
     */
    public HumanPlayer getHumanPlayer() {
        return this.humanPlayer;
    }

    /**
     * @return : The bot player object.
     */
    public BotPlayer getBot() {
        return this.bot;
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
        return "Gold owned: " + this.humanPlayer.getGoldOwned();
    }

}