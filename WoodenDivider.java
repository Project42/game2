import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class WoodenDivider extends SandbagBag
{


    public WoodenDivider() {
        super(4, 1);
        getImage().scale(75,50);
    }

    @Override
    public int getCost() {
        return 40;
    }
}
