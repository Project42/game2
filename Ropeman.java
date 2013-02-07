import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;

//Reddingswerker die aan touw hangt
public class Ropeman extends Actor {
    private int radius;
    private boolean ropemanDead;
    
    //Stel radius om victims op te pakken in
    public Ropeman() {
        radius = 5;
    }
    
    protected void addedToWorld(World world) { 
        ropemanDead = false;
    }
    
    public void act() {
        //Check voor victims binnen bereik. Voeg punten toe en verwijder victim wanneer gevonden.
        List<Actor> victims = getObjectsInRange(radius, Victim.class);
        for (Actor victim : victims) {
            HelicopterWorld world = (HelicopterWorld)getWorld();
            world.addScore(50);
            int x = getX();
            int y = getY();
            world.removeObject(victim);  
        }
        
        //Check of reddingswerker verdrinkt.
        int waterOffset = 70 - ((HelicopterWorld)getWorld()).getWaterLevel() / 2 / 10;
        if (waterOffset <= getY()) {
            resetRopeman();
            ropemanDead = true;
        }
        
        //Check collision
        if (!ropemanDead) {
            Actor houselinks = getOneObjectAtOffset(1, 0, House.class);
            if (houselinks != null && Greenfoot.isKeyDown("d")) {
                resetRopeman();
            }
            Actor houserechts = getOneObjectAtOffset(-2, 0, House.class);
            if (houserechts != null && Greenfoot.isKeyDown("a")) {
                resetRopeman();
            }
            Actor houseboven = getOneObjectAtOffset(0, 3, House.class);
            if (houseboven != null && Greenfoot.isKeyDown("s")) {
                resetRopeman();
            } 
        }
    }    
    
    public void setRadius(int r) {
        if (r <= 0) r = 1;
        radius = r;
    }
    
    public int getRadius() {
        return radius;
    }
    
    //Reset reddingswerker.
    public void resetRopeman() {
        HelicopterWorld world = (HelicopterWorld)getWorld();
        getWorld().removeObject(this);
        world.lostLifeRope();
    }
    
}
