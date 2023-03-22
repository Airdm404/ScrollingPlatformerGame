package model.collision;

import api.model.collision.IDirection;

/**
 * An enum that is used to indicate from which direction a collision is.
 * If I am detecting collisions for the player, and some other entity is above the player
 * and collides, the correct enum would be TOP.
 * If I am detecting collisions for the player, and some other entity is to the right of the player
 * and collides, the correct enum would be RIGHT.
 *
 * @author Ryan Krakower
 */
public enum Direction implements IDirection {
  RIGHT,
  LEFT,
  TOP,
  BOTTOM,
  NONE;

  /**
   * Gets the opposite direction of the current direction
   * @return the opposite cardinal direction, or NONE if the current direction is NONE
   */
  @Override
  public Direction getOpposite(){
    switch (this){
      case TOP -> {
        return BOTTOM;
      }
      case LEFT -> {
        return RIGHT;
      }
      case BOTTOM -> {
        return TOP;
      }
      case RIGHT -> {
        return LEFT;
      }
      default -> {
        return NONE;
      }
    }
  }
}
