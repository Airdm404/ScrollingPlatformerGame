package model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.collision.CollisionDirections;
import model.collision.Direction;
import api.model.collision.ICollisionHandler;
import api.model.entity.IEntity;
import api.model.entity.ISpawner;

/**
 * @author Mike Garay
 */
public class PowerUpBlock extends Block implements ISpawner {
    private final List<IEntity> spawnList = new ArrayList<>();
    private int maxSpawnCount = 1;

    /**
     * Constructs a PowerUpBlock given an x-coordinate and a y-coordinate
     * @param x The x-coordinate to construct this PowerUpBlock's Hitbox with
     * @param y The y-coordinate to construct this PowerUpBlock's Hitbox with
     */
    public PowerUpBlock(double x, double y) {
        super(x, y);
    }

    /**
     * Obtains the list of stored entities in the ISpawner instance
     * @return The list of stored entities in the ISpawner instance
     */
    @Override
    public List<IEntity> getSpawnList() {
        return this.spawnList;
    }

    /**
     * Accessor for the ICollisionHandler instance
     * that stores the list of collision directions required
     * for this ISpawner to spawn an entity
     * In this case, the only direction that can make a PowerUp spawn is Direction.BOTTOM
     * @return An ICollisionHandler instance containing collision directions required for spawning
     */
    @Override
    public ICollisionHandler getCollisionsRequiredForSpawn() {
        return new CollisionDirections(Collections.singletonList(Direction.BOTTOM));
    }

    /**
     * Creates an entity - in this case, a PowerUp
     * @param x The x-coordinate to spawn the entity at
     * @param y The y-coordinate to spawn the entity at
     * @return a new instance of an entity
     */
    @Override
    public IEntity createSpawn(double x, double y) {
        return new PowerUp(x, y);
    }

    /**
     * Obtains the max spawn count for this ISpawner instance
     * @return The stored max spawn count for this ISpawner instance
     */
    @Override
    public int getMaxSpawnCount() {
        return this.maxSpawnCount;
    }

    /**
     * Sets the current max spawn current for this ISpawner instance
     * If this becomes 0 or less, the ISpawner can no longer create new entity instances
     * to store in its spawn list
     * @param maxSpawnCount The max spawn count to set the stored count for this ISpawner instance to
     */
    @Override
    public void setMaxSpawnCount(int maxSpawnCount) {
        this.maxSpawnCount = maxSpawnCount;
    }
}
