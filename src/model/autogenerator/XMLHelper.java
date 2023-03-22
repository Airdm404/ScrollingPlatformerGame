package model.autogenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class is used by AutoGenerator to parse the xml file it uses for configuration and to
 * translate its XML syntax into a list of ConstantInstruction, RandomInstruction, and primitive
 * values (i.e. 15 for numRows, etc.). AutoGenerator then queries this object to get access to these
 * lists and primitive values for use in generation
 *
 * @author Alex Lu
 */
public class XMLHelper {

  private final Element root;
  private final int numCols;
  private final int numRows;

  private static final String DIMENSIONS = "dimensions";
  private static final String DEFAULT = "base";
  private static final String CONSTANT = "constant";
  private static final String RANDOM = "random";
  private static final String ROWS = "rows";
  private static final String COLS = "columns";
  private static final String ENTITY = "entity";
  private static final String DIRECTION = "direction";
  private static final String ORIGIN_ROW = "originrow";
  private static final String ORIGIN_COL = "origincol";
  private static final String NUM_ROWS = "numrows";
  private static final String NUM_COLS = "numcols";

  /**
   * Large pieces of this code provided by:
   * https://howtodoinjava.com/java/xml/read-xml-dom-parser-example/
   *
   * @param x the String filepath leading to the xml file
   * @throws ParserConfigurationException pce
   * @throws IOException                  ioe
   * @throws SAXException                 se
   */
  public XMLHelper(String x) throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document sourceDoc = builder.parse(new File(x));
    root = sourceDoc.getDocumentElement();

    numCols = getNumCols();
    numRows = getNumRows();
  }

  /**
   * Creates a list of all RandomGeneration elements specified by the XML file and then returns
   * them
   *
   * @return a list of RandomGeneration objects
   */
  public List<RandomGeneration> getRandomGenerations() {
    List<RandomGeneration> randomGenerations = new ArrayList<>();
    NodeList randoms = root.getElementsByTagName(RANDOM);

    for (int index = 0; index < randoms.getLength(); index += 1) {
      Element random = (Element) randoms.item(index);
      RandomGeneration gen = buildRandomGeneration(random);
      randomGenerations.add(gen);
    }

    return randomGenerations;
  }

  /**
   * Builds a RandomGeneration object from a correctly formatted Element constant
   *
   * @param random the Element node to be used to build the RandomGeneration
   * @return a ConstantGeneration object as specified in random
   */
  private RandomGeneration buildRandomGeneration(Element random) {
    String[] randomArgs = new String[7];
    randomArgs[0] = RANDOM;
    randomArgs[1] = getElementByTagName(random, ENTITY).getTextContent();
    randomArgs[2] = getElementByTagName(random, DIRECTION).getTextContent();
    randomArgs[3] = getElementByTagName(random, ORIGIN_ROW).getTextContent();
    randomArgs[4] = getElementByTagName(random, ORIGIN_COL).getTextContent();
    randomArgs[5] = getElementByTagName(random, NUM_ROWS).getTextContent();
    randomArgs[6] = getElementByTagName(random, NUM_COLS).getTextContent();

    return new RandomGeneration(numRows, numCols, randomArgs);
  }

  /**
   * Creates a list of all ConstantGeneration elements specified by the XML file and then returns
   * them
   *
   * @return a list of ConstantGeneration objects
   */
  public List<ConstantGeneration> getConstantGenerations() {
    List<ConstantGeneration> constantGenerations = new ArrayList<>();
    NodeList constants = root.getElementsByTagName(CONSTANT);

    for (int index = 0; index < constants.getLength(); index += 1) {
      Element constant = (Element) constants.item(index);
      ConstantGeneration gen = buildConstantGeneration(constant);
      constantGenerations.add(gen);
    }

    return constantGenerations;
  }

  /**
   * Builds a ConstantGeneration object from a correctly formatted Element constant
   *
   * @param constant the Element node to be used to build the ConstantGeneration
   * @return a ConstantGeneration object as specified in constant
   */
  private ConstantGeneration buildConstantGeneration(Element constant) {
    String[] constantArgs = new String[4];
    constantArgs[0] = CONSTANT;
    constantArgs[1] = getElementByTagName(constant, ENTITY).getTextContent();
    constantArgs[2] = getElementByTagName(constant, ROWS).getTextContent();
    constantArgs[3] = getElementByTagName(constant, COLS).getTextContent();

    return new ConstantGeneration(numRows, numCols, constantArgs);
  }

  /**
   * Determines the default entity value specified by the XML file
   *
   * @return the default entity value
   */
  public String getDefaultEntity() {
    Element defaultE = getElementByTagName(root, DEFAULT);
    return defaultE.getTextContent()
        .replaceAll(" ", "")
        .replaceAll("\n", "");
  }

  /**
   * Returns the number of rows associated with the generation as specified in the XML file
   *
   * @return the number of rows to be generated
   */
  public int getNumRows() {
    Element dimensions = getElementByTagName(root, DIMENSIONS);
    Element rows = getElementByTagName(dimensions, ROWS);
    String rowString = rows.getTextContent();
    return Integer.parseInt(rowString);
  }

  /**
   * Returns the number of cols associated with the generation as specified in the XML file
   *
   * @return the number of cols to be generated
   */
  public int getNumCols() {
    Element dimensions = getElementByTagName(root, DIMENSIONS);
    Element cols = getElementByTagName(dimensions, COLS);
    String colString = cols.getTextContent();
    return Integer.parseInt(colString);
  }

  /**
   * Returns the first instance of a node with tag name tagName in parent
   *
   * @param parent  the parent node
   * @param tagName the String tag name
   * @return the first instance of a node with tagName in parent
   */
  private Element getElementByTagName(Element parent, String tagName) {
    return (Element) parent.getElementsByTagName(tagName).item(0);
  }
}
