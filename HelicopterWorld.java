import greenfoot.*;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;

public class HelicopterWorld extends World {
    private Helicopter helicopter;
    private Counter scoreCounter;
    private MenuBar menuBar;
    private Wall wall;
    private Direction direction;
    private HeliWater water;
    private HP hp;
    private HeliHealth health1;
    private HeliHealth health2;
    private HeliHealth health3;
    private Health2 health4;
    private Health2 health5;
    private Health2 health6;
    private int healthlost;
    private int healthlostrope;
    private Victim victim;
    private House house;
    private NeedsHelp needshelp;
    GreenfootSound backgroundMusic = new GreenfootSound("background_music.mp3");
    
    public HelicopterWorld() {
        super(80, 80, 10, false);
        setBackground("bg.png");
        backgroundMusic.playLoop();
        for (int i = 1; i < 100; ++i) {
            // Willekeurige huizen en slachtoffers worden in het spel geplaatst.
            Victim victim;
            House house;
            int yvictim = 58;
            int yhouse = 65;
            int x = i * 50 + (-5 + Greenfoot.getRandomNumber(30));
            int randomhouse = (int)(Math.random() * ((9 - 0) + 1));
            int randomvictim = 0 + (int)(Math.random() * ((7 - 0) + 1));
            switch (randomhouse) {
                case 0: house = new House1(); break;
                case 1: house = new House2(); yvictim -= 4; yhouse -= 2; break;
                case 2: house = new House3(); yvictim -= 2; yhouse -= 1; break;
                case 3: house = new House4(); yvictim -= 37; yhouse -= 19; break;
                case 4: house = new House5(); yvictim -= 32; yhouse -= 16; break;
                case 5: house = new House6(); yvictim -= 26; yhouse -= 13; break;
                case 6: house = new House7(); yvictim -= 8; yhouse -= 4; break;
                case 7: house = new House8(); yvictim -= 10; yhouse -= 5; break;
                case 8: house = new House9(); yvictim -= 22; yhouse -= 11; break;
                case 9: house = new House10(); yvictim -= 22; yhouse -= 11; break;
                
                default:
                    // Will never happen.
                    assert false;
                    return;
            }
            addObject(house, x, yhouse);
            
            switch (randomvictim) {
                case 0: victim = new Victim1(); break;
                case 1: victim = new Victim2(); break;
                case 2: victim = new Victim3(); break;
                case 3: victim = new Victim4(); break;
                case 4: victim = new Victim5(); break;
                case 5: victim = new Victim6(); break;
                case 6: victim = new Victim7(); break;
                case 7: victim = new Victim8(); break;

                default:
                    // Will never happen.
                    assert false;
                    return;
            }
            addObject(victim, x, yvictim);
            
            //Uitroeptekens boven de slachtoffers worden geplaatst.
            needshelp = new NeedsHelp();
            addObject(needshelp, x, yvictim-4);
                
        }
        
        //Alle start objecten worden geplaatst.
        helicopter = new Helicopter();
        addObject(helicopter, 40, 10);

        direction = new Direction();
        addObject(direction, 20, 68);

        scoreCounter = new Counter("Score: ");
        addObject(scoreCounter, 6, 73);

        menuBar = new MenuBar();
        addObject(menuBar, 39, 75);

        wall = new Wall();
        addObject(wall, -35, 40);

        water = new HeliWater();
        addObject(water, 40, 70);
        
        hp = new HP();
        addObject(hp, 60, 64);
        
        health1 = new HeliHealth();
        addObject(health1, 60, 73);
        
        health2 = new HeliHealth();
        addObject(health2, 64, 73);
        
        health3 = new HeliHealth();
        addObject(health3, 68, 73);
        
        health4 = new Health2();
        addObject(health4, 60, 77);
        
        health5 = new Health2();
        addObject(health5, 64, 77);
        
        health6 = new Health2();
        addObject(health6, 68, 77);

        setPaintOrder(Counter.class, HP.class, Health2.class, HeliHealth.class, Helicopter.class, MenuBar.class, Wall.class, HeliWater.class, Victim.class, MenuBar.class, House.class, SpeedPowerUp.class, RadiusPowerUp.class, NeedsHelp.class);
    }

    @Override
    public void act() {
        //Check actors
        for (Actor actor : (List<Actor>)getObjects(null)) {
            if (actor == helicopter) continue;
            if (actor == scoreCounter) continue;

            actor.setLocation(actor.getX() + 40 - helicopter.getX(), actor.getY());
        }
        
        //PowerUp spawn
        if (Math.random() < 0.003) {
            Actor powerUp;
            switch (Greenfoot.getRandomNumber(2)) {
                case 0: powerUp = new SpeedPowerUp(); break;
                case 1: powerUp = new RadiusPowerUp(); break;
                default:
                    // Will never happen.
                    assert false;
                    return;
            }
            addObject(powerUp, -50 + Greenfoot.getRandomNumber(100), Greenfoot.getRandomNumber(40));
        }
        
        //Zorg ervoor dat bepaalde objecten een vaste plaats op het scherm hebben.
        helicopter.setLocation(40, helicopter.getY());
        scoreCounter.setLocation(6, 73);
        menuBar.setLocation(39, 75);
        health1.setLocation(60, 73);
        health2.setLocation(64, 73);
        health3.setLocation(68, 73);
        health4.setLocation(60, 77);
        health5.setLocation(64, 77);
        health6.setLocation(68, 77);
        hp.setLocation(60,64);
    
    }
    
    //Return water level
    public int getWaterLevel() {
        return water.getLevel();
    }
    
    //Voegt score toe
    public void addScore(int x) {
        scoreCounter.add(x);
    }
    
    //Proces bij het verliezen van een leven bij helikopter
    public void lostLife() {
        healthlost ++;
        switch (healthlost) {
            case 1: removeObject(health1); break;
            case 2: removeObject(health2); break;
            case 3:
                removeObject(health3);
                gameOver();
                break;
        }
    }
    
    //Proces bij het verliezen van een leven bij ropeman
    public void lostLifeRope() {
        healthlostrope ++;
        switch (healthlostrope) {
            case 1: removeObject(health4); break;
            case 2: removeObject(health5); break;
            case 3:
                removeObject(health6);
                gameOver();
                break;
        }
    }
    
    //Laad gameover scherm
    private void gameOver() {
        Greenfoot.setWorld(new GameOverWorld(Game.HELICOPTER_GAME, scoreCounter.getValue()));
    }
}
