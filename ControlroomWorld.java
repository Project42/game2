import greenfoot.*;

/**
 * Dit is de kamer waarin allerlei calamiteiten ontstaan en vervolgens
 * geïnterveniëerd moeten worden door de speler.
 *
 * Project 42
 * 0.80
 */

public class ControlroomWorld extends World
{
    // Controllers for objects
    private ControlroomScore scoreCounter;
    private ControlroomPoliceUnits policeUnitsCounter;
    private ControlroomFirefighterUnits firefighterUnitsCounter;
    private ControlroomNumberOfDeaths numberOfDeathsCounter;
    private ControlroomNumberOfSaved numberOfSavedCounter;
    private ControlroomExtinguish_Menu FirefighterMenu;
    private ControlroomShutOff_Menu ShutOffMenu;
    private ControlroomEvacuate_Menu EvacuateMenu;
    private ControlroomCatchThief_Menu CatchThiefMenu;
    private ControlroomConsole console;    
    private ControlroomLifes lifes;
    
    Character selectedCharacter;
    
    // Variables for all counters
    private int PoliceUnits;
    private int FirefighterUnits;
    private int spawnTimer = 0;
    
    // Variables for spawnlocation
    private int spawnLocationX = 0;
    private int spawnLocationY = 0;
    
    // Variable for score
    public int currentScore = 0;

    // References for Life objects
    private ControlroomLife life1;
    private ControlroomLife life2;
    private ControlroomLife life3;

    // Variable for amount of lifes
    private int countLifes = 4;
    
    // Sets backgroundmusic  
    GreenfootSound backgroundMusic = new GreenfootSound("background_music-ck.mp3");

    public enum Character {
        FIREFIGHTER,
        POLICE_SHUTOFF,
        POLICE_CATCHTHIEF,
        POLICE_EVACUATE,
    }
    
    // Constructor
    public ControlroomWorld()
    {
        super(80,80,10);
        setBackground("background-ck.png");
        backgroundMusic.playLoop();

        // Add interface
        addObject(new ControlroomMenuBar(), 39, 75);
        scoreCounter = new ControlroomScore("Score: ");
        policeUnitsCounter = new ControlroomPoliceUnits("Politie-eenheden: ");
        firefighterUnitsCounter = new ControlroomFirefighterUnits("Brandweer-eenheden:   ");
        numberOfDeathsCounter = new ControlroomNumberOfDeaths("Aantal gewonde mensen:  ");
        numberOfSavedCounter = new ControlroomNumberOfSaved("Aantal geredde mensen:  ");
        addObject(numberOfDeathsCounter, 15, 59);
        addObject(numberOfSavedCounter, 15, 61);
        addObject(scoreCounter, 6, 74);
        addObject(policeUnitsCounter, 12, 46);
        addObject(firefighterUnitsCounter, 14, 48);
        addObject(console = new ControlroomConsole(), 58, 56);
        addObject(life1 = new ControlroomLife(), 73, 75);
        addObject(life2 = new ControlroomLife(), 69, 75);
        addObject(life3 = new ControlroomLife(), 65, 75);
        addObject(FirefighterMenu = new ControlroomExtinguish_Menu(), 20, 75);
        addObject(ShutOffMenu = new ControlroomShutOff_Menu(), 30, 75);
        addObject(CatchThiefMenu = new ControlroomCatchThief_Menu(), 40, 75);
        addObject(EvacuateMenu = new ControlroomEvacuate_Menu(), 50, 75);
        addObject(lifes = new ControlroomLifes(), 68, 65);
    }

    /** Checks score to set difficulty over the game */
    public void checkScore(int score) {
        if (score < 300) {
            spawnSomewhere(400);
        }
        else if (score >= 300) {
            spawnSomewhere(350);
        }
        else if (score >= 700) {
            spawnSomewhere(300);
        }
        else if (score >= 1200) {
            spawnSomewhere(275);
        }
        else if (score >= 1700) {
            spawnSomewhere(250);
        }
        else if (score >= 2300) {
            spawnSomewhere(225);
        }
        else if (score >= 3000) {
            spawnSomewhere(200);
        }
        else if (score >= 3800) {
            spawnSomewhere(150);
        }
        else if (score >= 4700) {
            spawnSomewhere(100);
        }
        else {
            spawnSomewhere(50);
        }
    }

    // Random spawn and adds timer
    // Adds policeunits and firefighterunits over time
    public void act() {
        spawnTimer++;
        checkScore(scoreCounter.getValue());
        setCurrentScore();
        
        if (PoliceUnits == 300) {
            getPoliceUnits().add(1);
            PoliceUnits = 0;
        } else {
            ++PoliceUnits;
        }
        if (FirefighterUnits == 800) {
            getFirefighterUnits().add(1);
            FirefighterUnits = 0;
        } else {
            ++FirefighterUnits;
        }
    }

    /** Sets a random location */
    public void spawnLocation() {
        spawnLocationX = (int)(Math.random()*((64-0)+12));
        spawnLocationY = (int)(Math.random()*((31-0)+12));

        if (getObjectsAt(spawnLocationX,spawnLocationY,ControlroomCalamities.class).isEmpty())
        {
            if(spawnLocationX > 3 && spawnLocationY > 5)
            {
                    chooseObject((int)(Math.random()*((4-0)+1)));
            }
         }
    }

    /** Creates a random new object on a random location, adds console message */
    public void chooseObject (int x) {
        if (x==1)
        {
            addObject(new ControlroomFire(), spawnLocationX, spawnLocationY);
            addConsoleMessage("Er is brand ontstaan!");
        }

        else if (x==2)
        {
            addObject(new ControlroomVictims(), spawnLocationX, spawnLocationY);
            addConsoleMessage("Mensen moeten geëvacueerd worden!");
        }
        else if (x==3)
        {
            addObject(new ControlroomFloodStreet(), spawnLocationX, spawnLocationY);
            addConsoleMessage("Een straat is overstroomd!");
        }
        else if (x==4)
        {
            addObject(new ControlroomThief(),  spawnLocationX, spawnLocationY);
            addConsoleMessage("Er wordt geplunderd!");
        }
        setSpawnTimer(0);
    }


    /** Checks timer and spawns */
    public void spawnSomewhere(int difficulty) {
        if (getSpawnTimer()>difficulty)
        {
            spawnLocation();
        }
    }

    /** Deletes a life object after a miss */
    public void loseLife() {
        countLifes--;
        if (countLifes == 3)
        {
           removeObject(life3);
        }

        else if (countLifes == 2)
        {
            removeObject(life2);
        }

        else if (countLifes == 1)
        {
            Greenfoot.setWorld(new GameOverWorld(Game.CONTROL_ROOM_GAME, scoreCounter.getValue()));
        }
    }
    
    // Method for adding messages to console in the game interface
    public void addConsoleMessage(String message) {
        console.addMessage(message);
    }
    
    // Getter and setter Character
    public void setSelectedCharacter(Character character) {
        selectedCharacter = character;
    }

    public Character getSelectedCharacter() {
        return selectedCharacter;
    }
    
    // Getter scoreCounter
    public ControlroomScore getScoreCounter() {
        return scoreCounter;
    }
    
    // Setter currentScore
    public void setCurrentScore() {
        currentScore = scoreCounter.getValue();
    }
    
    // Getter and setter spawnTimer
    public void setSpawnTimer(int newSpawnTimer) {
        spawnTimer = newSpawnTimer;
    }

    public int getSpawnTimer() {
       return spawnTimer;
    }

    // Getters units counters
    public ControlroomPoliceUnits getPoliceUnits() {
        return policeUnitsCounter;
    }

    public ControlroomFirefighterUnits getFirefighterUnits() {
        return firefighterUnitsCounter;
    }

    // Getters statistics counters
    public ControlroomNumberOfDeaths getNumberOfDeathsCounter() {
        return numberOfDeathsCounter;
    }

    public ControlroomNumberOfSaved getNumberOfSavedCounter() {
        return numberOfSavedCounter;
    }

    // Getters menuobjects
    public ControlroomExtinguish_Menu getExtinguishMenu() {
        return FirefighterMenu;
    }

    public ControlroomShutOff_Menu getShutOffMenu() {
        return ShutOffMenu;
    }

    public ControlroomEvacuate_Menu getEvacuateMenu() {
        return EvacuateMenu;
    }

    public ControlroomCatchThief_Menu getCatchThiefMenu() {
        return CatchThiefMenu;
    }
}
