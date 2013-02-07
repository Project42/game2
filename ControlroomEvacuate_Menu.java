import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Evacuate_Menu here.
 * 
 */
public class ControlroomEvacuate_Menu extends ControlroomMenuItems
{
    public void addedToWorld(World world)
    {
        setImage("evac_vehicle.png");
    }
    
    public void act() 
    {
        // Checks if object itself has been clicked
        checkClicked(3);
    }
}
