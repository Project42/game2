import greenfoot.*;

/**
 * Dief die probeert te plunderen.
 * 
 * Project 42
 */
public class ControlroomThief extends ControlroomCalamities
{
    ControlroomCatchThief police_catchthief;
    public void addedToWorld(World world)
    {
        setImage("thief.gif");
        setDifficultyScore();
    }
    
    public void act()
    {
        super.act();
        checkClicked(); 
        checkIfIExpire(checkDifficulty());
        
        if (police_catchthief != null) {
            interventionTimer++;
            ControlroomWorld world = (ControlroomWorld)getWorld();
            if (interventionTimer > 200) 
            {
                world.removeObject(this);
                world.removeObject(police_catchthief);
                world.getScoreCounter().add(50);
                world.addConsoleMessage("De boeven zijn gearresteerd.");
            }
        } else {
            interventionTimer = 0;
        }
    }
    
    /** Check if object has been clicked
     * If true, it checks whether the last object clicked was a Firefighter
     * If true, it deletes the Fire object and adds 50 to score
     */
    
    public void checkClicked() {
        if (Greenfoot.mouseClicked(this)) 
        {
            ControlroomWorld world = (ControlroomWorld)getWorld();
            if (world.getSelectedCharacter() == ControlroomWorld.Character.POLICE_CATCHTHIEF) {
                int objectLocationX = getX()+2;
                int objectLocationY = getY();
                world.addObject(police_catchthief = new ControlroomCatchThief(), objectLocationX, objectLocationY);
                world.getPoliceUnits().add(-1);
            }
        }
    }
    
    /** Check whether object has been in the world for too long
     * If true, removes the Fire object and sets the timer back to 0
     * Difficulty argument decreases when progressing in the game, making objects expire faster
     */
    
    public void checkIfIExpire(int difficulty) {
       ControlroomWorld world = (ControlroomWorld)getWorld();

       if (getExpireTimer() > difficulty && police_catchthief == null)
       {
           world.removeObject(this);
           world.loseLife();
           setExpireTimer(0);
       }
    }
}

