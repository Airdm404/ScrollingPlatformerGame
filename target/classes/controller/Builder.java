package controller;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import api.controller.IBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * This class is the abstract super class that both ButtonBuilder and ImageBuilder extend. It
 * extracts some shared functionality from both classes (primarily related to the parsing of XML
 * documents that both ButtonBuilder and ImageBuilder read from)
 *
 * @author Alex Lu
 */
public abstract class Builder implements IBuilder {

  protected final double WIDTH;
  protected final double HEIGHT;
  protected String stateReferenced;
  protected final ResourceBundle resourceBundle;

  private static final String EXTENSION = ".English";
  private static final String RESOURCES = "resources/resourcebundles";


  /**
   * Constructs a new Builder object - pass in (w,h) as the width and height of the object. These
   * values will be used to scale button positions and dimensions
   *
   * @param w the absolute width that all element x coordinates and widths will be scaled by
   * @param h the absolute height that all element y coordinates and heights will be scaled by
   */
  public Builder(double w, double h) {
    WIDTH = w;
    HEIGHT = h;
    resourceBundle = ResourceBundle.getBundle(RESOURCES + EXTENSION);
  }

  /**
   * Returns the root element of an xml file indexed by xmlPath
   *
   * @param xmlPath the filepath leading to the xml file
   * @return the root element of an xmlPath
   */
  @Override
  public Element buildRoot(String xmlPath)
      throws IOException, SAXException, ParserConfigurationException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document sourceDoc = builder.parse(new File(xmlPath));
    return sourceDoc.getDocumentElement();
  }

  /**
   * Returns the first instance of a node with tag name tagName in parent
   *
   * @param parent  the parent node
   * @param tagName the String tag name
   * @return the first instance of a node with tagName in parent
   */
  @Override
  public Element getElementByTagName(Element parent, String tagName) {
    return (Element) parent.getElementsByTagName(tagName).item(0);
  }

  /**
   * Gets the text from an element
   *
   * @param parent  the parent node
   * @param tagName the String tag name
   * @return the text on the element
   */
  @Override
  public String getTextFromElement(Element parent, String tagName) {
    return getElementByTagName(parent, tagName).getTextContent();
  }

  /**
   * Returns the state that these button files pertain to (i.e. SELECT_SIMULATION, MENU, etc.) so
   * that the handler knows which scene to place the newly generated buttons into
   *
   * @return stateReferenced the state listed at the top of the file
   */
  @Override
  public String getStateReferenced() {
    return stateReferenced;
  }
}
