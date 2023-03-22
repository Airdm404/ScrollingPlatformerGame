public interface BlockEntity extends Entity{

    /**
     * Compares the positions and hitboxes of this block and another entity to see if there is a collision
     * If colliding, an interaction of some type will occur between the entity and this block
     * In addition to determining which direction the block was collided from to determine specific outcomes for each face
     * @param collidingEntity The entity this entity is potentially colliding with
     * @return Whether or not the two entities involved have collided
     */
    @Override
    void checkCollision(Entity collidingEntity);

    /**
     * Directions assigned to each face of the block
     */
    public enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}