public interface PlayerEntity extends LivingEntity implements IRangedAttacker{

    /**
     * Accessor for the lives of this player
     * @return The integer  amount of lives of this player
     */
    int getLives();

    /**
     * Setter for the lives of this player
     * @return The integer lives to become this player's amount of lives
     */
    void setLives(int lives);

    void applyPowerUpEffect(PowerUp powerUp);


    /**
     * Accessor for the speed modifier of this palyer
     * @return The double speed modifier of this player
     */
    double getSpeedModifier();

    void setSpeedModifier(double speedModifier);

    int getSizeModifier();

    void setSizeModifier(int sizeModifier);

    boolean isInvincible();

    int getDamageModifier();

    void setDamageModifier(int damageModifier);

    void setInvincible(boolean isInvincible);

    boolean hasRangedAttack();

    boolean setHasRangedAttack(boolean hasRangedAttack);

    // implemented from IRangedAttacker
    void useRangedAttack();
}