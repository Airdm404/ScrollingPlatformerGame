public interface LivingEntity implements MobileEntity {

    /**
     * Accessor for the health of this living entity
     * @return The current integer health of this living entity
     */
    int getHealth();

    /**
     * Setter for the health of this living entity
     * @param health The integer health to become the current health of this living entity
     */
    void setHealth(int health);

}