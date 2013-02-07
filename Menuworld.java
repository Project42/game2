import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Menuworld here.
 * 
 * @author (Levi Cupedo) 
 * @version (0.001)
 */
public class Menuworld extends World
{

    /**
     * Constructor for objects of class Menuworld.
     * 
     */
    public Menuworld()
    {    
        // Create a new world with 800x800 cells with a cell size of 1x1 pixels.
        super(160, 160, 5);
        setBackground("GameMenu.png");
        
        // Add things to menu
        addObject(new Game1(), 40, 70);
        addObject(new Game2(), 120, 70);
        addObject(new Game3(), 40, 125);
        addObject(new Game4(), 120, 125);
        addObject(new Scoreboardbutton1(), 40, 80);
        addObject(new Scoreboardbutton2(), 120, 80);
        addObject(new Scoreboardbutton3(), 40, 135);
        addObject(new Scoreboardbutton4(), 120, 135);
        
        //images of games
        addObject(new GameImage1(), 40, 50);
        addObject(new GameImage2(), 120, 50);
        addObject(new GameImage3(), 40, 105);
        addObject(new GameImage4(), 120, 105);
    }
    
}
