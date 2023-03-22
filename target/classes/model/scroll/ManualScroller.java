package model.scroll;

import model.Level;
import model.entity.Player;
import api.model.scroll.Scroller;

/**
 * This class's responsibility is to manually scroll the level (i.e. to move the visible
 * pane defining which part of the level the user sees dependently on the userinput) It, like
 * all scrollers, is primarily used in the scroll() method in the Level class, where the Level
 * object calls scroller.scroll(this [i.e. the Level object], gamePlayer)
 *
 * @author Alex Lu
 */
public class ManualScroller implements Scroller {
    protected double leftBound;
    protected double rightBound;
    protected double upBound;
    protected double downBound;

    protected double currentXScroll;
    protected double currentYScroll;

    public static final int NO_SCROLL = -1;
    public static final int ALWAYS_SCROLL = 0;
    private static final int SCORE_FROM_SCROLL = 0;

  /**
   * Instantiates and configures a ManualScroller object
   *
   * @param l the boundary for player movement (i.e. x = l) where if the player goes to its left,
   *          the game will scroll left
   * @param r the boundary for player movement (i.e. x = r) where if the player goes to its right,
   *          the game will scroll right
   * @param u the boundary for player movement (i.e. y = u) where if the player goes above it, the
   *          game will scroll up
   * @param d the boundary for player movement (i.e. y = d) where if the player goes below it, the
   *          game will scroll up
   *
   *          Note: a value of 0 to l, r, u or d means that the game will scroll in that direction
   *          if the player moves in the direction regardless of their location Note: a value of -1
   *          to l, r, u or d means that the game will never scroll in that relevant direction
   *          regardless of the player's location or movement
   */
    public ManualScroller(double l, double r, double u, double d) {
      leftBound = l;
      rightBound = r;
      upBound = u;
      downBound = d;
    }

  /**
   * Uses the velocity of the player to determine how to shift all entities in order to simulate
   * scrolling
   *
   * checkLeftScroll checks to see if the player is to the left of some vertical line x = leftBound
   * and moving further to the left if so, scrolls the screen to the left in order to catch up
   *
   * checkRightScroll checks to see if the player is to the right of some vertical line x =
   * rightBound and moving further to the right if so, scrolls the screen to the left in order to
   * catch up
   *
   * checkUpScroll checks to see if the player is above some horizontal line y = topBound and moving
   * further up and if so, scrolls the screen up in order to catch up
   *
   * checkDownScroll checks to see if the player is below some horizontal line y = bottomBound and
   * moving further down and if so, scrolls the screen down in order to catch up
   *
   * @param level the level to be scrolled
   * @param player the player whose data we are to use
   */
  @Override
  public void scroll(Level level, Player player) {
      currentXScroll = 0.0;
      currentYScroll = 0.0;

      checkLeftScroll(player);
      checkRightScroll(player);
      checkUpScroll(player);
      checkDownScroll(player);

      level.translateAllEntities(currentXScroll, currentYScroll);
  }

  /**
   * Resets the scroller
   */
  @Override
  public void reset() {
    //DO NOTHING
  }


  /**
   * Checks to see if the screen should scroll left
   * @param player the player whose action will determine the scroll
   */
  private void checkLeftScroll(Player player) {
      if ((player.getHitBox().getXLeft() < leftBound && leftBound!= NO_SCROLL)
          || leftBound == ALWAYS_SCROLL) {

          if (player.getXVel() <0) {
            currentXScroll -= player.getXVel();
          }
      }
  }

  /**
   * Checks to see if the screen should scroll right
   * @param player the player whose action will determine the scroll
   */
  private void checkRightScroll(Player player) {
    if ((player.getHitBox().getXRight() > rightBound && rightBound!= NO_SCROLL)
          || rightBound == ALWAYS_SCROLL) {

        if(player.getXVel() > 0) {
          currentXScroll -= player.getXVel();
        }
    }
  }

  /**
   *Checks to see if the screen should scroll up
   * @param player the player whose action will determine the scroll
   */
  private void checkUpScroll(Player player) {
    if ((player.getHitBox().getYTop() < upBound  && upBound!= NO_SCROLL)
          || upBound == ALWAYS_SCROLL) {

        if (player.getYVel() < 0) {
          currentYScroll -= player.getYVel();
        }
    }
  }

  /**
   * Checks to see if the screen should scroll down
   * @param player the player whose action will determine the scroll
   */
  private void checkDownScroll(Player player) {
    if ((player.getHitBox().getYBottom() > downBound && downBound!= NO_SCROLL)
          || downBound == ALWAYS_SCROLL) {

      if (player.getYVel() > 0) {
        currentYScroll -= player.getYVel();
      }
    }
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


}
