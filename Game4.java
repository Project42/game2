import greenfoot.*;

public class Game4 extends Buttons {
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setSpeed(40);
            Greenfoot.setWorld(new SkyscraperWorldMenu());
        }
    }
}
