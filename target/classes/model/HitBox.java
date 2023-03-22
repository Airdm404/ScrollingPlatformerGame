package model;

import model.collision.CollisionDirections;
import model.collision.Direction;
import api.model.IHitBox;

/**
 * A HitBox class for the entities.
 * Essentially a non-JavaFX version of a Rectangle.
 * It is also capable of detecting collisions with other HitBoxes,
 * and returning the direction(s) of the collision.
 *
 * This is used by all entities for collision checking and to store position.
 *
 * @author Ryan Krakower
 */

public class HitBox implements IHitBox {

  public static final double MAX_SIDE_INTERSECT = 0.5;
  public static final double MAX_TOP_INTERSECT = 0.35;
  public static final double MAX_BOTTOM_INTERSECT = 1 - MAX_TOP_INTERSECT;
  public static final double CORNER_GLITCH_AVOIDANCE_OFFSET = 0.005;
  public static final int X_SIZE = 1;
  public static final int Y_SIZE = 1;
  double xLeft;
  double yTop;
  int xSize;
  int ySize;

  /**
   * The HitBox constructor takes in a top coordinate and a
   * left coordinate. The coordinates system
   * that HitBox uses goes from left to right and
   * from top to bottom, just like JavaFX. It
   * calculates its right and bottom coordinates
   * by storing its size. In our project as it currently is,
   * all HitBoxes have the same size.
   *
   * @param xLeft left coordinate of hitBox
   * @param yTop top coordinate of hitBox
   */
  public HitBox(double xLeft, double yTop){
    this.xLeft = xLeft;
    this.yTop = yTop;
    this.xSize = X_SIZE;
    this.ySize = Y_SIZE;
  }

  /**
   * This alternative constructor could be used to make HitBoxes of
   * different sizes, but we do not use this in our project.
   * @param xLeft left coordinate of hitBox
   * @param yTop top coordinate of hitBox
   * @param xSize size of hitBox in the x direction
   * @param ySize size of hitBox in the y direction
   */
  public HitBox(double xLeft, double yTop, int xSize, int ySize) {
    this.xLeft = xLeft;
    this.xSize = xSize;
    this.yTop = yTop;
    this.ySize = ySize;
  }

  /**
   * Getter for XLeft
   * @return xLeft
   */
  @Override
  public double getXLeft() {
    return xLeft;
  }

  /**
   * Setter for XSize
   * @return xSize
   */
  @Override
  public int getXSize() {
    return xSize;
  }

  /**
   * Getter for YSize
   * @return ySize
   */
  @Override
  public int getYSize() {
    return ySize;
  }

  /**
   * Getter for XRight (note that xRight is not stored internally)
   * @return xRight
   */
  @Override
  public double getXRight() {
    return xLeft + xSize;
  }

  /**
   * Getter for yTop
   * @return yTop
   */
  @Override
  public double getYTop() {
    return yTop;
  }

  /**
   * Setter for yTop
   * @param yTop y top coordinate
   */
  @Override
  public void setYTop(double yTop) {
    this.yTop = yTop;
  }

  /**
   * Setter for yBottom (note that it actually sets yTop)
   * @param yBottom y bottom coordinate
   */
  @Override
  public void setYBottom(double yBottom) {
    this.yTop = yBottom - ySize;
  }

  /**
   * Setter for xRight (note that it actually sets xLeft)
   * @param xRight x right coordinate
   */
  @Override
  public void setXRight(double xRight) {
    this.xLeft = xRight - xSize;
  }

  /**
   * Setter for xLeft
   * @param xLeft x left coordinate
   */
  @Override
  public void setXLeft(double xLeft) {
    this.xLeft = xLeft;
  }

  /**
   * Getter for yBottom (note that yBottom is not stored internally)
   * @return yBottom
   */
  @Override
  public double getYBottom() {
    return yTop + ySize;
  }

  /**
   * Translates the HitBox in the x direction by shifting its x coordinate by a given amount
   * @param deltaX change in x (positive is right, negative is left)
   */
  @Override
  public void translateX(double deltaX) {
    xLeft += deltaX;
  }

  /**
   * Translates the HitBox in the y direction by shifting its x coordinate by a given amount
   * @param deltaY change in y (positive is down, negative is up)
   */
  @Override
  public void translateY(double deltaY) {
    yTop += deltaY;
  }

  /**
   * This method calculates a collision with another HitBox by determining whether
   * the two HitBoxes' coordinates overlap.
   * If there is a collision, it determines the direction(s) of the collision by figuring out
   * where the second HitBox is located in relation to the first.
   * It will not detect a collision if the two HitBoxes are just touching (i.e. if xRight of the first
   * is 5, and xLeft of the second is also 5). It will only detect a collision if they overlap by at least
   * the constant CORNER_GLITCH_AVOIDANCE_OFFSET, which should be a small number.
   *
   * @param otherBox the HitBox of the other Entity.
   * @return A CollisionDirections object. This is essentially a glorified list of Directions enums,
   * containing BOTTOM, TOP, LEFT, and/or RIGHT, depending on how the second HitBox collides with the first.
   * If there is no collision, an empty CollisionDirections object is returned.
   */
  @Override
  public CollisionDirections getCollisionDirections(HitBox otherBox) {
    CollisionDirections directions = new CollisionDirections();
    double xRight = xLeft + xSize;
    double yBottom = yTop + ySize;
    if (!((xRight > otherBox.getXLeft() && xLeft < otherBox.getXRight()) &&
        (yBottom > otherBox.getYTop() && yTop < otherBox.getYBottom()))) {
      return directions;
    }

      if (between(yBottom - otherBox.getYTop(), CORNER_GLITCH_AVOIDANCE_OFFSET, MAX_BOTTOM_INTERSECT)) {
        directions.add(Direction.BOTTOM);
      }
      if (between(otherBox.getYBottom() - yTop, CORNER_GLITCH_AVOIDANCE_OFFSET, MAX_TOP_INTERSECT)) {
        directions.add(Direction.TOP);
      }
      if (between(xRight - otherBox.getXLeft(), CORNER_GLITCH_AVOIDANCE_OFFSET, MAX_SIDE_INTERSECT)) {
        directions.add(Direction.RIGHT);
      }
      if (between(otherBox.getXRight() - xLeft, CORNER_GLITCH_AVOIDANCE_OFFSET, MAX_SIDE_INTERSECT))  {
        directions.add(Direction.LEFT);
      }
    return directions;
  }

}
