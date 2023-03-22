public interface MobileEntity extends Entity{

    /**
     * Accessor for the motion vector of this mobile entity
     * @return The Vector2D representing the current motion vector of this mobile entity
     */
    Vector2D getMotion();

    /**
     * Setter for the motion vector of this mobile entity
     * @param vector2D The Vector2D that will become this mobile entity's motion vector
     */
    void setMotion(Vector2D vector2D);
}