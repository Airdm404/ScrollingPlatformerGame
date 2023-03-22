package model.entity;

import model.HitBox;
import api.model.entity.IEntity;

/**
 * A class for entities that are blocks - solid entities that cannot
 * be moved or passed through that make up the base of the platform levels
 * @author Mike Garay
 */
public class Block implements IEntity {
    private HitBox hitBox;
    private final String type = this.getClass().getSimpleName();


    /**
     * Constructs a Block given an x-coordinate and a y-coordinate
     * @param x The x-coordinate to construct this Block's Hitbox with
     * @param y The y-coordinate to construct this Block's Hitbox with
     */
    public Block(double x, double y){
        this.hitBox = new HitBox(x, y);
    }

    /**
     * Obtains the stored Hitbox instance of the entity
     * @apiNote  : This should be amended to return an IHitbox instance, not a Hitbox instance
     * @return The Hitbox instance stored in the entity instance
     */
    @Override
    public HitBox getHitBox() {
        return hitBox;
    }

    /**
     * A generic handler for checking any collisions between this entity and another entity
     * Being a handler, it should contain any logic for when there are collisions present
     * @param entity The other entity to check for any possible collisions with
     */
    @Override
    public void checkCollision(IEntity entity) {

    }

    /**
     * Sets the x velocity for this entity
     * @param xVel The x velocity to set for this entity
     */
    @Override
    public void setXVel(double xVel) {

    }

    /**
     * Sets the y velocity for this entity
     * @param yVel The y velocity to set for this entity
     */
    @Override
    public void setYVel(double yVel) {

    }

    /**
     * Obtains the x velocity for this entity
     * @return The current x velocity of this entity
     */
    @Override
    public double getYVel() {
        return 0;
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
     * Obtains the y velocity for this entity
     * @return The current y velocity of this entity
     */
    @Override
    public double getXVel() {
        return 0;
    }
}
