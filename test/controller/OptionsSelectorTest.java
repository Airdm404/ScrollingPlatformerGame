package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import javafx.stage.Stage;
import util.DukeApplicationTest;
import org.junit.jupiter.api.Test;


/**
 * Tests the OptionsSelector class - note the .handlePush(String s) method is used to simulate
 * pushing a button on an OptionsSelector because the all buttons on the OptionsSelector call that
 * method with a String input
 */
public class OptionsSelectorTest extends DukeApplicationTest{

  private static final double WIDTH = 800;
  private static final double HEIGHT = 800;
  private OptionsSelector selector;

  @Override
  public void start(Stage stage) throws BuilderInstantiationException {
    List<String> choices = new ArrayList<>();
    choices.add("a");
    choices.add("b");
    choices.add("c");
    choices.add("d");
    selector = new OptionsSelector(WIDTH, HEIGHT, choices);
  }

  /**
   * Tests that the options selector returns the correct output when choice 1 is pushed without
   * the user navigating to any other pages
   */
  @Test
  public void testOptionSelectorChoice1Page1() {
    selector.handlePush("chooseTopChoice");
    assertEquals("a", selector.getTextInBuffer());
  }

  /**
   * Tests that the options selector returns the correct output when choice 2 is pushed without
   * the user navigating to any other pages
   */
  @Test
  public void testOptionSelectorChoice2Page1() {
    selector.handlePush("chooseMiddleChoice");
    assertEquals("b", selector.getTextInBuffer());
  }

  /**
   * Tests that the options selector returns the correct output when choice 3 is pushed without
   * the user navigating to any other pages
   */
  @Test
  public void testOptionSelectorChoice3Page1() {
    selector.handlePush("chooseBottomChoice");
    assertEquals("c", selector.getTextInBuffer());
  }

  /**
   * Tests that the options selector returns the correct output when choice 1 is pushed after
   * navigating to page 2
   */
  @Test
  public void testOptionsSelectorChoice1Page2() {
    selector.handlePush("nextOptions");
    selector.handlePush("chooseTopChoice");
    assertEquals("d", selector.getTextInBuffer());
  }

  /**
   * Tests that the options selector returns the correct output when choice 2 is pushed after
   * navigating to page 2 (i.e. the empty String should return since there is no 5th choice)
   */
  @Test
  public void testOptionsSelectorChoice2Page2() {
    selector.handlePush("nextOptions");
    selector.handlePush("chooseMiddleChoice");
    assertEquals("", selector.getTextInBuffer());
  }

  /**
   * Tests the ability to add a choice to the OptionsSelector - this choice should now be the middle
   * choice on page 2 as it has been appended to the end of the list
   */
  @Test
  public void testAddOption() {
    selector.addOption("e");
    selector.handlePush("nextOptions");
    selector.handlePush("chooseMiddleChoice");
    assertEquals("e", selector.getTextInBuffer());
  }

  /**
   * Tests the ability to remove a choice from the OptionsSelector - the option at c's spot
   * (bottom of page 1) should now be d as it shifts forward to replace c
   */
  @Test
  public void testRemoveOption() {
    selector.removeOption("c");
    selector.handlePush("chooseBottomChoice");
    assertEquals("d", selector.getTextInBuffer());
  }

  /**
   * Tests that when the user presses next on the last page of options, they wrap forwards to the
   * first page
   */
  @Test
  public void testWrapForwards() {
    selector.handlePush("chooseTopChoice");
    assertEquals("a", selector.getTextInBuffer());

    selector.handlePush("nextOptions");
    selector.handlePush("chooseTopChoice");
    assertEquals("d", selector.getTextInBuffer());

    selector.handlePush("nextOptions");
    selector.handlePush("chooseTopChoice");
    assertEquals("a", selector.getTextInBuffer());
  }

  /**
   * Tests that when the user presses prev on the first page of options, they wrap backwards to the
   * last page
   */
  @Test
  public void testWrapBackwards() {
    selector.handlePush("chooseTopChoice");
    assertEquals("a", selector.getTextInBuffer());

    selector.handlePush("prevOptions");
    selector.handlePush("chooseTopChoice");
    assertEquals("d", selector.getTextInBuffer());
  }
}