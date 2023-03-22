package api.model.entity;

import model.entity.Modifier;

import java.util.Map;

/**
 * This interface contains player-specific modifier manipulation methods
 * @author Mike Garay
 */
public interface IPlayer {

    /**
     * An accessor for the movement speed modifier value of an IPlayer
     * @implNote : It should return a default value if the modifier is not present
     * @return The movement speed modifier value of an IPlayer
     */
    double getMovementSpeedModifierValue();

    /**
     * An accessor for the movement speed modifier value of an IPlayer
     * @implNote : It should return a default value if the modifier is not present
     * @return The jump speed modifier value of an IPlayer
     */
    double getJumpSpeedModifierValue();

    /**
     * An accessor for the anti-gravity modifier value of an IPlayer
     * @implNote : It should return a default value if the modifier is not present
     * @return The anti-gravity modifier value of an IPlayer
     */
    double getAntiGravityModifierValue();

    /**
     * An accessor for the ModifierType to Modifier map of an IPlayer
     * @return The ModifierType to Modifier map of an IPlayer
     */
    Map<Modifier.ModifierType, Modifier> getModifiers();

    /**
     * Updates the duration values for each of the IPlayer's modifiers
     * If the modifier's duration is less than or equal to 0,
     * the modifier is removed from the map
     *
     */
    default void updateModifiers(){
        for(Modifier.ModifierType modifierType : this.getModifiers().keySet()){
            Modifier modifier = this.getModifiers().get(modifierType);
            if(modifier != null){
                modifier.updateDuration();
                if(modifier.getDuration() <= 0){
                    this.removeModifier(modifierType);
                }
            }
        }
    }

    /**
     * Applies a modifier to the IPlayer
     * @param modifier The modifier to apply to the IPlayer
     */
    default void applyModifier(Modifier modifier){
        this.getModifiers().put(modifier.getModifierType(), modifier);
    }

    /**
     * Removes a modifier from the IPlayer giving a ModifierType
     * @param modifierType The ModifierType to remove a stored instance of in the IPlayer
     */
    default void removeModifier(Modifier.ModifierType modifierType){
        this.getModifiers().remove(modifierType);
    }
}
