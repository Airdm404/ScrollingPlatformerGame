package model.collision;

import api.model.collision.ICollisionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a glorified version of a list of Directions enums.
 * It exists to make handling collisions with multiple directions at once
 * easier, and to simplify comparing collisions with multiple directions.
 *
 * @author Ryan Krakower
 */
public class CollisionDirections implements ICollisionHandler {
  public List<Direction> directionsList = new ArrayList<>();

  /**
   * A constructor that takes in a list of Directions
   * @param directions list of Directions of the current collision
   */
  public CollisionDirections(List<Direction> directions) {
    directionsList = directions;
  }

  /**
   * A constructor that creates an empty CollisionDirections object
   */
  public CollisionDirections() {}

  /**
   * Adds a new Direction to the collision
   * @param direction new direction to be added
   */
  @Override
  public void add(Direction direction) {
    if (!directionsList.contains(direction)) {
      directionsList.add(direction);
    }
  }

  /**
   * Takes in another CollisionDirections object, and adds all of its Directions to the current
   * collision of this object
   * @param directions other CollisionDirections object
   */
  @Override
  public void add(ICollisionHandler directions) {
    for (Direction direction : directions.getRawList()) {
      this.add(direction);
    }
  }

  /**
   * Removes a Direction from this collision
   * @param direction to be removed
   */
  @Override
  public void remove(Direction direction) {
    directionsList.remove(direction);
  }

  /**
   * Checks whether this object contains a given Direction
   * @param direction which may or may not be contained in this object
   * @return true if this contains the Direction
   */
  @Override
  public boolean contains(Direction direction) {
    return directionsList.contains(direction);
  }

  /**
   * Getter for the raw list of Direction enums
   * @return directionsList
   */
  @Override
  public List<Direction> getRawList() {
    return directionsList;
  }

  /**
   * Clears the list of Directions, making it empty
   */
  @Override
  public void clear() {
    this.directionsList = new ArrayList<>();
  }

  /**
   * Takes in a CollisionDirections object, and checks if at least one Direction in it
   * is also contained in this CollisionDirections object
   * @param otherDirections the other CollisionDirections object
   * @return true if the two CollisionDirections objects share at least one Direction
   */
  @Override
  public boolean oneIsContainedIn(ICollisionHandler otherDirections) {
    for (Direction direction : otherDirections.getRawList()) {
      if (this.contains(direction)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks whether this contains TOP or BOTTOM (but not both!)
   * @return true if this contains TOP or BOTTOM - but not both.
   */
  @Override
  public boolean containsVerticalCollision() {
    return this.contains(Direction.TOP) ^ this.contains(Direction.BOTTOM); // ^ is XOR
  }

  /**
   * Checks whether this contains LEFT or RIGHT (but not both!)
   * @return true if this contains LEFT or RIGHT - but not both.
   */
  @Override
  public boolean containsHorizontalCollision() {
    return this.contains(Direction.RIGHT) ^ this.contains(Direction.LEFT);
  }

  /**
   * Checks whether this contains no Directions
   * @return true if the size of the Direction list is zero
   */
  @Override
  public boolean isEmpty() {
    return directionsList.size() == 0;
  }

  /**
   * Checks if there is any collision.
   * @return true if the directions list contains at least one Direction, and it does not contain
   * None. NONE is not used in our current implementation of the project.
   */
  @Override
  public boolean doesCollide() {
    return directionsList.size() > 0 && !directionsList.contains(Direction.NONE);
  }

  /**
   * Creates a new CollisionDirections object with the opposite collision as this object.
   * @return new object with the Directions reversed (i.e. if this one contains TOP, the new
   * object would contain BOTTOM)
   */
  @Override
  public ICollisionHandler getOpposites() {
    List<Direction> oppositeList = new ArrayList<>();
    for (Direction direction : directionsList) {
      oppositeList.add(direction.getOpposite());
    }
    return new CollisionDirections(oppositeList);
  }
}
