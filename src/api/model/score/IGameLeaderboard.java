package api.model.score;

import java.io.IOException;

/**
 * The GameLeaderboard interface
 */
public interface IGameLeaderboard {

    /**
     * Saves the title of the file
     */
    void buildTitle();

    /**
     * Instantiates and builds the scoresList which will hold all of the scores
     */
    void buildScoresList();

    /**
     * Reads data from a line, uses it to construct an ScoreTuple and then adds it to scoresList
     *
     * @param line the line whose data will be inserted into scoresList
     */
    void insertIntoScoresList(String line);

    /**
     * Sorts the list of scores so that the highest one is at the top
     */
    void sortScoresList();

    /**
     * Returns the score tuple holding data on the indexth highest score (i.e. a call of
     * getScoreTupleAtPlace(1) would return the String representation of the ScoreTuple for the
     * highest score on the leaderboard
     *
     * @return the String representing the indexth highest placer on the leaderboard
     */
    String getScoreTupleAtPlace(int index);

    /**
     * Returns the title associated with the java file
     *
     * @return title
     */
    String getTitle();

    /**
     * Adds a ScoreTuple to this game's leaderboard
     *
     * @param toBeAdded the tuple to be added to the leaderboard
     */
    void addScoreTuple(IScoreTuple toBeAdded) throws IOException;

    /**
     * Saves the contents of a ScoreTuple to a file
     *
     * @param toBeWritten the tuple whose data will be saved in a file
     */
    void writeTupleToFile(IScoreTuple toBeWritten) throws IOException;
}
