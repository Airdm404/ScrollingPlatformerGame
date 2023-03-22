package api.model.entity;

import model.collision.CollisionDirections;
import model.collision.Direction;
import model.entity.Teams;
import api.model.collision.ICollisionHandler;
/**
 * An interface used for entities that can take and receive damage
 * @author Mike Garay
 */
public interface IDamageable {

    /**
     * Accesses the current health of this damageable
     * @return the current health of this damageable
     */
    double getHealth();

    /**
     * Sets the current health of this damageable
     * @param health The health to set the health of this damageable to
     */
    void setHealth(double health);

    /**
     * Accesses the current collision damage of this damageable
     * @return The current collision damage of this damageable
     */
    double getCollisionDamage();

    /**
     * Sets the current collision damage of this damageable
     * @param collisionDamage The collision damage to set the collision damage of this damageable to
     */
    void setCollisionDamage(double collisionDamage);

    /**
     * Returns a list of CollisionDirections that this damageable can apply damage from
     * @return The list of CollisionDirections that this damageable can apply damage from
     */
    ICollisionHandler getAppliesDamageDirections();

    /**
     * Returns a list of CollisionDirections that this damageable can receive damage from
     * @return The list of CollisionDirections that this damageable can receive damage from
     */
    ICollisionHandler getReceivesDamageDirections();

    /**
     * Determines whether or not this entity is dead - by default, it checks if its health is less than or equal to 0
     * @return Whether or not this entity is dead
     */
    default boolean isDead(){
        return this.getHealth() <= 0;
    }

    /**
     * Attempts to apply damage to the other IDamageable passed in if damage can be applied to the direction
     * and the other IDamageable can receive damage from that direction
     * @param damageable The other IDamageable entity to potentially apply damage to
     * @param currentCollidingDirections The current directions through which this entity is colliding with the other entity
     */
    default void attemptApplyDamage(IDamageable damageable, CollisionDirections currentCollidingDirections){
        boolean isOnSameTeam = this.isOnSameTeam(damageable);
        boolean isEmpty =  currentCollidingDirections.isEmpty();
        for(Direction direction : currentCollidingDirections.directionsList){
            boolean canApplyDamage = this.canApplyDamageToDirection(direction);
            boolean damageableCanReceiveDamage = damageable.canReceiveDamageFromDirection(direction.getOpposite());

            if(canApplyDamage && damageableCanReceiveDamage && !isOnSameTeam && !isEmpty){
                double currentHealth = damageable.getHealth();
                damageable.setHealth(currentHealth - this.getCollisionDamage());
            }
        }
    }

    /**
     * Determines whether or not this entity can apply damage through the direction passed in
     * @param direction The direction to check if damage can be applied to
     * @return Whether or not this entity can apply damage through the direction passed in
     */
    default boolean canApplyDamageToDirection(Direction direction){
        return this.getAppliesDamageDirections().contains(direction);
    }

    /**
     * Determines whether or not this entity can receive damage from the direction passed in
     * @param direction The direction to check if damage can be received from
     * @return Whether or not this entity can recieve damage from the direction passed in
     */
    default boolean canReceiveDamageFromDirection(Direction direction){
        return this.getReceivesDamageDirections().contains(direction);
    }

    /**
     * Determines if this entity and the other IDamageable entity are on the same team
     * @param damageable The other IDamageable entity to check the team of
     * @return Whether or not this entity and the IDamageable entity passed in are on the same team
     */
    default boolean isOnSameTeam(IDamageable damageable){
        return this.getTeam() == damageable.getTeam();
    }

    /**
     * Obtains the Team this entity belongs to
     * @return The Team this entity belongs to
     */
    Teams getTeam();
}
