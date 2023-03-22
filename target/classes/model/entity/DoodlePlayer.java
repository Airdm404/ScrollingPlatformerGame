package model.entity;

import model.collision.Direction;
import api.model.collision.ICollisionHandler;
import api.model.entity.IEntity;

/**
 * A class for a type of Player that in addition to responding to manual controls
 * also automatically jumps when colliding at the bottom with an entity
 * @author Ryan Krakower
 */
public class DoodlePlayer extends Player {

  private static final double DOODLE_JUMP_SPEED = -0.4;
  private static final double POWER_JUMP_SPEED = -0.6;
  private final double MOVEMENT_SPEED = 0.2;

  /**
   * Constructs a DoodlePlayer given an x-coordinate and a y-coordinate
   * @param x The x-coordinate to construct this DoodlePlayer's Hitbox with
   * @param y The y-coordinate to construct this DoodlePlayer's Hitbox with
   */
  public DoodlePlayer(double x, double y) {
    super(x, y);
    this.setGrounded(false);
  }
  /**
   * A handler for when two entities are colliding and this how this entity
   * should have its movement altered if they are
   * Ideally, this should be called during IEntity#checkCollision
   * to have easy access to an ICollisionHandler list
   * @param otherEntity The entity to check if this entity is colliding with
   * @param directions The collision handler containing a list of Directions
   */
  @Override
  public void processCurrentCollision(IEntity otherEntity, ICollisionHandler directions) {
    if (directions.contains(Direction.BOTTOM) && this.getYVel() > 0) {
      this.getHitBox().setYBottom(otherEntity.getHitBox().getYTop());
    }
  }
  /**
   * Updates the velocity of the player given key inputs
   * @param leftKey A boolean reprsenting whether or not the "move left" key was pressed
   * @param rightKey A boolean representing whether or not the "move right" key was pressed
   * @param jumpKey A boolean representing whether or not the "jump" key was pressed
   */
  @Override
  public void updateVelocity(boolean leftKey, boolean rightKey, boolean jumpKey) {
    if (rightKey && this.getXVel() <= 0) {this.setXVel(MOVEMENT_SPEED);}
    if (leftKey && this.getXVel() >= 0) {this.setXVel(-MOVEMENT_SPEED);}
    if (rightKey == leftKey) {this.setXVel(0);}
    this.applyGravity();
  }

  /**
   * Updates the position of this player - for a Doodle Player,
   * it makes it automatically jump when it is colliding at the bottom
   * And then translates its hitbox accordingly
   */
  @Override
  public void updatePosition() {
    if (this.getCurrentCollision().contains(Direction.BOTTOM) && this.getYVel() > 0) {
      this.setYVel(DOODLE_JUMP_SPEED);
    }
    this.translateHitBox();
  }
  /**
   * Applies a direct jump speed modifier to the DoodlePlayer
   * @param modifier An usused Modifier instance
   */
  @Override
  public void applyModifier(Modifier modifier){
    this.setYVel(POWER_JUMP_SPEED);
  }
}
