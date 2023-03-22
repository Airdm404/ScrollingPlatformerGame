package api.controller;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * The builder interface
 */
public interface IBuilder {

    /**
     * Returns the root element of an xml file indexed by xmlPath
     *
     * @param xmlPath the filepath leading to the xml file
     * @return the root element of an xmlPath
     */
    Element buildRoot(String xmlPath)
        throws IOException, SAXException, ParserConfigurationException;

    /**
     * Returns the first instance of a node with tag name tagName in parent
     *
     * @param parent  the parent node
     * @param tagName the String tag name
     * @return the first instance of a node with tagName in parent
     */
    Element getElementByTagName(Element parent, String tagName);

    /**
     * Gets the text from an element
     *
     * @param parent  the parent node
     * @param tagName the String tag name
     * @return the text on the element
     */
    String getTextFromElement(Element parent, String tagName);

    /**
     * Returns the state that these button files pertain to (i.e. SELECT_SIMULATION, MENU, etc.) so
     * that the handler knows which scene to place the newly generated buttons into
     *
     * @return stateReferenced the state listed at the top of the file
     */
    String getStateReferenced();
}
