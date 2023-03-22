package model.entity;

import api.model.entity.IEntity;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PowerUpTest {

    @Test
    public void playerSpawnsPowerUp() {
        PowerUpBlock powerUpBlock = new PowerUpBlock(5, 5);
        Player player = new MarioPlayer(5, 5.9);

        player.checkCollision(powerUpBlock);

        Optional<IEntity> optionalSpawnedEntity = powerUpBlock.attemptSpawnEntity();
        assertTrue(optionalSpawnedEntity.isPresent());
    }

    @Test
    public void playerConsumesPowerUp() {
        PowerUp powerUp = new PowerUp(5, 5);
        assertFalse(powerUp.hasAppliedModifier());
        Player player = new MarioPlayer(5, 5.9);
        player.checkCollision(powerUp);
        assertTrue(powerUp.hasAppliedModifier());
    }

    @Test
    public void playerConsumesPowerUpAndObtainsModifier() {
        PowerUp powerUp = new PowerUp(5, 5);
        powerUp.setModifier(new Modifier(Modifier.ModifierType.MOVEMENT_SPEED, 1.5, 10 * 60));
        assertFalse(powerUp.hasAppliedModifier());
        Player player = new MarioPlayer(5, 5.9);
        player.checkCollision(powerUp);
        assertTrue(powerUp.hasAppliedModifier());
        assertTrue(player.getModifiers().containsKey(Modifier.ModifierType.MOVEMENT_SPEED));
    }

    @Test
    public void playerConsumesPowerUpAndObtainsModifierAndThenLosesIt() {
        PowerUp powerUp = new PowerUp(5, 5);
        powerUp.setModifier(new Modifier(Modifier.ModifierType.MOVEMENT_SPEED, 1.5, 10 * 60));
        assertFalse(powerUp.hasAppliedModifier());
        Player player = new MarioPlayer(5, 5.9);
        player.checkCollision(powerUp);
        assertTrue(powerUp.hasAppliedModifier());
        assertTrue(player.getModifiers().containsKey(Modifier.ModifierType.MOVEMENT_SPEED));
        for(int i = 0; i < 10 * 60; i++){
            player.updateModifiers();
        }
        assertFalse(player.getModifiers().containsKey(Modifier.ModifierType.MOVEMENT_SPEED));
    }
}
