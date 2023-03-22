<<<<<<< HEAD
import model.logic.*;
public interface Entity{
=======
package entity;

public interface Entity {
>>>>>>> c87663a14a34314ffc506b9983947883264fac1e

    /**
     * Accessor for the type of this entity to be used in view for assigning a shape and texture
     * @return The Type this entity was assigned
     */
    Type getType();

    /**
     * Accessor for the width of this entity, to be used for calculating the hitbox
     * @return The integer width of this entity
     */
    int getWidth();


    /**
     * Accessor for the height of this entity, to be used for calculating the hitbox
     * @return The integer height of this entity
     */
    int getHeight();

    /**
     * Accessor for the position of this entity in 2D space
     * @return The entity.Position2D representing this entity's current position
     */
    Position2D getPosition();

    /**
     * Setter for the position of this entity in 2D space
     * @param position2D The entity.Position2D that will become the entity's current position
     */
    void setPosition(Position2D position2D);

    /**
     * Accessor for the string id of this entity
     * @return A String representing the id of this entity
     */
    String getID();

    /**
     * Compares the positions and hitboxes of this entity and another to see if there is a collision
     * If colliding, an interaction of some type will occur between the two entities and the method will return true
     * @param collidingEntity The entity this entity is potentially colliding with
     * @return Whether or not the two entities involved have collided
     */
    boolean checkCollision(Entity collidingEntity);

    public enum Type{
        PLAYER,
        ENEMY,
        BARRIER_BLOCK,
        BREAKABLE_BLOCK,
        DAMAGE_BLOCK,
        POWER_UP_BLOCK,
        POWER_UP,
        GOAL
    }
}