import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class PowerUps extends Actor
{
    public void act() 
    {
        Actor wallcheck = getOneObjectAtOffset(0, 0, Wall.class);
        
        Helicopter helicopter = new Helicopter();
        
        if (wallcheck != null) {
            getWorld().removeObject(this);
        }
    }    
}
