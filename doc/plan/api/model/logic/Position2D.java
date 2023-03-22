package entity;

public interface Position2D{

    /**
     * Accessor for the x-coordinate of this position
     * @return A float x-coordinate
     */
    float getXPosition();

    /**
     * Accessor for the y-coordinate of this position
     * @return A float y-coordinate
     */
    float getYPosition();

    /**
     * Setter for the x-coordinate of this position
     * @param xPosition The float x-coordinate
     */
    void setXPosition(float xPosition);

    /**
     * Setter for the y-coordinate of this position
     * @param yPosition The float y-coordinate
     */
    void setYPosition(float yPosition);

}