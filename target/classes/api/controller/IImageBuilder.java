package api.controller;

import javafx.scene.image.ImageView;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * The ImageBuilder interface
 */
public interface IImageBuilder extends IBuilder {

    /**
     * Makes each of the images as specified in a particular file
     *
     * @param xmlPath the file to be read
     */
    void makeImages(String xmlPath)
            throws IOException, ParserConfigurationException, SAXException;

    /**
     * Sets up new a ImageView's starting x and y coordinates, its text, its width and height, and its
     * action based on a line in a file which specifies this
     *
     * @param imageNode the line of the file to be parsed for the relevant information
     * @return a fully instantiated ImageView
     */
    ImageView buildImageFromLine(Element imageNode) throws FileNotFoundException;

    /**
     * Returns the state that these image files pertain to (i.e. SELECT_SIMULATION, MENU, etc.) so
     * that the handler knows which scene to place the newly generated buttons into
     *
     * @return stateReferenced the state listed at the top of the file
     */
    String getStateReferenced();

    /**
     * Returns the list of ImageViews that this ImageBuilder has found from parsing the file
     *
     * @return a list of ImageViews
     */
    List<ImageView> getFoundImages();
}
