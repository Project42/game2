import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class ControlroomShutOff_Menu extends ControlroomMenuItems
{
    public void addedToWorld(World world)
    {
        setImage("shut_off_street.png");
    }
    
    public void act() 
    {
        // Checks if object itself has been clicked
        checkClicked(2);
    }    
}
