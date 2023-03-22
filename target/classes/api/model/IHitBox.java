package api.model;

import model.HitBox;
import model.collision.CollisionDirections;

/**
 * A HitBox class for the entities.
 * Essentially a non-JavaFX version of a Rectangle.
 * It is also capable of detecting collisions with other HitBoxes,
 * and returning the direction(s) of the collision.
 *
 * This is used by all entities for collision checking and to store position.
 *
 * @author Ryan Krakower
 */
public interface IHitBox {

    /**
     * Getter for XLeft
     * @return xLeft
     */
    double getXLeft();

    /**
     * Setter for XSize
     * @return xSize
     */
    int getXSize();

    /**
     * Getter for YSize
     * @return ySize
     */
    int getYSize();

    /**
     * Getter for XRight (note that xRight is not stored internally)
     * @return xRight
     */
    double getXRight();

    /**
     * Getter for yTop
     * @return yTop
     */
    double getYTop();

    /**
     * Setter for yTop
     * @param yTop y top coordinate
     */
    void setYTop(double yTop);

    /**
     * Setter for yBottom (note that it actually sets yTop)
     * @param yBottom y bottom coordinate
     */
    void setYBottom(double yBottom);

    /**
     * Setter for xRight (note that it actually sets xLeft)
     * @param xRight x right coordinate
     */
    void setXRight(double xRight);

    /**
     * Setter for xLeft
     * @param xLeft x left coordinate
     */
    void setXLeft(double xLeft);

    /**
     * Getter for yBottom (note that yBottom is not stored internally)
     * @return yBottom
     */
    double getYBottom();

    /**
     * Translates the HitBox in the x direction by shifting its x coordinate by a given amount
     * @param deltaX change in x (positive is right, negative is left)
     */
    void translateX(double deltaX);

    /**
     * Translates the HitBox in the y direction by shifting its x coordinate by a given amount
     * @param deltaY change in y (positive is down, negative is up)
     */
    void translateY(double deltaY);

    /**
     * This method calculates a collision with another HitBox by determining whether
     * the two HitBoxes' coordinates overlap.
     * If there is a collision, it determines the direction(s) of the collision by figuring out
     * where the second HitBox is located in relation to the first.
     * It will not detect a collision if the two HitBoxes are just touching (i.e. if xRight of the first
     * is 5, and xLeft of the second is also 5). It will only detect a collision if they overlap by at least
     * the constant CORNER_GLITCH_AVOIDANCE_OFFSET, which should be a small number.
     *
     * @param otherBox the HitBox of the other Entity.
     * @return A CollisionDirections object. This is essentially a glorified list of Directions enums,
     * containing BOTTOM, TOP, LEFT, and/or RIGHT, depending on how the second HitBox collides with the first.
     * If there is no collision, an empty CollisionDirections object is returned.
     */
    CollisionDirections getCollisionDirections(HitBox otherBox);

    /**
     * Helper method to determine if one double is in between two others
     * @param value to be evaluated
     * @param min must be less than value
     * @param max must be greater than value
     * @return true if above conditions are met
     */
    default boolean between(double value, double min, double max) {
        if ((value > min) && (value < max)) {
            return true;
        }
        return false;
    }
}
