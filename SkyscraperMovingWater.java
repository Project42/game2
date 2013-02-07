import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class SkyscraperMovingWater extends Actor
{
    private int acts;
    private int level;

    public SkyscraperMovingWater() {
        getImage().scale(1, 20);
        getImage().setTransparency(100);
        level = 10;
    }
    
    @Override
    public void act() {
        setLocation(40, 70);
        
        if (++acts == 1) {
            setLevel(level + 2);
        }
    }
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int lvl) {
        getImage().scale(820, lvl);
        acts = 0;
        level = lvl;
    }
}
