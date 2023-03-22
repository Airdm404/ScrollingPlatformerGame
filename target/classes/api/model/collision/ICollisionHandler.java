package api.model.collision;

import model.collision.Direction;

import java.util.List;

/**
 * This class is a glorified version of a list of Directions enums.
 * It exists to make handling collisions with multiple directions at once
 * easier, and to simplify comparing collisions with multiple directions.
 *
 * @author Ryan Krakower
 */
public interface ICollisionHandler {

    /**
     * Adds a new Direction to the collision
     * @param direction new direction to be added
     */
    void add(Direction direction);

    /**
     * Takes in another CollisionDirections object, and adds all of its Directions to the current
     * collision of this object
     * @param directions other CollisionDirections object
     */
    void add(ICollisionHandler directions);

    /**
     * Removes a Direction from this collision
     * @param direction to be removed
     */
    void remove(Direction direction);

    /**
     * Checks whether this object contains a given Direction
     * @param direction which may or may not be contained in this object
     * @return true if this contains the Direction
     */
    boolean contains(Direction direction);

    /**
     * Getter for the raw list of Direction enums
     * @return directionsList
     */
    List<Direction> getRawList();

    /**
     * Clears the list of Directions, making it empty
     */
    void clear();

    /**
     * Takes in a CollisionDirections object, and checks if at least one Direction in it
     * is also contained in this CollisionDirections object
     * @param otherDirections the other CollisionDirections object
     * @return true if the two CollisionDirections objects share at least one Direction
     */
    boolean oneIsContainedIn(ICollisionHandler otherDirections);

    /**
     * Checks whether this contains TOP or BOTTOM (but not both!)
     * @return true if this contains TOP or BOTTOM - but not both.
     */
    boolean containsVerticalCollision();

    /**
     * Checks whether this contains LEFT or RIGHT (but not both!)
     * @return true if this contains LEFT or RIGHT - but not both.
     */
    boolean containsHorizontalCollision();

    /**
     * Checks whether this contains no Directions
     * @return true if the size of the Direction list is zero
     */
    boolean isEmpty();

    /**
     * Checks if there is any collision.
     * @return true if the directions list contains at least one Direction, and it does not contain
     * None. NONE is not used in our current implementation of the project.
     */
    boolean doesCollide();

    /**
     * Creates a new CollisionDirections object with the opposite collision as this object.
     * @return new object with the Directions reversed (i.e. if this one contains TOP, the new
     * object would contain BOTTOM)
     */
    ICollisionHandler getOpposites();
}
