package model.scroll;

import model.HitBox;
import model.Level;
import model.entity.Player;
import api.model.scroll.Scroller;

/**
 * This class's responsibility is to automatically scroll the level (i.e. to move the visible
 * pane defining which part of the level the user sees independently of any user input) It, like
 * all scrollers, is primarily used in the scroll() method in the Level class, where the Level
 * object calls scroller.scroll(this [i.e. the Level object], gamePlayer)
 *
 * @author Alex Lu
 */
public class AutoScroller implements Scroller {
    protected double xScroll;
    protected double yScroll;
    protected boolean playerScrolls;

  private static final int SCORE_FROM_SCROLL = 1;


  /**
   * Constructs an AutoScroller object
   * @param x the amount by which this scroller will translate the x components of all Entities
   *          in the level it is scrolling each time this scroller scrolls it
   * @param y the amount by which this scroller will translate the y components of all Entities
   *          in the level it is scrolling each time this scroller scrolls it
   * @param lockPlayerInPlace true if the player's location stays fixed on the screen after a
   *                          scroll, false if not
   */
  public AutoScroller(double x, double y, boolean lockPlayerInPlace) {
      xScroll = x;
      yScroll = y;
      playerScrolls = lockPlayerInPlace;
    }

  /**
   * Scrolls the list of Entitiies
   * @param level the level to be scrolled
   * @param player the player of the level
   */
  @Override
  public void scroll(Level level, Player player) {
    level.translateAllEntities(xScroll, yScroll);

    if (playerScrolls) {
      scrollPlayer(player);
    }
  }

  /**
   * Resets the scroller
   */
  @Override
  public void reset() {
    //DO NOTHING
  }


  /**
   * Returns an integer value which should be added to the user's score due to survival of
   * the last scroll
   * @return SCORE_FROM_SCROLL
   */
  @Override
  public int getScoreFromScroll() {
    return SCORE_FROM_SCROLL;
  }

  /**
   * Moves the Player in the reverse direction as the level is moving to simulate locking them
   * in place
   *
   * @param player the Player to be scrolled
   */
  public void scrollPlayer(Player player) {
    HitBox hitBox = player.getHitBox();
    hitBox.translateX(-1 * xScroll);
    hitBox.translateY(-1 * yScroll);
  }
}
