import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Toont sprite in het menu en is klikbaar
 * 
 */
public class ControlroomExtinguish_Menu extends ControlroomMenuItems
{
    public void addedToWorld(World world)
    {
        setImage("firefighter.png");
    }
    
    public void act() 
    {
        // Checks if object itself has been clicked
        checkClicked(1);
    }    
}
