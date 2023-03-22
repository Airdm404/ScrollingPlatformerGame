package api.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import api.model.entity.IEntity;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

/**
 * The Texturer interface
 */
public interface ITexturer {

    /**
     * Builds a list of ImageViews from a properties file
     *
     * @param propertiesPath the filepath leading to the .properties file
     */
    List<ImageView> buildViewList(String propertiesPath);

    /**
     * Builds a TreeMap based on a properties file
     *
     * @param propertiesPath the String path leading to the .properties file
     * @return a new Map of properties
     * @throws IOException ioe
     */
    TreeMap buildPropertiesMap(String propertiesPath) throws IOException,
            NullPointerException;

    /**
     * Constructs an ImageView with ID of id and Image from filepath
     *
     * @param id       the id to give the ImageView
     * @param filepath the filepath leading to the correct image
     * @return a new ImageView
     */
    ImageView buildImageView(String id, String filepath);

    /**
     * Builds the map String ids -> ImageViews
     *
     * @param viewList the List<ImageView> to build the map on top of
     */
    void constructTextureMap(List<ImageView> viewList);

    /**
     * Updates the textures
     *
     * @param entityList the list of Entities to be textured
     */
    void updateTextures(List<IEntity> entityList, double blocksWide, double blocksHigh);

    /**
     * Clears the textures out from textureGroup
     */
    void clearCurrentTextures();

    /**
     * Inserts the new textures into textureGroup
     *
     * @param entityList the entity list for whom we'll be applying the textures
     */
    void insertNewTextures(List<IEntity> entityList);

    /**
     * Adds a single new texture to the group textureGroup
     *
     * @param currentEntity the IEntity for whom we will be adding the texture
     */
    void addNewTexture(IEntity currentEntity);

    /**
     * Sets the ImageView's location in textureGroup to reflect the x and y coordinates in
     * currentEntity
     *
     * @param currentEntity the Entity whose texture is being placed in (x,y) space
     * @param view          the ImageView representing that texture
     */
    void placeLocationOfView(IEntity currentEntity, ImageView view);

    /**
     * Builds an image of width x height filled with black pixels
     *
     * @param height the height of the image to be drawn
     * @param width  the width of the image to be drawn
     * @return an image filled in all block
     */
    Image buildMissingImage(double width, double height);

    /**
     * For testing - return the String filepath leading to the file generating the textures
     *
     * @return path
     */
    String getPath();
}
