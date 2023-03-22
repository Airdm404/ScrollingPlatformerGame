public interface LevelReader{

    void readFile(File levelFile) throws FileNotFoundException;

    Level createLevel();

}