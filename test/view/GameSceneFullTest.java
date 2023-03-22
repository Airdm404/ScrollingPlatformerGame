package view;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import util.DukeApplicationTest;
import org.junit.jupiter.api.Test;
import api.view.IGameScene;


/**
 * Tests the GameScene class
 */
public class GameSceneFullTest extends DukeApplicationTest {

  IGameScene testScene;
  private final double WIDTH = 800;
  private final double HEIGHT = 800;

  @Override
  public void start(Stage stage) {
    testScene = new GameScene(new Group(), "Test", WIDTH, HEIGHT);
  }

  /**
   * Tests that it is possible to add an element to the root node associated with a game scene
   */
  @Test
  public void testAddElementToRoot() {
    Button testButton = new Button("hi");
    String id = "123";
    testButton.setId(id);

    testScene.addElementToRoot(testButton);
    assertDoesNotThrow(() ->testScene.lookupElementInRoot(id));
  }

  /**
   * Tests that it is possible to remove an element from the root node associated with a game scene
   */
  @Test
  public void testRemoveElementFromRoot() {
    Button testButton = new Button("hi");
    String id = "123";
    testButton.setId(id);

    testScene.addElementToRoot(testButton);
    assertDoesNotThrow(() ->testScene.lookupElementInRoot(id));

    testScene.removeElementFromRoot(testButton);
    assertThrows(NullPointerException.class, () -> testScene.lookupElementInRoot(id));

  }

  /**
   * Tests that it is possible to remove an element from the root node associated with a game scene
   */
  @Test
  public void testLookupElementInRoot() {
    Button testButton = new Button("hi");
    String id = "123";
    testButton.setId(id);

    testScene.addElementToRoot(testButton);
    Node foundNode = testScene.lookupElementInRoot(id);
    assertEquals(Button.class, foundNode.getClass());
    assertEquals(testButton.getText(),((Button)foundNode).getText());

  }
}
