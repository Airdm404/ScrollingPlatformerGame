
/**
 * Controls each level of the game
 */


public class Level{

    void setLevelWon(boolean isLevelWon) {}

    boolean isLevelWon() {}

    void setLevelLost(boolean isLevelLost) {}

    boolean isLevelLost() {}

}


/**
 * The view of our game
 */

class GameView {
    Level levelObject = new Level();


    /**
     * displays a screen when player wins level
     */
    private void diplayLossPopUp(levelObject.isLevelWon) {}



    /**
     * displays a screen when player loses level
     */
    private void displayWonPopUp(levelObject.isLevelLoss) {}


}