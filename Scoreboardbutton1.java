import greenfoot.*;

public class Scoreboardbutton1 extends Buttons {
    public void act()  {
        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setWorld(new HighScoreWorld(Game.SANDBAG_GAME));
        }
    }    
}
