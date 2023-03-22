package model.score;


import api.model.score.IGameLeaderboard;
import api.model.score.IScoreTuple;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * This class' responsibility is to serve as a leaderboard of high scores for a single game - it
 * holds a list of ScoreTuples representing (player, score) pairings and is able to sort them so
 * that the highest scores appear at the start of the list. LeaderboardView will use this class as a
 * model of sorts on which to base the information that it displays.
 *
 * @author Alex Lu
 */
public class GameLeaderboard implements IGameLeaderboard {

  private final String path;
  private List<IScoreTuple> scoresList;
  private final Scanner leaderboardScanner;
  private String title;
  private static final String PATH_START = "./src/resources/leaderboards/";

  /**
   * Constructs a GameLeaderboard object
   *
   * @param filepath the filepath that points to the leaderboard xml file
   */
  public GameLeaderboard(String filepath) throws FileNotFoundException {
    path = filepath;

    File leaderboardFile = new File(PATH_START + filepath);
    leaderboardScanner = new Scanner(leaderboardFile);

    buildTitle();
    buildScoresList();
    sortScoresList();
  }

  /**
   * Saves the title of the file
   */
  @Override
  public void buildTitle() {
    if (leaderboardScanner.hasNextLine()) {
      title = leaderboardScanner.nextLine();
    }
  }

  /**
   * Instantiates and builds the scoresList which will hold all of the scores
   */
  @Override
  public void buildScoresList() {
    scoresList = new ArrayList<>();
    while (leaderboardScanner.hasNextLine()) {
      insertIntoScoresList(leaderboardScanner.nextLine());
    }
  }

  /**
   * Reads data from a line, uses it to construct an ScoreTuple and then adds it to scoresList
   *
   * @param line the line whose data will be inserted into scoresList
   */
  @Override
  public void insertIntoScoresList(String line) {
    String[] nameScore = line.split(",");

    String name = nameScore[0];
    int score = Integer.parseInt(nameScore[1]);
    ScoreTuple tuple = new ScoreTuple(name, score);
    scoresList.add(tuple);
  }

  /**
   * Sorts the list of scores so that the highest one is at the top
   */
  @Override
  public void sortScoresList() {
    Collections.sort(scoresList);
  }

  /**
   * Returns the score tuple holding data on the indexth highest score (i.e. a call of
   * getScoreTupleAtPlace(1) would return the String representation of the ScoreTuple for the
   * highest score on the leaderboard
   *
   * @return the String representing the indexth highest placer on the leaderboard
   */
  @Override
  public String getScoreTupleAtPlace(int index) {
    sortScoresList();
    if (index <= 0 || index > scoresList.size()) {
      return "";
    }
    return scoresList.get(index - 1).toString();
  }

  /**
   * Returns the title associated with the java file
   *
   * @return title
   */
  @Override
  public String getTitle() {
    return title;
  }

  /**
   * Adds a ScoreTuple to this game's leaderboard
   *
   * @param toBeAdded the tuple to be added to the leaderboard
   */
  @Override
  public void addScoreTuple(IScoreTuple toBeAdded) throws IOException {
    scoresList.add(toBeAdded);
    writeTupleToFile(toBeAdded);
    sortScoresList();
  }

  /**
   * Saves the contents of a ScoreTuple to a file
   *
   * @param toBeWritten the tuple whose data will be saved in a file
   */
  @Override
  public void writeTupleToFile(IScoreTuple toBeWritten) throws IOException {
    String line = toBeWritten.getName() + "," + toBeWritten.getScore();

    FileWriter writer = new FileWriter(PATH_START + path, true);

    writer.write("\n" + line);
    writer.close();
  }
}
