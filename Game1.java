import greenfoot.*;

public class Game1 extends Buttons {
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setSpeed(50);
            Greenfoot.setWorld(new FloodWorldMenu());
        }
    }
}
