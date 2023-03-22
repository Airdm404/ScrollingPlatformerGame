package api.model.entity;

import model.HitBox;
import model.collision.Direction;
import api.model.collision.ICollisionHandler;

import java.util.List;
import java.util.Optional;

/**
 * An interface for entities that when collided with
 * should spawn a new entity instance
 * @author Mike Garay
 */
public interface ISpawner {

    /**
     * Attempts to retrieve an entity from the stored spawn list
     * and afterwards, remove it from the stored spawn list
     * @return an Optional of an IEntity
     */
    default Optional<IEntity> attemptSpawnEntity(){
        if(!this.getSpawnList().isEmpty()){
            IEntity entity = this.getSpawnList().get(0);
            this.getSpawnList().remove(0);
            return Optional.of(entity);
        }
        return Optional.empty();
    }

    /**
     * Given an ICollisionHandler instance,
     * if it contains any of the collision directions required to spawn an entity,
     * it initializes an x-position and y-position to attempt to construct a new entity instance with
     * and then calls the attemptCreateAndAddSpawn that takes in an x-position and a y-position
     * @param direction The ICollisionHandler instance to check the stored Directions for
     */
    default void attemptCreateAndAddSpawn(ICollisionHandler direction){
        ICollisionHandler directionOpposite = direction.getOpposites();
        if(this.getCollisionsRequiredForSpawn().oneIsContainedIn(directionOpposite)){
            double xPos = this.getHitBox().getXLeft();
            double yPos = this.getHitBox().getYTop();
            int height = this.getHitBox().getYSize();
            int width = this.getHitBox().getXSize();
            if (directionOpposite.contains(Direction.TOP)) {
                yPos += height;
            }
            if (directionOpposite.contains(Direction.BOTTOM)) {
                yPos -= height;
            }
            if (directionOpposite.contains(Direction.LEFT)) {
                xPos += width;
            }
            if (directionOpposite.contains(Direction.RIGHT)) {
                xPos -= width;
            }
            this.attemptCreateAndAddSpawn(xPos, yPos);
        }
    }

    /**
     * If the max spawn count is greater than 0,
     * the ISpawner instance creates a new instance of an entity
     * using createSpawn, adds it to the stored spawn list,
     * and then decrements the max spawn count by 1
     * @param xPos The x-position to construct the entity with
     * @param yPos The y-position to construct the entity with
     */
    default void attemptCreateAndAddSpawn(double xPos, double yPos){
        if(this.getMaxSpawnCount() > 0){
            IEntity entity = this.createSpawn(xPos, yPos);
            this.getSpawnList().add(entity);
            this.setMaxSpawnCount(this.getMaxSpawnCount() - 1);
        }
    }

    /**
     * Obtains the list of stored entities in the ISpawner instance
     * @return The list of stored entities in the ISpawner instance
     */
    List<IEntity> getSpawnList();

    /**
     * Accessor for the ICollisionHandler instance
     * that stores the list of collision directions required
     * for this ISpawner to spawn an entity
     * @return An ICollisionHandler instance containing collision directions required for spawning
     */
    ICollisionHandler getCollisionsRequiredForSpawn();

    /**
     * Creates an entity - the class should be determined inside the implementation of this method
     * @param x The x-coordinate to spawn the entity at
     * @param y The y-coordinate to spawn the entity at
     * @return a new instance of an entity
     */
    IEntity createSpawn(double x, double y);

    /**
     * Obtains the max spawn count for this ISpawner instance
     * @return The stored max spawn count for this ISpawner instance
     */
    int getMaxSpawnCount();

    /**
     * Sets the current max spawn current for this ISpawner instance
     * If this becomes 0 or less, the ISpawner can no longer create new entity instances
     * to store in its spawn list
     * @param maxSpawnCount The max spawn count to set the stored count for this ISpawner instance to
     */
    void setMaxSpawnCount(int maxSpawnCount);

    /**
     * Obtains the Hitbox instance stored in the entity
     * @implNote : This should be amended to return an IHitBox, not a Hitbox
     * @return A Hitbox stored in the entity
     */
    HitBox getHitBox();
}
