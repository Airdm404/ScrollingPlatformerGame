// This case tests for the program's ability to identify that the player has reached a goal point

/**
 * The model of our game
 */
class GameModel {

  private PlayerEntity currentPlayer = new PlayerEntity();
  private GoalEntity currentGoal = new GoalEntity();

  /**
   * Updates the current GameModel
   */
  public void updateGame() {
    checkForGoalReached();
  }

  /**
   * Checks to see if the goal has been reached by looking at each goal currently in the Game and
   * then if the goal has been reached, handling that case separately
   */
  private void checkForGoalReached() {
      if (currentGoal.wasGoalReached()) {
        handleGoal(currentGoal);
      }
  }

  /**
   * Handles the situation where the player has arrived at a goal
   */
  private void handleGoal(GoalEntity goal) {
    displayWinScreen();
  }

  /**
   * Displays the winning screen after the player has finished the level
   */
  private void displayWinScreen() {
    // Obviously this will be beefed up, this is just a placeholder
    System.out.println("you won!");
  }

}

/**
 * This class represents a goal point somewhere on the map
 */
class GoalEntity {

  /**
   * Reveals whether or not the player has reached the Goal
   * @return true, as a dummy variable, it would actually return something else later
   */
  public boolean wasGoalReached() {
    return true;
  }
}