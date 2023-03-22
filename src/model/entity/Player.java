package model.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import model.HitBox;
import model.collision.CollisionDirections;
import model.collision.Direction;
import api.model.collision.ICollisionHandler;
import api.model.entity.*;
/**
 * A class for entities that are players
 * @author Mike Garay and Ryan Krakower
 */
public abstract class Player implements IEntity, IMovable, IDamageable, IPlayer {

  //private final String type = this.getClass().getSimpleName();
  private final String type = "Player";
  public static final int GRACE_PERIOD = 1;
  public static final double GRAVITY_FACTOR = 0.015f;
  private double xVel = 0;
  private double yVel = 0;
  protected HitBox hitBox;
  private boolean grounded = true;
  private double health = 0;
  private double damage = 0;
  private final Map<Modifier.ModifierType, Modifier> modifiers = new HashMap<>();
  private ICollisionHandler currentCollision = new CollisionDirections();

  /**
   * Constructs a Player given an x-coordinate and a y-coordinate
   * @param x The x-coordinate to construct this Player's Hitbox with
   * @param y The y-coordinate to construct this Player's Hitbox with
   */
  public Player(double x, double y){
    this.hitBox = new HitBox(x, y);
    this.setHealth(100);
    this.setCollisionDamage(100);
  }

  /**
   * Updates the velocity of the player given key inputs
   * @param leftKey A boolean reprsenting whether or not the "move left" key was pressed
   * @param rightKey A boolean representing whether or not the "move right" key was pressed
   * @param jumpKey A boolean representing whether or not the "jump" key was pressed
   */
  public abstract void updateVelocity(boolean leftKey, boolean rightKey, boolean jumpKey);

  /**
   * An accessor for the movement speed modifier value of an IPlayer
   * @implNote : It should return a default value if the modifier is not present
   * @return The movement speed modifier value of an IPlayer
   */
  @Override
  public double getMovementSpeedModifierValue(){
    Modifier movementSpeedModifier = this.getModifiers().get(Modifier.ModifierType.MOVEMENT_SPEED);
    return movementSpeedModifier != null ? movementSpeedModifier.getValue() : 1;
  }

  /**
   * An accessor for the movement speed modifier value of an IPlayer
   * @implNote : It should return a default value if the modifier is not present
   * @return The jump speed modifier value of an IPlayer
   */
  @Override
  public double getJumpSpeedModifierValue(){
    Modifier jumpSpeedModifier = this.getModifiers().get(Modifier.ModifierType.JUMP_SPEED);
    return jumpSpeedModifier != null ? jumpSpeedModifier.getValue() : 1;
  }

  /**
   * An accessor for the anti-gravity modifier value of an IPlayer
   * @implNote : It should return a default value if the modifier is not present
   * @return The anti-gravity modifier value of an IPlayer
   */
  @Override
  public double getAntiGravityModifierValue(){
    Modifier gravityModifier = this.getModifiers().get(Modifier.ModifierType.ANTI_GRAVITY);
    return gravityModifier != null ? gravityModifier.getValue() : 1;
  }

  /**
   * Updates the position of this player - Should be used for game type specific
   * position updates determines by the extending class
   */
  public abstract void updatePosition();

  /**
   * A  handler for checking any collisions between this entity and another entity
   *
   * If the other entity is an instance of IDamageable, this player attempts to apply damage to it
   *
   * If the other entity is an instance of IEmpowering and the collision directions are not empty,
   * this player attempts to retrieve a modifier from it and then has it call
   * IEmpowering#sethasAppliedModifier to true if the player successfully obtains the modifier
   *
   * If the other entity is an instance of ISpawner and the collision directions are not empty,
   *
   * ISpawner#attemptCreateAndAddSpawn is called to attempt to make it spawn an entity
   * If the entity is an instance of IWinnable and the collision directions are not empty,
   * IWinnable#setHasWon is called to set it to true that the player has "won" it
   *
   * @param entity The other entity to check for any possible collisions with
   */
  public void checkCollision(IEntity entity) {
    CollisionDirections collision = hitBox.getCollisionDirections(entity.getHitBox());
    currentCollision.add(collision);
    this.processCurrentCollision(entity, collision);
    if (entity instanceof IDamageable) {
      this.attemptApplyDamage((IDamageable) entity, collision);
    }
    if (entity instanceof IEmpowering && !collision.isEmpty()) {
      IEmpowering empowering = (IEmpowering) entity;
      if (!empowering.hasAppliedModifier()) {
        if(empowering.getModifier() != null){
          this.applyModifier(empowering.getModifier());
        }
        empowering.setHasAppliedModifier(true);
      }
    }
    if (entity instanceof ISpawner && !collision.isEmpty()) {
      ISpawner spawner = (ISpawner) entity;
      spawner.attemptCreateAndAddSpawn(collision);
    }
    if (entity instanceof IWinnable && !collision.isEmpty()) {
      IWinnable goal = (IWinnable) entity;
      goal.setHasWon(true);
    }
  }

  /**
   * Obtains the stored Hitbox instance of the entity
   * @apiNote : This should be amended to return an IHitbox instance, not a Hitbox instance
   * @return The Hitbox instance stored in the entity instance
   */
  @Override
  public HitBox getHitBox() {
    return hitBox;
  }

  /**
   * Sets the x velocity for this entity
   * @param xVel The x velocity to set for this entity
   */
  @Override
  public void setXVel(double xVel) {
    this.xVel = xVel;
  }

  /**
   * Sets the y velocity for this entity
   * @param yVel The y velocity to set for this entity
   */
  @Override
  public void setYVel(double yVel) {
    this.yVel = yVel;
  }

  /**
   * Obtains the x velocity for this entity
   * @return The current x velocity of this entity
   */
  @Override
  public double getXVel() {
    return xVel;
  }

  /**
   * Obtains the y velocity for this entity
   * @return The current y velocity of this entity
   */
  @Override
  public double getYVel() {
    return yVel;
  }

  /**
   * Obtains a String representing the stored type of this entity
   * This should usually obtain the name of the implementing class
   * and can be used to avoid instanceof/downcasting if not absolutely required
   * @return A String representing the type of this entity
   */
  @Override
  public String getType() {
    return type;
  }

  /**
   * Obtains whether or not this entity is "grounded", aka
   * it should not be considered airborne
   * @return Whether or not this entity is grounded
   */
  @Override
  public boolean getGrounded() {
    return grounded;
  }

  /**
   * Sets whether or not this entity is "grounded", aka
   * whether or not is should be considered airborne
   * @param grounded The boolean to set the grounded value to
   */
  @Override
  public void setGrounded(boolean grounded) {
    this.grounded = grounded;
  }

  /**
   * Translates the hitbox of this player using its current velocity
   * then clears its current collision handler
   */
  public void translateHitBox() {
    this.getHitBox().translateX(this.getXVel());
    this.getHitBox().translateY(this.getYVel());
    this.currentCollision.clear();
  }

  // applies gravity to the player, taking into account the anti-gravity modifier it may have
  protected void applyGravity() {
    double antiGravityValue = this.getAntiGravityModifierValue();
    double adjustedAntiGravityValue = 1 - ((antiGravityValue - 1));
    double yVelWithGravity = this.getYVel() + (GRAVITY_FACTOR * adjustedAntiGravityValue);
    this.setYVel(yVelWithGravity);
  }

  /**
   * Accesses the current health of this damageable
   * @return the current health of this damageable
   */
  @Override
  public double getHealth() {
    return this.health;
  }

  /**
   * Sets the current health of this damageable
   * @param health The health to set the health of this damageable to
   */
  @Override
  public void setHealth(double health) {
    this.health = health;
  }

  /**
   * Accesses the current collision damage of this damageable
   * @return The current collision damage of this damageable
   */
  @Override
  public double getCollisionDamage() {
    return this.damage;
  }

  /**
   * Sets the current collision damage of this damageable
   * @param collisionDamage The collision damage to set the collision damage of this damageable to
   */
  @Override
  public void setCollisionDamage(double collisionDamage) {
    this.damage = collisionDamage;
  }

  /**
   * Returns a list of CollisionDirections that this damageable can apply damage from
   * @return The list of CollisionDirections that this damageable can apply damage from
   */
  @Override
  public ICollisionHandler getAppliesDamageDirections() {
    return new CollisionDirections(Collections.singletonList(Direction.BOTTOM));
  }

  /**
   * Returns a list of CollisionDirections that this damageable can receive damage from
   * @return The list of CollisionDirections that this damageable can receive damage from
   */
  @Override
  public ICollisionHandler getReceivesDamageDirections() {
    return new CollisionDirections(Arrays.asList(Direction.TOP, Direction.BOTTOM, Direction.LEFT, Direction.RIGHT));
  }

  // obtains the current collision handler instance of this player
  protected ICollisionHandler getCurrentCollision() {
    return this.currentCollision;
  }

  /**
   * An accessor for the ModifierType to Modifier map of an IPlayer
   * @return The ModifierType to Modifier map of an IPlayer
   */
  @Override
  public Map<Modifier.ModifierType, Modifier> getModifiers() {
    return this.modifiers;
  }

  /**
   * Obtains the Team this entity belongs to
   * @return The Team this entity belongs to
   */
  @Override
  public Teams getTeam() {
    return Teams.PLAYER;
  }

  /**
   * Determines whether or not this entity is dead - by default, it checks if its health is less than or equal to 0
   * @return Whether or not this entity is dead
   */
  @Override
  public boolean isDead() {
    return this.health <= 0;
  }
}
