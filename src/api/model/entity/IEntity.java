package api.model.entity;

import model.HitBox;
/**
 * An interface used as the basis for all entities in a level
 * @author Mike Garay and Ryan Krakower
 */
public interface IEntity {

    /**
     * Obtains the stored Hitbox instance of the entity
     * @apiNote : This should be amended to return an IHitbox instance, not a Hitbox instance
     * @return The Hitbox instance stored in the entity instance
     */
    HitBox getHitBox();

    /**
     * A generic handler for checking any collisions between this entity and another entity
     * Being a handler, it should contain any logic for when there are collisions present
     * @param entity The other entity to check for any possible collisions with
     */
    void checkCollision(IEntity entity);

    /**
     * Sets the x velocity for this entity
     * @param xVel The x velocity to set for this entity
     */
    void setXVel(double xVel);

    /**
     * Sets the y velocity for this entity
     * @param yVel The y velocity to set for this entity
     */
    void setYVel(double yVel);

    /**
     * Obtains the x velocity for this entity
     * @return The current x velocity of this entity
     */
    double getXVel();

    /**
     * Obtains the y velocity for this entity
     * @return The current y velocity of this entity
     */
    double getYVel();

    /**
     * Obtains a String representing the stored type of this entity
     * This should usually obtain the name of the implementing class
     * and can be used to avoid instanceof/downcasting if not absolutely required
     * @return A String representing the type of this entity
     */
    String getType();



}
