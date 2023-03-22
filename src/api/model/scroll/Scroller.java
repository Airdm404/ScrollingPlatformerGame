package api.model.scroll;

import model.Level;
import model.entity.Player;

/**
 * The Scroller interface
 */
public interface Scroller {
    int NUM_BLOCKS = 15;

    /**
     * Scrolls the level, perhaps based on the actions of player
     * @param level the Level object to be scrolled
     * @param player the Player object whose actions might determine the scrolling of level
     */
    void scroll(Level level, Player player);

    /**
     * Resets the scroller
     */
    void reset();

    /**
     * Returns an integer value which should be added to the user's score due to survival of
     * the last scroll
     * @return an integer value representing the score gained by the user from the last scroll
     */
    int getScoreFromScroll();
}
