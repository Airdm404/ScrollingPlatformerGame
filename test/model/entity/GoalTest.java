package model.entity;

import model.Level;
import model.configuration.*;
import api.model.configuration.IGameConfiguration;
import api.model.entity.IEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GoalTest {


    private static final float GRAVITY_FACTOR = 0.015f;
    private static final int LEVEL_STEP_AMOUNT = 10;
    private static final double ENEMY_MOVEMENT_SPEED = 0.1;
    private static final double TOLERANCE = 0.01;
    private static final String TEST_CONFIGURATION = "collision_test.properties";
    private static final String TEST_SAVE_PREFIX = "data/saves/";

    @Test
    public void playerFallsOnGoalAndWinsTest() throws InvalidFileException {
        IGameConfiguration IGameConfiguration = new GameConfiguration(TEST_CONFIGURATION);
        ILevelLoader ILevelLoader = new LevelLoader(IGameConfiguration.getLevelFile(), new EntityFactory());
        Level level = new Level(ILevelLoader);
        Goal goal = null;
        Player player = null;
        for(IEntity entity : level.getCopyOfEntityList()){
            if(entity instanceof Goal && goal == null){
                goal = (Goal)entity;
            }
            else if(entity instanceof Player && player == null){
                player = (Player)entity;
            }
        }
        assertTrue(level.getCopyOfEntityList().contains(player));
        assertTrue(level.getCopyOfEntityList().contains(goal));
        if(player != null && goal != null){
            double xPosition = goal.getHitBox().getXLeft();
            double yPosition = goal.getHitBox().getYTop();
            player.getHitBox().setXLeft(xPosition);
            float fallDistance = LEVEL_STEP_AMOUNT * 10 * GRAVITY_FACTOR;
            player.getHitBox().setYTop(yPosition - fallDistance);
            player.setGrounded(false);
            for(int i = 0; i < LEVEL_STEP_AMOUNT * 10; i++){
                // ensure player falls directly above goal - unneeded in actual gameplay
                player.getHitBox().setXLeft(xPosition);
                level.step();
            }
            assertFalse(level.isLevelLost());
            assertTrue(level.isLevelWon());
        }
    }

    @Test
    public void playerLosesLevel() throws InvalidFileException {
        IGameConfiguration IGameConfiguration = new GameConfiguration(TEST_CONFIGURATION);
        ILevelLoader ILevelLoader = new LevelLoader(IGameConfiguration.getLevelFile(), new EntityFactory());
        Level level = new Level(ILevelLoader);
        Player player = null;
        for(IEntity entity : level.getCopyOfEntityList()){
            if(entity instanceof Player && player == null){
                player = (Player)entity;
            }
        }

        if(player != null){
            assertTrue(level.getCopyOfEntityList().contains(player));
            player.getHitBox().setXLeft(level.getLevelWidth());
            player.getHitBox().setYTop(level.getLevelLength());
            level.step();
            assertFalse(level.isLevelWon());
            assertTrue(level.isLevelLost());
        }
    }
}
