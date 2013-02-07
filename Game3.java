import greenfoot.*;

public class Game3 extends Buttons {
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setSpeed(55);
            Greenfoot.setWorld(new ControlroomMenu());
        }
    }
}
