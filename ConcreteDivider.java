import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class ConcreteDivider extends SandbagBag
{

    public ConcreteDivider() {
        super(6, 3);
        getImage().scale(75,50);
    }
    
    @Override
    public int getCost() {
        return 60;
    }
}
