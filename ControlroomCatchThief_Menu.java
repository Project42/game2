import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CatchThief_Menu here.
 * 
 */
public class ControlroomCatchThief_Menu extends ControlroomMenuItems
{
    public void addedToWorld(World world)
    {
        setImage("arrest.png");
    }
    
    public void act() 
    {
        // Checks if object itself has been clicked
        checkClicked(4);
    }    
}
