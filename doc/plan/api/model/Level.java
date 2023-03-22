import PowerUpUseCase.Entity;

public interface Level{

    int[][] getGrid();

    Entity getEntity(int xCoordinate, int yCoordinate);

    void setLevelWon(boolean isLevelWon);

    boolean isLevelWon();

    void setLevelLost(boolean isLevelLost);

    boolean isLevelLost();

}