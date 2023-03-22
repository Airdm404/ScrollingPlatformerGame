package model.configuration;

import model.entity.*;
import api.model.entity.IEntity;
import api.model.entity.IMovable;
import api.model.entity.IWinnable;

import java.io.File;
import java.util.List;

/**
 * An interface containing functionality for loading a level
 * @author Mike Garay
 */
public interface ILevelLoader {
    /**
     * Accessor for the length of the level loaded in
     * @return The length of the level loaded in
     */
    int getLevelLength();

    /**
     * Accessor for the width of the level loaded in
     * @return The width of the level loaded in
     */
    int getLevelWidth();

    /**
     * Accessor for a copy of the list of player entities,
     * to prevent direct modification of the original list
     * @return A defensive copy of the list of player entities
     */
    List<Player> getCopyOfPlayerList();

    /**
     * Accessor for a copy of the list of IMovable entities,
     * to prevent direct modification of the original list
     * @return A defensive copy of the list of IMovable entities
     */
    List<IMovable> getCopyOfMovableEntityList();

    /**
     * Accessor for a copy of the list of IWinnable entities,
     * to prevent direct modification of the original list
     * @return A defensive copy of the list of IWinnable entities
     */
    List<IWinnable> getCopyOfWinnableList();

    /**
     * Accessor for a copy of the list of enemy entities,
     * to prevent direct modification of the original list
     * @return A defensive copy of the list of enemy entities
     */
    List<Enemy> getCopyOfEnemyList();

    /**
     * Accessor for a copy of the list of block entities,
     * to prevent direct modification of the original list
     * @return A defensive copy of the list of block entities
     */
    List<Block> getCopyOfBlockList();

    /**
     * Accessor for a copy of the list of power-up entities,
     * to prevent direct modification of the original list
     * @return A defensive copy of the list of power-up entities
     */
    List<PowerUp> getCopyOfPowerUpList();

    /**
     * Accessor for a copy of the list of all entities,
     * to prevent direct modification of the original list
     * @return A defensive copy of the list of all entities
     */
    List<IEntity> getCopyOfEntityList();

    /**
     * Initializes all of the entity lists during initial level construction
     * It should create entity instances using an IEntityFactory,
     * then call addEntityToLists to add the instance to the lists they belong in
     * @param levelFileIn The level file to initialize the entity lists from
     * @throws InvalidFileException if the level file could not be found
     */
    void initializeEntityLists(File levelFileIn) throws InvalidFileException;

    /**
     * Adds an entity instance to the main IEntity list,
     * as well as any other specific entity lists the entity
     * should be a part of. Uses downcasting.
     * @param entity
     */
    void addEntityToLists(IEntity entity);

    /**
     *
     * Accessor for a copy of the list of entities,
     * to prevent direct modification of the original list
     * @param originalCopy The IEntity list to copy
     * @return A defensive copy of the list of player entities
     */
    List<IEntity> defensivelyCopyList(List<IEntity> originalCopy);

    /**
     * Reinitializes the level loader (i.e. resets all lists to have their contents when the
     * LevelLoader was first instantiated
     */
    void reinitialize();

    /**
     * Handles any exceptions thrown during construction of a level loader
     * @param levelFileIn The level file
     * @throws InvalidFileException A custom exception that communicates file read failures during level creation
     */
    void handleConstructionExceptions(File levelFileIn) throws InvalidFileException;
}
