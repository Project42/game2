import greenfoot.*;

public class Scoreboardbutton3 extends Buttons {
    public void act()  {
        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setWorld(new HighScoreWorld(Game.CONTROL_ROOM_GAME));
        }
    }    
}
