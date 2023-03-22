package api.model.entity;

/**
 * An interface for entities that when collided with
 * should allow the player to win the level
 * @author Mike Garay
 */
public interface IWinnable {

    /**
     * Determines whether or not this entity has been "won",
     * aka an entity (normally a player) has collided with it
     * @return Whether or not this entity has been won
     */
    boolean getHasWon();

    /**
     * Sets the stored boolean for whether or not this entity has been "won",
     * aka an entity (normally a player) has collided with it
     * @param hasWon The boolean to set the stored hasWon value to
     */
    void setHasWon(boolean hasWon);
}
