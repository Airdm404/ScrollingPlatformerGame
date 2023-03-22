package model.entity;

import model.HitBox;
import model.collision.CollisionDirections;
import model.collision.Direction;
import api.model.collision.ICollisionHandler;
import api.model.entity.IDamageable;
import api.model.entity.IEntity;
import api.model.entity.IMovable;

import java.util.Arrays;

/**
 * A class for entities that are enemies - they target any entities that are players,
 * and attempt to colldie with them to damage/kill them.
 * @author Mike Garay and Ryan Krakower
 */
public class Enemy implements IEntity, IMovable, IDamageable {

    private static final double ENEMY_MOVEMENT_SPEED = 0.1;
    private static final double MIN_DISTANCE_TO_PLAYER = 0.06;
    public static final double GRAVITY_FACTOR = 0.015f;
    private final HitBox hitBox;
    private final String type = this.getClass().getSimpleName();
    private double xVel = 0;
    private double yVel = 0;
    private boolean grounded = true;
    private double health = 0;
    private double damage = 0;
    private ICollisionHandler currentCollision = new CollisionDirections();

    /**
     * Constructs a Enemy given an x-coordinate and a y-coordinate
     * @param x The x-coordinate to construct this Enemy's Hitbox with
     * @param y The y-coordinate to construct this Enemy's Hitbox with
     */
    public Enemy(double x, double y){
        this.hitBox = new HitBox(x, y);
        this.setHealth(100);
        this.setCollisionDamage(100);
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
     * Updates the velocity of this enemy using a Player entity passed in
     * This makes the enemy move towards the player in order to attack it
     * @param player The player to move towards
     */
    public void updateVelocity(Player player) {

        if(player.getHitBox().getXLeft() < this.getHitBox().getXLeft() - MIN_DISTANCE_TO_PLAYER){
            this.setXVel(ENEMY_MOVEMENT_SPEED * -1);
        }
        else if(player.getHitBox().getXLeft() > this.getHitBox().getXLeft() + MIN_DISTANCE_TO_PLAYER){
            this.setXVel(ENEMY_MOVEMENT_SPEED);
        }
        else{
            this.setXVel(0);
        }
    }

    /**
     * A handler for checking any collisions between this entity and another entity
     * If the other entity is an instance of IDamageable, this entity attempts to apply damage to it
     * @param entity The other entity to check for any possible collisions with
     */
    public void checkCollision(IEntity entity) {
        CollisionDirections collision = hitBox.getCollisionDirections(entity.getHitBox());
        currentCollision.add(collision);

        this.processCurrentCollision(entity, collision);
        if(entity instanceof IDamageable){
            this.attemptApplyDamage((IDamageable) entity,collision);
        }
    }

    /**
     * Updates the position of this enemy
     * by translating its hitbox using its current x and y velocity
     * This should be done every frame/step of the level
     */
    @Override
    public void updatePosition() {
        if (!this.getCurrentCollision().contains(Direction.BOTTOM)) {
            this.applyGravity();
        }
        translateHitbox();
    }

    // translates the hitbox of the enemy and clears its current colision
    protected void translateHitbox() {
        hitBox.translateX(xVel);
        hitBox.translateY(yVel);
        this.currentCollision.clear();
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
     * Obtains the y velocity for this entity
     * @return The current y velocity of this entity
     */
    @Override
    public double getXVel() {
        return xVel;
    }

    /**
     * Obtains the x velocity for this entity
     * @return The current x velocity of this entity
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
     * Determines whether or not this entity is dead - by default, it checks if its health is less than or equal to 0
     * @return Whether or not this entity is dead
     */
    @Override
    public boolean isDead() {
        return this.health <= 0;
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
        return new CollisionDirections(Arrays.asList(Direction.BOTTOM, Direction.LEFT, Direction.RIGHT));
    }

    /**
     * Returns a list of CollisionDirections that this damageable can receive damage from
     * @return The list of CollisionDirections that this damageable can receive damage from
     */
    @Override
    public ICollisionHandler getReceivesDamageDirections() {
        return new CollisionDirections(Arrays.asList(Direction.TOP, Direction.BOTTOM, Direction.LEFT, Direction.RIGHT));
    }

    /**
     * Obtains the Team this entity belongs to
     * @return The Team this entity belongs to
     */
    @Override
    public Teams getTeam() {
        return Teams.ENEMY;
    }

    // applies gravity to the enemy using the gravity factor
    protected void applyGravity() {
        this.setYVel(this.getYVel() + GRAVITY_FACTOR);
    }

    // obtains the current collision handler instance of this enemy
    protected ICollisionHandler getCurrentCollision() {
        return this.currentCollision;
    }
}
