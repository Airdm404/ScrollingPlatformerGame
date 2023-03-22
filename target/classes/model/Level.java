package model;

import model.configuration.ILevelLoader;
import model.entity.*;
import model.scroll.AutoScroller;
import api.model.scroll.Scroller;
import api.model.IKeyPressFunctions;
import api.model.ILevel;
import api.model.entity.IEntity;
import api.model.entity.IMovable;
import api.model.entity.ISpawner;
import api.model.entity.IWinnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The class that "plays" a game.
 *
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
public class Level implements ILevel {

  public static final int FRAMES_PER_SECOND = 60;
  public static final int MODIFIER_DURATION = 10;
  public static final double MODIFIER_VALUE = 1.5;
  private static final int ENEMY_SCORE = 100;
  public api.model.IKeyPressFunctions IKeyPressFunctions = new KeyPressFunctions();

  private Scroller scroller;
  private ILevelLoader loader;


  // NOTE: There are multiple lists of entities in Level, based on their
  // class type. This is a violation of the Polymorphism principle, but we did
  // it anyway because we thought it would cut down the code's runtime
  // and avoid having to loop through every single entity for every
  // single function. For example, when checking collisions, it loops through
  // every entity in movableEntityList, and for each of those entities, loops through
  // every single entity in entityList (which contains all entities). If it had to loop
  // through every entity, for every entity, the runtime of this would be O(n^2), instead
  // of O(mn), with m being much smaller than n. This could have decreased performance and
  // made the game laggy.
  //
  // In hindsight, if we had made every entity have a simple return statement for each type of
  // method it did not need, this could have allowed us to abide by the Polymorphism rule,
  // and also help with the Liskov Substitution Principle because we would not need to know what
  // subclass a given object is before calling a method. I'm not sure how much
  // this would have increased runtime.

  private List<IEntity> entityList;
  private List<Player> playerList;
  private List<Enemy> enemyList;
  private List<IMovable> movableEntityList = new ArrayList<>();
  private List<PowerUp> powerUpList;
  private List<Block> blockList;
  private List<IWinnable> winnableList;

  private int levelLength;
  private int levelWidth;
  private int score;
  private boolean levelLost;
  private boolean levelWon;
  private boolean isSaving;

  /**
   * The constructor for a Level
   * @param ILevelLoader the class that loads in a level
   */
  public Level(ILevelLoader ILevelLoader) {
    this.scroller = new AutoScroller(0,0, false);
    this.setOrResetLevel(ILevelLoader);
  }

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
  @Override
  public void step() {
    if (!IKeyPressFunctions.isPaused()) {
      this.removeEntitiesAsNeeded();
      this.spawnEntitiesAsNeeded();
      this.updateModifiers();
      this.updateVelocities();
      this.checkCollisions();
      this.updatePositions();
      this.checkWinLoseConditions();
      this.checkFellOutOfLevel();
      this.scroll();
    }
  }

  /**
   * Removes entities as needed.
   * If player is dead, player is removed and level is lost.
   * If IMovable (enemy) is dead, it is removed.
   * If powerUp is used, it is removed.
   */
  @Override
  public void removeEntitiesAsNeeded() {
    List<IEntity> entitiesToRemove = new ArrayList<>();

    for (Player player : playerList) {
      if (player.isDead()) {
        entitiesToRemove.add(player);
        this.setLevelLost(true);
      }
    }

    for (IMovable movable : this.movableEntityList) {
      if (movable.isDead()) {
        entitiesToRemove.add(movable);
      }
    }

    for(PowerUp powerUp : this.powerUpList){
      if(powerUp.hasAppliedModifier()){
        entitiesToRemove.add(powerUp);
      }
    }

    for(IEntity entity : entitiesToRemove){
      this.removeEntity(entity);
    }
  }

  /**
   * Attempt to spawn new entities from Spawners in the level.
   */
  @Override
  public void spawnEntitiesAsNeeded() {
    for(Block block : this.blockList){
      if(block instanceof ISpawner){
        ISpawner spawner = (ISpawner)block;
        Optional<IEntity> optionalIEntity = spawner.attemptSpawnEntity();
        optionalIEntity.ifPresent(this::addEntity);
      }
    }
  }

  /**
   * Update the player according to modifiers which come from power ups
   */
  @Override
  public void updateModifiers() {
    for (Player player : playerList) {
      player.updateModifiers();
    }
  }

  /**
   * Update velocities of entities.
   * In our current game, this means making the player move according to which keys are currently pressed,
   * and them updating each enemy's velocity according to where the player is.
   */
  @Override
  public void updateVelocities() {
    for (Player player : playerList) {
      player.updateVelocity(IKeyPressFunctions.isPlayerMovingLeft(), IKeyPressFunctions.isPlayerMovingRight(), IKeyPressFunctions.isPlayerJumping());
    }
    for (Enemy enemy : enemyList) {
      if(!playerList.isEmpty()){
        enemy.updateVelocity(playerList.get(0));
      }
    }
  }

  /**
   * Check collisions of entities.
   * The game only needs to check collisions for movable entities, but for each of those entities,
   * it has to iterate through every entity in the level. If a collision is detected, the
   * entity will react appropriately.
   */
  @Override
  public void checkCollisions(){
    for (IMovable movable : this.movableEntityList) {
      for(IEntity otherEntity : this.entityList){
        if(!movable.equals(otherEntity)){
          movable.checkCollision(otherEntity);
        }
      }
    }
  }

  /**
   * Updates the position of player and enemies according to their velocities.
   */
  @Override
  public void updatePositions(){
    for (Player player : this.playerList) {
      player.updatePosition();
      keepPlayerInBounds(player);
    }
    for (Enemy enemy : this.enemyList) {
      enemy.updatePosition();
    }
  }


  /**
   * Moves the entities in the level based on data on this level and the player.
   * Depending on the settings of the scroller, entities may move continuously in one direction,
   * move if the player is at a certain coordinate, or not move at all.
   */
  @Override
  public void scroll() {
    if(!playerList.isEmpty()){
      scroller.scroll(this, playerList.get(0));
      score += scroller.getScoreFromScroll();
    }
  }

  /**
   * Moves all entities in the list by <xChange, yChange>
   * @param xChange the amount to scroll the entity in the x direction
   * @param yChange the amount to scroll the entity in the y direction
   */
  @Override
  public void translateAllEntities(double xChange, double yChange) {
    entityList.forEach(entity -> translateEntity(entity, xChange, yChange));
  }

  /**
   * Moves the Entity
   * @param entity the Entity to be scrolled
   * @param xChange the amount to scroll the entity in the x direction
   * @param yChange the amount to scroll the entity in the y direction
   */
  @Override
  public void translateEntity(IEntity entity, double xChange, double yChange) {
    HitBox hitBox = entity.getHitBox();
    hitBox.translateX(xChange);
    hitBox.translateY(yChange);
  }

  /**
   * Sets the scroller of the level equal to the Scroller passed in
   * @param configScroller the Scroller that will serve as this level's new Scroller
   */
  @Override
  public void setScroller(Scroller configScroller) {
    scroller = configScroller;
  }

  /**
   * Getter for levelLength
   * @return levelLength
   */
  @Override
  public int getLevelLength() {
    return this.levelLength;
  }

  /**
   * Getter for levelWidth
   * @return levelWidth
   */
  @Override
  public int getLevelWidth() {
    return this.levelWidth;
  }

  /**
   * Initializes a level and creates new lists of entities. If the level is reset,
   * it initializes the level all over again.
   */
  @Override
  public void setOrResetLevel(ILevelLoader ILevelLoader){
    loader = ILevelLoader;
    score = 0;

    this.playerList = ILevelLoader.getCopyOfPlayerList();
    this.enemyList = ILevelLoader.getCopyOfEnemyList();
    this.movableEntityList = ILevelLoader.getCopyOfMovableEntityList();
    this.blockList = ILevelLoader.getCopyOfBlockList();
    this.powerUpList = ILevelLoader.getCopyOfPowerUpList();
    this.winnableList = ILevelLoader.getCopyOfWinnableList();
    this.entityList = ILevelLoader.getCopyOfEntityList();
    this.levelLength = ILevelLoader.getLevelLength();
    this.levelWidth = ILevelLoader.getLevelWidth();
  }

  /**
   * Adds a new entity to the level. There are several lists of entities, and it is placed
   * in the correct list(s) depending on what properties the entity has.
   * @param entity to be added to the level.
   */
  @Override
  public void addEntity(IEntity entity) {
    if (entity!=null) {
      this.entityList.add(entity);
    }

    if (entity instanceof Block) {
      this.blockList.add((Block)entity);
    }
    if (entity instanceof Enemy) {
      this.enemyList.add((Enemy)entity);
    }
    if (entity instanceof IMovable) {
      this.movableEntityList.add((IMovable)entity);
    }
    if (entity instanceof Player) {
      this.playerList.add((Player)entity);
    }
    if (entity instanceof PowerUp) {
      PowerUp powerUp = (PowerUp) entity;
      powerUp.setRandomModifier(MODIFIER_VALUE, MODIFIER_DURATION * FRAMES_PER_SECOND);
      this.powerUpList.add(powerUp);
    }
    if (entity instanceof IWinnable) {
      this.winnableList.add((IWinnable)entity);
    }
  }

  /**
   * Removes an entity from the level by removing it from all lists of entities.
   * @param entity to be removed.
   */
  @Override
  public void removeEntity(IEntity entity) {
    if(entity != null){
      this.entityList.remove(entity);
    }
    if (entity instanceof Block) {
      this.blockList.remove(entity);
    }
    if (entity instanceof Enemy) {
      this.enemyList.remove(entity);
      score+=ENEMY_SCORE;
    }
    if (entity instanceof Player) {
      this.playerList.remove(entity);
    }
    if (entity instanceof PowerUp) {
      this.powerUpList.remove(entity);
    }
    if (entity instanceof IMovable) {
      this.movableEntityList.remove(entity);
    }
    if (entity instanceof IWinnable) {
      this.winnableList.remove(entity);
    }
  }

  /**
   * Gets the entity at a certain set of coordinates.
   * @param xCoordinate of entity
   * @param yCoordinate of entity
   * @return entity at the coordinates. If there is no entity there, it returns null.
   */
  @Override
  public Optional<IEntity> getEntityAt(int xCoordinate, int yCoordinate) {
    for(IEntity entity : entityList){
      if((int)entity.getHitBox().getXLeft() == xCoordinate && (int)entity.getHitBox().getYTop() == yCoordinate){
        return Optional.of(entity);
      }
    }
    return Optional.empty();
  }

  /**
   * Makes sure that the player is within bounds (i.e. xleft > 0 and xright <
   * scroller.NUM_BLOCKS or the num blocks wide and tall on display) and if
   * not, places that player within bounds
   *
   * @param player the player whose bounds will be checked
   */
  @Override
  public void keepPlayerInBounds(Player player) {
      if (player.getHitBox().getXLeft() < 0) {
        player.getHitBox().setXLeft(0);
      }
      else if (player.getHitBox().getXRight() > scroller.NUM_BLOCKS) {
        player.getHitBox().setXRight(scroller.NUM_BLOCKS);
      }
  }

  /**
   * Checks whether the game is won or lost. Games can have varying win conditions,
   * but the lose condition is if the size of the playerList is 0 (if the player is
   * removed from the game, through death, falling or otherwise)
   */
  @Override
  public void checkWinLoseConditions(){
    if (playerList.size() == 0) {
      setLevelLost(true);
    }
    else{
      if(!winnableList.isEmpty()){
        int winCount = 0;
        for(IWinnable winnable : winnableList){
          if(winnable.getHasWon()){
            winCount++;
          }
        }
        if(winCount >= winnableList.size()){
          setLevelWon(true);
        }
      }
    }
  }


  /**
   * Checks to see if the player has lost the level (i.e. fell through
   * bottom of screen) and if so resets the level
   */
  @Override
  public void checkFellOutOfLevel() {
    if (playerList.size() > 0) {
      Player player = playerList.get(0);
      if (player.getHitBox().getYTop() > scroller.NUM_BLOCKS) {
        setLevelLost(true);
      }
    }
  }

  /**
   * Handles the situation where the player has fallen off of the screen
   */
  @Override
  public void reinitialize() {
    loader.reinitialize();
    setOrResetLevel(loader);

    scroller.reset();
    levelLost = false;
    levelWon = false;
  }

  /**
   * Setter for isLevelWon
   * @param isLevelWon boolean that is true if level is won
   */
  @Override
  public void setLevelWon(boolean isLevelWon) {
    this.levelWon = isLevelWon;
  }

  /**
   * Setter for isLevelLost
   * @param isLevelLost boolean that is true if level is lost
   */
  @Override
  public void setLevelLost(boolean isLevelLost) {
    this.levelLost = isLevelLost;
  }

  /**
   * Getter for isLevelWon
   * @return true if the level is won
   */
  @Override
  public boolean isLevelWon(){
    return levelWon;
  }

  /**
   * Getter for keyPressFunctions
   * @return the KeyPressFunctions object, which holds booleans that remember what key functions
   * are currently activated
   */
  @Override
  public IKeyPressFunctions getKeyPressFunctions() {
    return IKeyPressFunctions;
  }

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
  @Override
  public List<IEntity> getCopyOfEntityList() {
    return new ArrayList<>(entityList);
  }

  /**
   * Gets the list containing every entity in the level.
   * This is used internally in Level for the purpose of
   * iterating through all entities.
   *
   * Changing the contents of this list WILL
   * change the Entity information stored in Level.
   *
   * @return entityList
   */
  @Override
  public List<IEntity> getAllEntities() { return entityList; }

  /**
   * Gets the list containing every player in the level
   * @return playerList
   */
  @Override
  public List<Player> getPlayerList() {return playerList;}

  /**
   * Reveals the score of the player within the level
   * @return score
   */
  @Override
  public int getScore() {
    return score;
  }

  /**
   * Reveals if the level has lost
   * @return levelLost
   */
  @Override
  public boolean isLevelLost() {
    return levelLost;
  }

  /**
   * Reveals if we're saving the game
   * @return isSaving
   */
  @Override
  public boolean isSaving() {
    return isSaving;
  }

  /**
   * Sets a variable to show if we're saving the game
   * @param save the indicator for if we're saving
   */
  @Override
  public void setIsSaving(boolean save) {
    isSaving = save;
  }

}
