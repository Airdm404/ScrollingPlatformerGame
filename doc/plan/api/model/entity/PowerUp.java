public interface PowerUp extends MobileEntity{

    ModifierType getModifierType();

    double getModifierAmount();

    // numerical example: PowerUp(ModifierType.SPEED, 2.0D) applies a speed modifier of 2.0
    // boolean example: PowerUp(ModifierType.INVICIBILITY, 1.0D) applies invicibility

    public enum ModifierType{
        SPEED,
        LIVES,
        HEALTH,
        DAMAGE,
        SIZE,
        PROJECTILE,
        INVULNERABILITY
    }
}