package model.entity;

import api.model.collision.ICollisionHandler;
import api.model.entity.IEntity;

/**
 * A class for a type of Player that only responds to a jump command
 * and is immobilized when it collides with any entities
 * @author Ryan Krakower
 */
public class FlappyPlayer extends Player {

  private static final double FLAPPY_JUMP_SPEED = -0.25;

  /**
   * Constructs a FlappyPlayer given an x-coordinate and a y-coordinate
   * @param x The x-coordinate to construct this FlappyPlayer's Hitbox with
   * @param y The y-coordinate to construct this FlappyPlayer's Hitbox with
   */
  public FlappyPlayer(double x, double y) {
    super(x, y);
  }

  private boolean heldDownJumpKey = false;
  private boolean immobilized = false;
  /**
   * A handler for when two entities are colliding and this how this entity
   * should have its movement altered if they are
   * Ideally, this should be called during IEntity#checkCollision
   * to have easy access to an ICollisionHandler list
   * For a FlappyPlayer, it immobilizes it once it collides with something
   * @param otherEntity The entity to check if this entity is colliding with
   * @param directions The collision handler containing a list of Directions
   */
  @Override
  public void processCurrentCollision(IEntity otherEntity, ICollisionHandler directions) {
    if (directions.doesCollide()) {
      this.immobilized = true;
    }
  }
  /**
   * Updates the velocity of the player given key inputs
   * @param leftKey An unused boolean
   * @param rightKey An unused boolean
   * @param jumpKey A boolean representing whether or not the "jump" key was pressed
   */
  @Override
  public void updateVelocity(boolean leftKey, boolean rightKey, boolean jumpKey) {
    if (immobilized) {return;}
    if (jumpKey && !heldDownJumpKey)  {
      this.setYVel(FLAPPY_JUMP_SPEED);
      heldDownJumpKey = true;
    } else if (!jumpKey){
      heldDownJumpKey = false;
    }
  }
  /**
   * Updates the position of this player - For a FlappyPlayer,
   * this means applying gravity to it and then translating the hitbox
   * using its current velocity
   */
  @Override
  public void updatePosition(){
    this.applyGravity();
    this.translateHitBox();
  }
}
