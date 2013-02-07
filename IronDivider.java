import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class IronDivider extends SandbagBag
{
    public IronDivider() {
        super(5, 2);
        getImage().scale(75,50);
    }

    @Override
    public int getCost() {
        return 50;
    }
}
