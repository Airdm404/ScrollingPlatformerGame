package api.view;

import javafx.css.Styleable;
import javafx.event.EventTarget;

import java.io.FileNotFoundException;

public interface ILeaderboardView extends EventTarget, Styleable {

    /**
     * Initializes this LeaderboardView object
     */
    void initialize();

    /**
     * Builds the title text label
     */
    void buildTitle();

    /**
     * Builds the score text
     * @param index the index of the score text label
     */
    void buildScoreText(double index);

    /**
     * Updates the high scores
     */
    void updateLeaderboard() throws FileNotFoundException;
}
