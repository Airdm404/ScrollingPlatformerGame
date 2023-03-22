package model.entity;

import model.HitBox;
import api.model.entity.IEmpowering;
import api.model.entity.IEntity;

/**
 * @author Mike Garay
 */
public class PowerUp implements IEntity, IEmpowering {
    private HitBox hitBox;
    private final String type = this.getClass().getSimpleName();
    private Modifier modifier;
    private boolean hasAppliedModifier = false;

    /**
     * Constructs a PowerUp given an x-coordinate and a y-coordinate
     * @param x The x-coordinate to construct this PowerUp's Hitbox with
     * @param y The y-coordinate to construct this PowerUp's Hitbox with
     */
    public PowerUp(double x, double y){
        this.hitBox = new HitBox(x, y);
    }

    /**
     * Obtains the stored Hitbox instance of the entity
     * @apiNote : This should be amended to return an IHitbox instance, not a Hitbox instance
     * @return The Hitbox instance stored in the entity instance
     */
    public HitBox getHitBox() {
        return hitBox;
    }

    /**
     *
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
     * Obtains the y velocity for this entity
     * @return The current y velocity of this entity
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
     * Obtains the x velocity for this entity
     * @return The current x velocity of this entity
     */
    @Override
    public double getXVel() {
        return 0;
    }

    /**
     * Obtains the modifier stored in an instance of the implementing class
     * @return The modifier instance stored in this instance
     */
    @Override
    public Modifier getModifier() {
        return this.modifier;
    }

    /**
     * Sets the modifier to store in an instance of the implementing class
     * @param modifier The modifier instance to store
     */
    @Override
    public void setModifier(Modifier modifier) {
        this.modifier = modifier;
    }

    /**
     * Determines if the instance of the implementing class has applied its modifier
     * This is used as a basis for any post-modifier-application logic
     * @return Whether or not the instance of the implementing class has applied its modifier
     */
    @Override
    public boolean hasAppliedModifier() {
        return this.hasAppliedModifier;
    }

    /**
     * Sets the stored boolean in the instance of the implementing class that
     * determines if it has applied the stored modifier to another entity
     * @param hasAppliedModifier The value to set the stored boolean to
     */
    @Override
    public void setHasAppliedModifier(boolean hasAppliedModifier) {
        this.hasAppliedModifier = hasAppliedModifier;
    }
}
