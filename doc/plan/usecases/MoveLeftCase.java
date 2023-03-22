// This case tests for the program's ability to handle the user moving left and to update
// accordingly

/**
 * The controller of our game
 */
class GameController {
  private static final String MOVE_LEFT = "move left";

  /**
   * Responds to a press of a key
   * @param code represents the key that the user pressed
   */
  public void keyHandler(KeyCode code) {
    if (code.equals(KeyCode.LEFT)) {
      dispatchEvent(MOVE_LEFT);
    }
  }

  /**
   * Fires an event with a type of eventType to let the holder of the controller know that something
   * has happened
   *
   * @param eventType the type of event that has occured (i.e. "move right", "move left")
   */
  private void dispatchEvent(String eventType) {
    fireEvent(new Event(eventType));
  }
}

/**
 * The view of our game
 */
class GameView {

  private GameController currentController;
  private GameModel currentModel = new GameModel();
  private static final String MOVE_LEFT = "move left";

  /**
   * Instantiates the game controller that will run the game - build an EventHandler to catch
   * events coming from GameController
   */
  private void buildGameController() {
    currentController = new GameController();
    controller.addEventHandler(EventType.ROOT, event -> handleControllerEvent(
        event.getEventType().getName()));
  }

  /**
   * Responds to the situation where an event has happened in currentController
   * @param event the String representation of the event that has occurred
   */
  private void handleControllerEvent(String event) {
      if (event.equals(MOVE_LEFT)) {
        // Sets the player's next motion to be -1 in the x direction, 0 in the y direction
        currentModel.getPlayer().setMotion(new Vector2D(-1, 0));
      }
  }
}

/**
 * Represents a game model
 */
class GameModel {

  /**
   * Returns the player associated with this game
   * @return a new player entity object, in the real code, this would be a preinstantiated object
   */
  public PlayerEntity getPlayer() {
    return new PlayerEntity();
  }
}

/**
 * Represents the player of the game
 */
class PlayerEntity {
  Vector2D currentMotion;

  /**
   * Sets the current motion of the 2D vector to equal vect
   * @param vect the new motion vector for the player to move with
   */
  public void setMotion(Vector2D vect) {
    motion = vect;
  }
}

/**
 * Represents a 2D vector
 */
class Vector2D {
  private double xcomponent;
  private double ycomponent;

  /**
   * Builds a new 2D vector
   * @param x the x component of the vector
   * @param y the y component of the vector
   */
  public Vector2D(double x, double y) {
    xcomponent = x;
    ycomponent = y;
  }

}