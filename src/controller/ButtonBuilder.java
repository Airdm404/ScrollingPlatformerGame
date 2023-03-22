package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import api.controller.IButtonBuilder;
import api.controller.IButtonPushHandler;
import javafx.scene.control.Button;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Is responsible for creating Buttons from a config file that takes the form XXXX.xml. For
 * instructions on how to write such a config file, as well as an in depth description of the
 * definitions of element tags within existing config files, please refer to the README
 *
 * @author Alex Lu
 */
public class ButtonBuilder extends Builder implements IButtonBuilder {

  private final List<Button> foundButtons;
  private final IButtonPushHandler handler;

  private static final String BUTTON = "button";
  private static final String ID = "id";
  private static final String CENTERX = "centerx";
  private static final String CENTERY = "centery";
  private static final String BUTTON_WIDTH = "width";
  private static final String BUTTON_HEIGHT = "height";
  private static final String METHOD = "method";
  private static final String TITLE = "title";
  private static final String BUILDER_EXCEPTION = "BUTTON_ERROR";

  /**
   * Instantiates a new ButtonBuilder object
   *
   * @param w    the WIDTH of the area in which buttons can be placed
   * @param h    the HEIGHT of the area in which buttons can be placed
   * @param path the filepath leading to the .txt file containing data on the button
   * @param bph  the handler who will respond when a Button that has been created is pushed
   * @throws BuilderInstantiationException if failing to build a button
   */
  public ButtonBuilder(double w, double h, String path, IButtonPushHandler bph)
      throws BuilderInstantiationException {

    super(w,h);
    foundButtons = new ArrayList<>();
    handler = bph;

    try {
      makeButtons(path);
    }
    catch (Exception e) {
      throw new BuilderInstantiationException(BUILDER_EXCEPTION);
    }
  }


  /**
   * Makes each of the buttons as specified in a particular file
   *
   * @param xmlPath the file to be read
   */
  public void makeButtons(String xmlPath)
          throws IOException, ParserConfigurationException, SAXException {
    Element root = buildRoot(xmlPath);

    NodeList buttons = root.getElementsByTagName(ButtonBuilder.BUTTON);
    stateReferenced = getTextFromElement(root, ButtonBuilder.TITLE);

    for (int index = 0; index < buttons.getLength(); index += 1) {
      Element buttonNode = (Element) buttons.item(index);
      Button builtButton = buildButtonFromLine(buttonNode);
      foundButtons.add(builtButton);
    }
  }

  /**
   * Sets up new a button's starting x and y coordinates, its text, its width and height, and its
   * action based on a line in a file which specifies this
   *
   * @param buttonNode the node of the file to be parsed for the relevant information
   * @return a fully instantiated Button
   */
  public Button buildButtonFromLine(Element buttonNode) {

    Button output = new Button();
    String text = getTextFromElement(buttonNode, ButtonBuilder.ID);

    output.setId(text);
    output.setText(resourceBundle.getString(text));

    output.setPrefWidth(WIDTH * Double.parseDouble(getTextFromElement(buttonNode, ButtonBuilder.BUTTON_WIDTH)));
    output.setPrefHeight(HEIGHT * Double.parseDouble(getTextFromElement(buttonNode, ButtonBuilder.BUTTON_HEIGHT)));

    output.setLayoutX(WIDTH * Double.parseDouble(getTextFromElement(buttonNode, ButtonBuilder.CENTERX)) -
            output.getPrefWidth() / 2);
    output.setLayoutY(HEIGHT * Double.parseDouble(getTextFromElement(buttonNode, ButtonBuilder.CENTERY)) -
            output.getPrefHeight() / 2);

    output.setOnAction(e -> handler.handlePush(getTextFromElement(buttonNode, ButtonBuilder.METHOD)));
    return output;
  }

  /**
   * Returns the list of buttons that this ButtonBuilder has found from parsing the file
   *
   * @return a list of buttons
   */
  @Override
  public List<Button> getFoundButtons() {
    return foundButtons;
  }
}
