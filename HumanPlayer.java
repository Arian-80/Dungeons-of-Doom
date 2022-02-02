/**
 * Runs the game with a human player and contains code needed to read inputs.
 */
public class HumanPlayer {
    /* Number of gold owned by the player */
    private int goldOwned;


    /* Default constructor, sets goldOwned to 0 (default value). */
    public HumanPlayer() {
        this.goldOwned = 0;
    }

    /**
     * @return : The gold owned by the player.
     */
    protected int getGoldOwned() {
        return this.goldOwned;
    }

    protected void incrementGoldOwned() {
        this.goldOwned++;
    }

}