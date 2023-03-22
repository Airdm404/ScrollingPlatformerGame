package api.model.score;

import org.jetbrains.annotations.NotNull;

/**
 * The ScoreTuple interface object
 */
public interface IScoreTuple extends Comparable<IScoreTuple> {

    /**
     * The String representation of this object
     *
     * @return a String representation of the form "NAME: SCORE"
     */
    @Override
    String toString();

    /**
     * Returns the name associated with this ScoreTuple
     *
     * @return name
     */
    String getName();

    /**
     * Returns the score associated with this tuple
     *
     * @return score
     */
    int getScore();

    /**
     * Compares this ScoreTuple to another ScoreTuple
     *
     * @param other the other ScoreTuple
     * @return a value determining the ordering of this ScoreTuple and the other
     */
    @Override
    int compareTo(@NotNull IScoreTuple other);
}
