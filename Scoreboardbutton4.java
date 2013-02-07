import greenfoot.*;

public class Scoreboardbutton4 extends Buttons {
    public void act()  {
        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setWorld(new HighScoreWorld(Game.SKYSCRAPER_GAME));
        }
    }    
}
