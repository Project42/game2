import greenfoot.*;
//Stijgend water
public class HeliWater extends Actor {
    private int acts;
    private int level;

    public HeliWater() {
        getImage().scale(820, 20);
        getImage().setTransparency(100);
        level = 10;
    }
    
    @Override
    public void act() {
        setLocation(40, 70);
        //Water komt steeds hoger
        ++acts;
        if (acts == 10) {
            getImage().scale(820, getImage().getHeight() + 1);
            acts = 0;
            ++level;
        }
    }
    
    public int getLevel() {
        return level;
    }
}
