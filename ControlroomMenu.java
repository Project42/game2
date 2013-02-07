import greenfoot.*; 

/**
 * Shows instructions, then starts game
 *
 **/
public class ControlroomMenu extends World
{
    public ControlroomMenu() {
        super(80, 80, 10);
        setBackground("menu_controlroom1.png");
    }

    @Override
    public void act() {
        // Starts game when pressed enter 2 times
        if (Greenfoot.isKeyDown("right")) {
            setBackground("menu_controlroom2.png");
        } else if (Greenfoot.isKeyDown("enter")) {
            Greenfoot.setWorld(new ControlroomWorld());
            return;
        } else if (Greenfoot.isKeyDown("left")) {
            setBackground("menu_controlroom1.png");
            return;
        }
    }
}

