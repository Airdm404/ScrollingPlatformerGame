package model.scroll;

import java.util.List;
import model.Level;
import model.autogenerator.GenerationException;
import api.model.entity.IEntity;
import model.entity.Player;

/**
 * This class's responsibility is to automatically scroll the level (i.e. to move the visible pane
 * defining which part of the level the user sees independently of any user input) while also
 * generating new chunks of entities and inserting those chunks into the level to simulate infinite
 * automatic level generation. It uses an AutoGenerationHelper to accomplish this generation.
 *
 * It, like all scrollers, is primarily used in the scroll() method in the Level class, where the
 * Level object calls scroller.scroll(this [i.e. the Level object], gamePlayer)
 *
 * @author Alex Lu
 */
public class AutoGenerationScroller extends AutoScroller {
  private double flagX;
  private final AutoGenerationHelper helper;
  private final int GENERATE_MAX_BOUND;
  private static final String EXCEPTION_MESSAGE = "Failed to build auto-generation";

  /**
   * Instantiates an AutoGenerationScroller object
   * @param xScr the amount by which this scroller will translate the x components of all Entities
   *        in the level it is scrolling each time this scroller scrolls it
   * @param yScr the amount by which this scroller will translate the y components of all Entities
   *        in the level it is scrolling each time this scroller scrolls it
   * @param pScrolls true if the player's location stays fixed on the screen after a
   *        scroll, false if not
   * @param path a String reference to the xml file to be used to generate levels
   */
  public AutoGenerationScroller(double xScr, double yScr, boolean pScrolls, String path) {
    super(xScr,yScr, pScrolls);

    try {
      helper = new AutoGenerationHelper(path);

      GENERATE_MAX_BOUND = NUM_BLOCKS;
      flagX = GENERATE_MAX_BOUND;
    }
    catch (Exception e) {
      throw new GenerationException(EXCEPTION_MESSAGE);
    }
  }

  /**
   * Scrolls all of the entities
   * @param level the level to be scrolled
   * @param player the player of the level
   */
  @Override
  public void scroll(Level level, Player player) {
    checkForGeneration(level);
    flagX+=xScroll;

    super.scroll(level, player);
  }

  /**
   * Checks to see if it's necessary to generate a new generation
   */
  private void checkForGeneration(Level level) {
    if (flagX <= GENERATE_MAX_BOUND) {
      helper.generateForLevel(level, 0, flagX);
      flagX+= helper.getAddedNumColumns();
      cleanGarbage(level);
    }
  }

  /**
   * Checks the entityList to see if any of the entities have gone off screen forever (i.e. have
   * x < 0), if so, removes them from entityList
   *
   * @param level the Level whose garbage we're cleaning
   */
  private void cleanGarbage(Level level) {
    List<IEntity> entityList = level.getAllEntities();

    for (int index = entityList.size() - 1; index >= 0; index --) {
      IEntity entity = entityList.get(index);
      if (entity.getHitBox().getXRight() < 0) {
        entityList.remove(entity);
      }
    }
  }

  /**
   * Resets the scroller - i.e. return flagX to its starting position so that automatic level
   * generation can proceed as if this object has just been instantiated
   */
  @Override
  public void reset() {
    flagX = GENERATE_MAX_BOUND;
  }
}
