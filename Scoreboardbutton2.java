import greenfoot.*;

public class Scoreboardbutton2 extends Buttons {
    public void act()  {
        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setWorld(new HighScoreWorld(Game.HELICOPTER_GAME));
        }
    }    
}
