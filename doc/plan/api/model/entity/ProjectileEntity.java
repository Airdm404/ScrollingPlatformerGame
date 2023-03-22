public interface ProjectileEntity extends MobileEntity{

    /**
     * Accessor for the entity that fired this projectile
     * @return The Entity that was assigned to be the shooter of this projectile
     */
    Entity getShooter();
}