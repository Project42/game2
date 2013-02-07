import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class RadiusPowerUp extends PowerUps implements PowerUp
{
    public void apply(Helicopter helicopter) {
        helicopter.setRadius(10);
    }

    public void remove(Helicopter helicopter) {
        helicopter.setRadius(3);
    }  
}