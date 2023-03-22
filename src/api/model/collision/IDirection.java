package api.model.collision;

import model.collision.Direction;

/**
 * An enum that is used to indicate from which direction a collision is.
 * If I am detecting collisions for the player, and some other entity is above the player
 * and collides, the correct enum would be TOP.
 * If I am detecting collisions for the player, and some other entity is to the right of the player
 * and collides, the correct enum would be RIGHT.
 *
 * @author Ryan Krakower
 */
public interface IDirection {

    /**
     * Gets the opposite direction of the current direction
     * @return the opposite cardinal direction, or NONE if the current direction is NONE
     */
    Direction getOpposite();
}
