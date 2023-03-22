package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import model.HitBox;
import api.model.entity.IEntity;
import api.view.ITexturer;


/**
 * This class constructs and returns a list of ImageView objects that have location and size to
 * represent game textures. It is primarily called in GameView's update method which uses a Texturer
 * object to update all of the textures displayed to the screen.
 *
 * @author Alex Lu
 */
public class Texturer implements ITexturer {

  private Map<String, ImageView> textureMap;
  private final Group textureGroup;
  private final double WIDTH;
  private final double HEIGHT;
  private final Image MISSING_IMAGE;
  private double numBlocksWide;
  private double numBlocksHigh;
  private final String path;

  private static final String FILEPATH_START = "resources/images/texturefiles/";

  /**
   * Constructs a new Texturer object
   *
   * @param w        the WIDTH of the screen
   * @param h        the HEIGHT of the screen
   * @param filepath the filepath leading to the file containing the texture data that ImageBuilder
   *                 will use to create the ImageViews
   * @param tGroup   the Group that will contain the textures
   */
  public Texturer(double w, double h, String filepath, Group tGroup) {
    WIDTH = w;
    HEIGHT = h;
    textureGroup = tGroup;
    path = filepath;

    MISSING_IMAGE = buildMissingImage(1, 1);

    List<ImageView> viewList = buildViewList(FILEPATH_START + path);
    constructTextureMap(viewList);
  }

  /**
   * Builds a list of ImageViews from a properties file
   *
   * @param propertiesPath the filepath leading to the .properties file
   */
  @Override
  public List<ImageView> buildViewList(String propertiesPath) {
    List<ImageView> viewList = new ArrayList<>();

    try {
      Map<String, String> idToFilepathMap = buildPropertiesMap(propertiesPath);
      for (String id : idToFilepathMap.keySet()) {
        String value = idToFilepathMap.get(id);
        viewList.add(buildImageView(id, value));
      }
      return viewList;
    } catch (Exception e) {
      return new ArrayList<>();
    }
  }

  /**
   * Builds a TreeMap based on a properties file
   *
   * @param propertiesPath the String path leading to the .properties file
   * @return a new Map of properties
   * @throws IOException ioe
   */
  @Override
  public TreeMap buildPropertiesMap(String propertiesPath) throws IOException,
      NullPointerException {
    Properties properties = new Properties();
    InputStream stream = getClass().getClassLoader().getResourceAsStream(propertiesPath);
    properties.load(stream);
    return new TreeMap(properties);
  }

  /**
   * Constructs an ImageView with ID of id and Image from filepath
   *
   * @param id       the id to give the ImageView
   * @param filepath the filepath leading to the correct image
   * @return a new ImageView
   */
  @Override
  public ImageView buildImageView(String id, String filepath) {
    ImageView view = new ImageView();
    view.setId(id);

    try {
      Image image = new Image(new FileInputStream(filepath));
      view.setImage(image);
    } catch (FileNotFoundException fnfe) {
      view.setImage(MISSING_IMAGE);
    }

    return view;
  }

  /**
   * Builds the map String ids -> ImageViews
   *
   * @param viewList the List<ImageView> to build the map on top of
   */
  @Override
  public void constructTextureMap(List<ImageView> viewList) {
    textureMap = new HashMap<>();
    viewList.forEach(view -> textureMap.put(view.getId(), view));
  }

  /**
   * Updates the textures
   *
   * @param entityList the list of Entities to be textured
   */
  @Override
  public void updateTextures(List<IEntity> entityList, double blocksWide, double blocksHigh) {
    numBlocksWide = blocksWide;
    numBlocksHigh = blocksHigh;

    clearCurrentTextures();
    insertNewTextures(entityList);
  }

  /**
   * Clears the textures out from textureGroup
   */
  @Override
  public void clearCurrentTextures() {
    textureGroup.getChildren().clear();
  }

  /**
   * Inserts the new textures into textureGroup
   *
   * @param entityList the entity list for whom we'll be applying the textures
   */
  @Override
  public void insertNewTextures(List<IEntity> entityList) {
    entityList.forEach(entity -> addNewTexture(entity));
  }

  /**
   * Adds a single new texture to the group textureGroup
   *
   * @param currentEntity the IEntity for whom we will be adding the texture
   */
  @Override
  public void addNewTexture(IEntity currentEntity) {
    Image image;
    try {
      image = textureMap.get(currentEntity.getType()).getImage();
    } catch (NullPointerException npe) {
      image = MISSING_IMAGE;
    }

    ImageView view = new ImageView(image);
    placeLocationOfView(currentEntity, view);
    textureGroup.getChildren().add(view);
  }

  /**
   * Sets the ImageView's location in textureGroup to reflect the x and y coordinates in
   * currentEntity
   *
   * @param currentEntity the Entity whose texture is being placed in (x,y) space
   * @param view          the ImageView representing that texture
   */
  @Override
  public void placeLocationOfView(IEntity currentEntity, ImageView view) {
    HitBox hitBox = currentEntity.getHitBox();

    view.setX(hitBox.getXLeft() * WIDTH / numBlocksWide);
    view.setY(hitBox.getYTop() * HEIGHT / numBlocksHigh);
    view.setFitWidth(WIDTH / numBlocksWide);
    view.setFitHeight(HEIGHT / numBlocksHigh);
    view.setId(
        currentEntity.getType() + "x" + (int) hitBox.getXLeft() + "y" + (int) hitBox.getYTop());
  }

  /**
   * Builds an image of width x height filled with black pixels
   *
   * @param height the height of the image to be drawn
   * @param width  the width of the image to be drawn
   * @return an image filled in all block
   */
  @Override
  public Image buildMissingImage(double width, double height) {
    WritableImage filler = new WritableImage((int) width, (int) height);
    PixelWriter writer = filler.getPixelWriter();

    for (int horizontal = 0; horizontal < width; horizontal += 1) {
      for (int vertical = 0; vertical < height; vertical += 1) {
        writer.setColor(horizontal, vertical, Color.BLACK);
      }
    }

    return filler;
  }

  /**
   * For testing - return the String filepath leading to the file generating the textures
   *
   * @return path
   */
  @Override
  public String getPath() {
    return path;
  }
}
