import greenfoot.*;
import java.util.*;

public class Victim extends Actor {
    @Override
    public void act() {
        int waterOffset = 70 - ((HelicopterWorld)getWorld()).getWaterLevel() / 2 / 10;
        if (waterOffset <= getY()) {
            getWorld().removeObject(this);
        }
    }
}