package model.entity;

import model.collision.Direction;

/**
 * A class for a type of player that responds to manual controls
 * for movement and jumping and whose movement is affected
 * by the Modifiers currently applied to it
 * @author Ryan Krakower
 */
public class MarioPlayer extends Player {

  private static final double MARIO_JUMP_SPEED = -0.35;
  private static final double MAX_MOVE_SPEED = 0.15;
  private static final double MOVE_ACCELERATION = 0.015;
  //GRACE_BEFORE_JUMP is there to prevent a graphical glitch where Mario gets
  //very jittery when he tries to jump directly into a block
  private boolean GRACE_BEFORE_JUMP = true;

  /**
   * Constructs a MarioPlayer given an x-coordinate and a y-coordinate
   * @param x The x-coordinate to construct this MarioPlayer's Hitbox with
   * @param y The y-coordinate to construct this MarioPlayer's Hitbox with
   */
  public MarioPlayer(double x, double y) {
    super(x, y);
  }

  /**
   * Updates the velocity of the player given key inputs
   * For a MarioPlayer, this is where movement-based modifiers are applied
   * @param leftKey A boolean reprsenting whether or not the "move left" key was pressed
   * @param rightKey A boolean representing whether or not the "move right" key was pressed
   * @param jumpKey A boolean representing whether or not the "jump" key was pressed
   */
  @Override
  public void updateVelocity(boolean leftKey, boolean rightKey, boolean jumpKey) {
    double movementSpeedModifierValue = this.getMovementSpeedModifierValue();
    double jumpSpeedModifierValue = this.getJumpSpeedModifierValue();

    if (jumpKey && this.getGrounded()) {
      if (GRACE_BEFORE_JUMP) {
        this.setYVel(-(MOVE_ACCELERATION * jumpSpeedModifierValue));
        GRACE_BEFORE_JUMP = false;
      }
      else {
        this.setYVel(MARIO_JUMP_SPEED * jumpSpeedModifierValue);
        this.setGrounded(false);
        GRACE_BEFORE_JUMP = true;
      }
    }
    else {
      GRACE_BEFORE_JUMP = true;
    }
    if (rightKey && this.getXVel() < (MAX_MOVE_SPEED * movementSpeedModifierValue)) {
      accelerateRight();
    }
    if (leftKey && this.getXVel() > -(MAX_MOVE_SPEED * movementSpeedModifierValue)) {
      accelerateLeft();
    }
    if (rightKey == leftKey && this.getXVel() < 0) {
      accelerateRight();
    }
    if (rightKey == leftKey && this.getXVel() > 0) {
      accelerateLeft();
    }

    if (Math.abs(this.getXVel()) < MOVE_ACCELERATION) {
      this.setXVel(0);
    }
  }

  // sets the x velocity to make the player move right using MOVE_ACCELERATION and the player's movement speed modifier value
  private void accelerateRight() {
    this.setXVel(this.getXVel() + (MOVE_ACCELERATION * this.getMovementSpeedModifierValue()));
  }
  // sets the x velocity to make the player move left using MOVE_ACCELERATION and the player's movement speed modifier value
  private void accelerateLeft() {
    this.setXVel(this.getXVel() - (MOVE_ACCELERATION * this.getMovementSpeedModifierValue()));
  }

  /**
   * Updates the position of this player - For a MarioPlayer,
   * it applies gravity to it then translates its hitbox
   * using its current velocity
   */
  @Override
  public void updatePosition() {
    if (!this.getCurrentCollision().contains(Direction.BOTTOM)) {
      this.applyGravity();
    }
    this.translateHitBox();
  }

}
