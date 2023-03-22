package api.model.entity;

import model.HitBox;
import model.collision.Direction;
import api.model.collision.ICollisionHandler;

/**
 * An interface used for entities that can move
 * @author Mike Garay and Ryan Krakower
 */
public interface IMovable extends IEntity {


  /**
   * Updates the position of this entity
   * by translating its hitbox using its current x and y velocity
   * This should be done every frame/step of the level
   */
  default void updatePosition(){
    this.getHitBox().translateX(this.getXVel());
    this.getHitBox().translateY(this.getYVel());
  }

  /**
   * A generic handler for checking any collisions between this entity and another entity
   * Being a handler, it should contain any logic for when there are collisions present
   * @param entity The other entity to check for any possible collisions with
   */
  void checkCollision(IEntity entity);

  /**
   * Obtains whether or not this entity is "grounded", aka
   * it should not be considered airborne
   * @return Whether or not this entity is grounded
   */
  boolean getGrounded();

  /**
   * Sets whether or not this entity is "grounded", aka
   * whether or not is should be considered airborne
   * @param grounded The boolean to set the grounded value to
   */
  void setGrounded(boolean grounded);

  /**
   * Determines whether or not this movable entity is "dead".
   * If true, it should be used by the level to remove this entity
   * if it is not an instance of IDamageable.
   * @return Whether or not this movable entity is "dead"
   */
  boolean isDead();

  /**
   * Sets the x velocity for this entity
   * @param xVel The x velocity to set for this entity
   */
  void setXVel(double xVel);

  /**
   * Obtains the x velocity for this entity
   * @return The current x velocity of this entity
   */
  double getXVel();

  /**
   * Obtains the stored Hitbox instance of the entity
   * @implNote : This should be amended to return an IHitbox instance, not a Hitbox instance
   * @return The Hitbox instance stored in the entity instance
   */
  HitBox getHitBox();

  /**
   * Sets the y velocity for this entity
   * @param yVel The y velocity to set for this entity
   */
  void setYVel(double yVel);

  /**
   * Obtains the y velocity for this entity
   * @return The current y velocity of this entity
   */
  double getYVel();

  /**
   * The minimum difference between x or y coordinates to be considered a collision
   */
  double MIN_COLLISION = 0.01;

  /**
   *
   * @return
   */
  String getType();

  /**
   * A handler for when two entities are colliding and this how this entity
   * should have its movement altered if they are
   * Ideally, this should be called during IEntity#checkCollision
   * to have easy access to an ICollisionHandler list
   * @param entity The entity to check if this entity is colliding with
   * @param collision The collision handler containing a list of Directions
   */
  default void processCurrentCollision(IEntity entity, ICollisionHandler collision){

    if (collision.contains(Direction.BOTTOM) && !collision.containsHorizontalCollision()) {
      //System.out.print("Bottom");
      this.setGrounded(true);

      this.getHitBox().setYBottom(entity.getHitBox().getYTop() + MIN_COLLISION);
      if (this.getYVel() > 0) {
        this.setYVel(0);
      }
    }
    if (collision.contains(Direction.TOP) && !collision.containsHorizontalCollision()){
      //System.out.print("Top");
      this.getHitBox().setYTop(entity.getHitBox().getYBottom() - MIN_COLLISION);
      if (this.getYVel() < 0) {
        this.setYVel(0);
      }
    }
    if (collision.contains(Direction.RIGHT) && !collision.containsVerticalCollision()) {
      //System.out.print("Right");
      this.getHitBox().setXRight(entity.getHitBox().getXLeft() + MIN_COLLISION);
      if (this.getXVel() > 0) {
        this.setXVel(0);
      }
    }
    if (collision.contains(Direction.LEFT) && !collision.containsVerticalCollision()) {
      //System.out.print("Left");
      this.getHitBox().setXLeft(entity.getHitBox().getXRight() - MIN_COLLISION);
      if (this.getXVel() < 0) {
        this.setXVel(0);
      }
    }

    //this allows enemies to be killed from above even if the player is slightly offset from center
    if (collision.contains(Direction.TOP) && this.getYVel() >= 0) {
      collision.remove(Direction.RIGHT);
      collision.remove(Direction.LEFT);
    }
  }

}
