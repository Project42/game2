import greenfoot.*;  

public class SpeedPowerUp extends PowerUps implements PowerUp
{
    public void apply(Helicopter helicopter) {
        helicopter.increaseSpeed();
    }

    public void remove(Helicopter helicopter) {
        helicopter.decreaseSpeed();
    }
}