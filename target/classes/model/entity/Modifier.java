package model.entity;

/**
 * A class for modifiers that can be applied to entities to modify their
 * properties
 * @author Mike Garay
 */
public class Modifier {
    private ModifierType modifierType;
    private double value;
    private double duration;

    /**
     * Constructs a Modifer given a ModifierType, the value of the modifier and the duration of the modifier
     * @param modifierType The ModifierType to construct this Modifier with
     * @param value The value of the modifier i.e., the amplifier for the entity's base value
     * @param duration The time this modifier can last
     */
    public Modifier(ModifierType modifierType, double value, double duration){
        this.modifierType = modifierType;
        this.value = value;
        this.duration = duration;
    }

    /**
     * Updates the duration of this Modifier by decrementing it by 1
     */
    public void updateDuration(){
        this.duration--;
    }

    /**
     * Retrieves the ModifierType of this Modifier
     * @return The ModifierType stored in this Modifier
     */
    public ModifierType getModifierType() {
        return this.modifierType;
    }

    /**
     * Retrieves the value of this Modifier
     * @return The value of this Modifier
     */
    public double getValue() {
        return this.value;
    }

    /**
     * Sets the Modifier's value to the given value
     * @param valueIn The value to set this Modifier's value to
     */
    public void setValue(double valueIn) {
        this.value = valueIn;
    }

    /**
     * Retrieves the duration of this Modifier
     * @return The duration of this Modifier
     */
    public double getDuration() {
        return this.duration;
    }

    /**
     * An enum containing constants representing different types of modifiers
     * By default, they are movement based
     */
    public enum ModifierType{
        MOVEMENT_SPEED,
        JUMP_SPEED,
        //COLLISION_DAMAGE,
        //HEALTH,
        ANTI_GRAVITY
    }
}
