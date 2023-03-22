package api.view.scenes;

import javafx.event.EventTarget;
import api.view.IGameScene;

/**
 * The HighScoreScene interface
 */
public interface IHighScoreScene extends EventTarget, IGameScene {

    /**
     * Builds the leaderboard view
     */
    void buildLeaderBoardViews();

    /**
     * Builds a leaderboard view and adds it to root
     */
    void buildLeaderBoardView(String path, double x, double y);

    /**
     * Updates the contents of all leaderboards in the scene
     */
    void updateLeaderboards();
}
