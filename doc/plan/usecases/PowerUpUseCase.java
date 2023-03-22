import PowerUpUseCase.PowerUpEntity.ModifierType;

class PowerUpUseCase{

    int playerCounter = 0;

    static void main(String[] args){
        int playerHeight = 2;
        int playerWidth = 2;
        int powerUpHeight = 2;
        int powerUpWidth = 2;
        Position2D playerPosition = new Position2D(5, 5);
        Position2D powerUpPosition = new Position2D(5, 5);
        PlayerEntity playerEntity = new PlayerEntity(playerPosition, playerHeight, playerWidth);
        PowerUpEntity powerUpEntity = new PowerUpEntity(ModifierType.SPEED, 2.0D, powerUpPosition, powerUpHeight, powerUpWidth);
        playerEntity.checkCollision(PowerUpEntity);
    }

    abstract class Position2D{
        double xCoordinate;
        double yCoordinate;

        Position2D(double x, double y){
            this.xCoordinate = x;
            this.yCoordinate = y;
        }

        void getXCoordinate(){
            return this.xCoordinate;
        }

        void getYCoordinate(){
            return this.yCoordinate;
        }

        boolean intersects(Position2D position2D){

        }
    }

    abstract class Entity {
        int height;
        int width;

        Entity(int height, int width) {
            this.height = height;
            this.width = width;
        }

        boolean checkCollision(Entity entity){
            if(this.getPosition().intersects(entity.getPositon())){
                return true;
            }
            return false;
        }

    }

    abstract class MobileEntity extends Entity{
        MobileEntity(int height, int width){
            super(height, width);
        }
    }

    abstract class LivingEntity extends MobileEntity{
        LivingEntity(int height, int width){
            super(height, width);
        }
    }

    class PlayerEntity extends LivingEntity{

        PlayerEntity(int height, int width){
            super(height, width);
        }

        @Override
        boolean checkCollision(Entity entity){
            boolean collidedWith = super.checkCollision(entity);
            if(collidedWith & entity instanceof PowerUpEntity){
                PowerUpEntity powerUpEntity = (PowerUpEntity)entity;
                powerUpEntity.applyModifier(PlayerEntity.this);
            }
            return collidedWith;
        }
    }

    class PowerUpEntity extends Entity{
        ModifierType modifierType;
        double modifierValue;

        PowerUpEntity(ModifierType modifierType, double modifierValue, int height, int width){
            this.modifierType = modifierType;
            this.modifierValue = modifierValue;
            super(height, width);
        }

        void applyModifier(PlayerEntity playerEntity){

        }

        enum ModifierType{
            SPEED,
            DAMAGE,
            SIZE,
            HEALTH
        }
    }

}