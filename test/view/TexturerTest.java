package view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.entity.Block;
import api.model.entity.IEntity;
import model.entity.MarioPlayer;
import model.entity.Player;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Tests the Texturer class
 */
public class TexturerTest extends DukeApplicationTest{

  private Group textureNode;
  private Texturer texturer;
  private List<IEntity> entityList;

  private static final double DEFAULT_BLOCKS_WIDE = 10;
  private static final double DEFAULT_BLOCKS_HIGH = 10;
  private static final String TEST_ENTITY = "entity";
  private static final String PATH = "resources/images/texturefiles/gametextures.properties";
  private static final double WIDTH = 800;
  private static final double HEIGHT = 800;

  @Override
  public void start(Stage st) {
    textureNode = new Group();
    entityList = new ArrayList<>();
    texturer = new Texturer(WIDTH, HEIGHT, PATH, textureNode);
  }

  /**
   * Tests that a simple texturing works as intended - i.e. the texturer puts an Entity in the right
   * place in the textureNode
   */
  @Test
  public void testSimpleTexture() {
      Player pe = new MarioPlayer(2,3);
      entityList.add(pe);

      texturer.updateTextures(entityList, DEFAULT_BLOCKS_WIDE,
          DEFAULT_BLOCKS_HIGH);
      assertEquals(1, textureNode.getChildren().size());


      ImageView view = (ImageView)textureNode.lookup("#Playerx2y3");
      assertEquals(2 * WIDTH/DEFAULT_BLOCKS_WIDE, view.getX());
      assertEquals(3 * HEIGHT/DEFAULT_BLOCKS_HIGH, view.getY());
      assertEquals(WIDTH/DEFAULT_BLOCKS_WIDE, view.getFitWidth());
      assertEquals(HEIGHT/DEFAULT_BLOCKS_HIGH, view.getFitHeight());
  }

  /**
   * Tests that the texturer does not break when given a bad texture file
   */
  @Test
  public void testBadTextureFile() {
    Texturer badFile = new Texturer(WIDTH, HEIGHT, "hi", textureNode);
    Block bbe = new Block(5,6);
    entityList.add(bbe);

    badFile.updateTextures(entityList, DEFAULT_BLOCKS_WIDE,
        DEFAULT_BLOCKS_HIGH);
    assertEquals(1, textureNode.getChildren().size());

    ImageView view = (ImageView)textureNode.lookup("#Blockx5y6");
    assertEquals(5 * WIDTH/DEFAULT_BLOCKS_WIDE, view.getX());
    assertEquals(6 * HEIGHT/DEFAULT_BLOCKS_HIGH, view.getY());
    assertEquals(WIDTH/DEFAULT_BLOCKS_WIDE, view.getFitWidth());
    assertEquals(HEIGHT/DEFAULT_BLOCKS_HIGH, view.getFitHeight());

  }
}