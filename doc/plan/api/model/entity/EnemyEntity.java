public interface EnemyEntity extends LivingEntity implements IRangedAttacker{

    int getDetectionRange();

    boolean hasDetectedPlayer();

    void moveTowardsTarget(PlayerEntity playerEntity);

    // implemented from IRangedAttacker
    void useRangedAttack();
}