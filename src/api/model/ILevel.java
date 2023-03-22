package api.model;

import model.configuration.ILevelLoader;
import api.model.entity.IEntity;
import model.entity.Player;
import api.model.scroll.Scroller;

import java.util.List;
import java.util.Optional;

/**
 * The class that "plays" a game.
 * It contains a step function which repeatedly runs through the necessary logic to
 * animate and control a game. It contains all of the entities that are in the level,
 * and manipulates them as the game progresses.
 *
 * GameModel contains an instance of this class and is responsible for
 * calling the step function.
 *
 * GameView calls getAllEntitiesInLevel to take in the entity list. This entity
 * list contains the information that the view will display to the end user.
 *
 * @author Ryan Krakower
 * @author Mike Garay
 */
public interface ILevel {

    /**
     * The step function, which runs continuously.
     *
     * 1. It removes any entities that should be removed
     * 2. It spawns any entities that should be spawned
     * 3. It updates "modifiers," which are effects of power ups on the player
     * 4. It updates velocities, calculating any change in velocity that the entities should have
     * 5. It checks for collisions. If a collision is detected, the entities are alerted that they
     *    have collided. Different entities react to collisions differently - depending on the entity
     *    in question and the other entity it collides with, the entity may change velocities,
     *    change positions, do nothing, or be removed from the game entirely.
     * 6. It updates the position of every entity according to its velocity.
     * 7. It checks whether the game is won or lost, and if so, ends the game appropriately.
     * 8. It checks whether the player fell out of the level, and if so, resets the game.
     * 9. It scrolls the game, adjusting the viewing window, if necessary.
     *
     * If the game is paused, the above steps are not run, freezing the game in place.
     */
    void step();

    /**
     * Removes entities as needed.
     * If player is dead, player is removed and level is lost.
     * If IMovable (enemy) is dead, it is removed.
     * If powerUp is used, it is removed.
     */
    void removeEntitiesAsNeeded();

    /**
     * Attempt to spawn new entities from Spawners in the level.
     */
    void spawnEntitiesAsNeeded();

    /**
     * Update the player according to modifiers which come from power ups
     */
    void updateModifiers();

    /**
     * Update velocities of entities.
     * In our current game, this means making the player move according to which keys are currently pressed,
     * and them updating each enemy's velocity according to where the player is.
     */
    void updateVelocities();

    /**
     * Check collisions of entities.
     * The game only needs to check collisions for movable entities, but for each of those entities,
     * it has to iterate through every entity in the level. If a collision is detected, the
     * entity will react appropriately.
     */
    void checkCollisions();

    /**
     * Updates the position of player and enemies according to their velocities.
     */
    void updatePositions();

    /**
     * Moves the entities in the level based on data on this level and the player.
     * Depending on the settings of the scroller, entities may move continuously in one direction,
     * move if the player is at a certain coordinate, or not move at all.
     */
    void scroll();

    /**
     * Moves all entities in the list by <xChange, yChange>
     * @param xChange the amount to scroll the entity in the x direction
     * @param yChange the amount to scroll the entity in the y direction
     */
    void translateAllEntities(double xChange, double yChange);

    /**
     * Moves the Entity
     * @param entity the Entity to be scrolled
     * @param xChange the amount to scroll the entity in the x direction
     * @param yChange the amount to scroll the entity in the y direction
     */
    void translateEntity(IEntity entity, double xChange, double yChange);

    /**
     * Sets the scroller of the level equal to the Scroller passed in
     * @param configScroller the Scroller that will serve as this level's new Scroller
     */
    void setScroller(Scroller configScroller);

    /**
     * Getter for levelLength
     * @return levelLength
     */
    int getLevelLength();

    /**
     * Getter for levelWidth
     * @return levelWidth
     */
    int getLevelWidth();

    /**
     * Initializes a level and creates new lists of entities. If the level is reset,
     * it initializes the level all over again.
     */
    void setOrResetLevel(ILevelLoader ILevelLoader);

    /**
     * Adds a new entity to the level. There are several lists of entities, and it is placed
     * in the correct list(s) depending on what properties the entity has.
     * @param entity to be added to the level.
     */
    void addEntity(IEntity entity);

    /**
     * Removes an entity from the level by removing it from all lists of entities.
     * @param entity to be removed.
     */
    void removeEntity(IEntity entity);

    /**
     * Gets the entity at a certain set of coordinates.
     * @param xCoordinate of entity
     * @param yCoordinate of entity
     * @return entity at the coordinates. If there is no entity there, it returns null.
     */
    Optional<IEntity> getEntityAt(int xCoordinate, int yCoordinate);

    /**
     * Makes sure that the player is within bounds (i.e. xleft > 0 and xright <
     * scroller.NUM_BLOCKS or the num blocks wide and tall on display) and if
     * not, places that player within bounds
     *
     * @param player the player whose bounds will be checked
     */
    void keepPlayerInBounds(Player player);

    /**
     * Checks whether the game is won or lost. Games can have varying win conditions,
     * but the lose condition is if the size of the playerList is 0 (if the player is
     * removed from the game, through death, falling or otherwise)
     */
    void checkWinLoseConditions();

    /**
     * Checks to see if the player has lost the level (i.e. fell through
     * bottom of screen) and if so resets the level
     */
    void checkFellOutOfLevel();

    /**
     * Handles the situation where the player has fallen off of the screen
     */
    void reinitialize();

    /**
     * Setter for isLevelWon
     * @param isLevelWon boolean that is true if level is won
     */
    void setLevelWon(boolean isLevelWon);

    /**
     * Setter for isLevelLost
     * @param isLevelLost boolean that is true if level is lost
     */
    void setLevelLost(boolean isLevelLost);

    /**
     * Getter for isLevelWon
     * @return true if the level is won
     */
    boolean isLevelWon();

    /**
     * Getter for keyPressFunctions
     * @return the KeyPressFunctions object, which holds booleans that remember what key functions
     * are currently activated
     */
    IKeyPressFunctions getKeyPressFunctions();

    /**
     * Gets a copy of the list containing every player in the level.
     * This method is called by GameView, and used to gain information
     * on every entity that will be displayed to the end user.
     *
     * Changing the contents of this list will NOT
     * change the Entity information stored in Level.
     *
     * @return playerList
     */
    List<IEntity> getCopyOfEntityList();

    /**
     * Gets the list containing every entity in the level.
     * This is used internally in Level for the purpose of
     * iterating through all entities.
     *
     * Changing the contents of this list WILL
     * change the Entity information stored in Level.
     *
     * * @return entityList
     */
    List<IEntity> getAllEntities();

    /**
     * Gets the list containing every player in the level
     * @return playerList
     */
    List<Player> getPlayerList();

    /**
     * Reveals the score of the player within the level
     * @return score
     */
    int getScore();

    /**
     * Reveals if the level has lost
     * @return levelLost
     */
    boolean isLevelLost();

    /**
     * Reveals if we're saving the game
     * @return isSaving
     */
    boolean isSaving();

    /**
     * Sets a variable to show if we're saving the game
     * @param save the indicator for if we're saving
     */
    void setIsSaving(boolean save);
}
