package controller;

import api.controller.IImageBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Is responsible for creating ImageViews from a config file that takes the form XXXX.xml. For
 * instructions on how to write such a config file, as well as an in depth description of the
 * definitions of element tags within existing config files, please refer to the README
 *
 * @author Edem Ahorlu
 */
public class ImageBuilder extends Builder implements IImageBuilder {

  private String stateReferenced;
  private final List<ImageView> foundImages;


  private static final String IMAGE = "image";
  private static final String TITLE = "title";

  private static final String ID = "id";
  private static final String CENTERX = "centerx";
  private static final String CENTERY = "centery";
  private static final String IMAGE_WIDTH = "width";
  private static final String IMAGE_HEIGHT = "height";
  private static final String PATH = "path";
  protected static final String BUILDER_EXCEPTION = "IMAGE_ERROR";

  /**
   * Instantiates a new ImageBuilder object
   *
   * @param w    the WIDTH of the area in which images can be placed
   * @param h    the HEIGHT of the area in which images can be placed
   * @param path the filepath leading to the .xml file containing data on the button
   */
  public ImageBuilder(double w, double h, String path) throws BuilderInstantiationException {
    super(w, h);
    foundImages = new ArrayList<>();
    try {
      makeImages(path);
    } catch (Exception e) {
      throw new BuilderInstantiationException(
          BUILDER_EXCEPTION);
    }
  }


  /**
   * Makes each of the images as specified in a particular file
   *
   * @param xmlPath the file to be read
   */
  @Override
  public void makeImages(String xmlPath)
      throws IOException, ParserConfigurationException, SAXException {
    Element root = buildRoot(xmlPath);

    NodeList imageviews = root.getElementsByTagName(ImageBuilder.IMAGE);
    stateReferenced = getTextFromElement(root, ImageBuilder.TITLE);

    for (int index = 0; index < imageviews.getLength(); index += 1) {
      Element imageNode = (Element) imageviews.item(index);
      ImageView builtImage = buildImageFromLine(imageNode);
      foundImages.add(builtImage);
    }
  }

  /**
   * Sets up new a ImageView's starting x and y coordinates, its text, its width and height, and its
   * action based on a line in a file which specifies this
   *
   * @param imageNode the line of the file to be parsed for the relevant information
   * @return a fully instantiated ImageView
   */
  @Override
  public ImageView buildImageFromLine(Element imageNode) throws FileNotFoundException {

    Image image = new Image(new FileInputStream(
        getTextFromElement(imageNode, ImageBuilder.PATH)));

    ImageView output = new ImageView(image);
    output.setId(getTextFromElement(imageNode, ImageBuilder.ID));

    output.setFitWidth(WIDTH * Double.parseDouble(
        getTextFromElement(imageNode, ImageBuilder.IMAGE_WIDTH)));
    output.setFitHeight(HEIGHT * Double.parseDouble(
        getTextFromElement(imageNode, ImageBuilder.IMAGE_HEIGHT)));

    output.setX(WIDTH * Double.parseDouble(getTextFromElement(imageNode, ImageBuilder.CENTERX)) -
        output.getFitWidth() / 2);
    output.setY(HEIGHT * Double.parseDouble(getTextFromElement(imageNode, ImageBuilder.CENTERY)) -
        output.getFitHeight() / 2);

    return output;
  }

  /**
   * Returns the state that these image files pertain to (i.e. SELECT_SIMULATION, MENU, etc.) so
   * that the handler knows which scene to place the newly generated buttons into
   *
   * @return stateReferenced the state listed at the top of the file
   */
  @Override
  public String getStateReferenced() {
    return stateReferenced;
  }

  /**
   * Returns the list of ImageViews that this ImageBuilder has found from parsing the file
   *
   * @return a list of ImageViews
   */
  @Override
  public List<ImageView> getFoundImages() {
    return foundImages;
  }
}

