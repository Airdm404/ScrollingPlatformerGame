package model.entity;

public class ZeroGEnemy extends Enemy {

  private final String ID = "Enemy";
  private final double VELOCITY_MULTIPLIER = 0.6;

  public ZeroGEnemy(double x, double y) {
    super(x,y);
  }
  @Override
  public void updatePosition() {
    this.setXVel(this.getXVel()*VELOCITY_MULTIPLIER);
    this.translateHitbox();
  }

  @Override
  public String getType() {
    return ID;
  }
}
