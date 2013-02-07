import greenfoot.*;
import java.lang.Object;

public abstract class SandbagPlayer extends Actor {
    private int speed;
    private int initialSpeed;
    private SandbagBag.BagType bagType;

    protected GreenfootImage image1;
    protected GreenfootImage image2;
    protected GreenfootImage image3;
    protected GreenfootImage image4;
    protected GreenfootImage image5;
    protected GreenfootImage image6;
    protected GreenfootImage image7;
    protected GreenfootImage image8;
    
    private static GreenfootSound sandbagSound;
    private static GreenfootSound coinSound;

    public enum PlayerType {
        CITIZEN, POLICE, GENIUS
    };

    protected SandbagPlayer(int speed) {
        this.initialSpeed = speed;
        this.speed = speed;
        setBagType(SandbagBag.BagType.SANDBAG);
    }

    static public SandbagPlayer createPlayer(PlayerType type) {
        switch (type) {
            case CITIZEN: return new Citizen();
            case POLICE: return new Police();
            case GENIUS: return new Genius();
        }

        assert false;
        return null;
    }

    @Override
    public void act() {
        if (speed / 4 <= 0) speed = 4;

        if (Greenfoot.isKeyDown("w")) {
            move(0, -speed / 4);
            switchImageStraight();
        }

        if (Greenfoot.isKeyDown("s")) {
            move(0, +speed / 4);
            switchImageBack();
        }

        if (Greenfoot.isKeyDown("a")) {
            move(-speed / 4, 0);
            switchImageLeft();
        }

        if (Greenfoot.isKeyDown("d")) {
            move(+speed / 4, 0);
            switchImageRight();
        }

        if (Greenfoot.isKeyDown("1")) {
            setBagType(SandbagBag.BagType.SANDBAG);
            ((FloodWorld)getWorld()).setOverlayLocation(12, 75);
        }

        if (Greenfoot.isKeyDown("2")) {
            setBagType(SandbagBag.BagType.GRAVEL_BAG);
            ((FloodWorld)getWorld()).setOverlayLocation(23, 75);
        }

        if (Greenfoot.isKeyDown("3")) {
            setBagType(SandbagBag.BagType.CEMENT_BAG);
            ((FloodWorld)getWorld()).setOverlayLocation(34, 75);
        }

        if (Greenfoot.isKeyDown("4")) {
            setBagType(SandbagBag.BagType.WOODEN_DIVIDER);
            ((FloodWorld)getWorld()).setOverlayLocation(45, 75);
        }

        if (Greenfoot.isKeyDown("5")) {
            setBagType(SandbagBag.BagType.IRON_DIVIDER);
            ((FloodWorld)getWorld()).setOverlayLocation(56, 75);
        }

        if (Greenfoot.isKeyDown("6")) {
            setBagType(SandbagBag.BagType.CONCRETE_DIVIDER);
            ((FloodWorld)getWorld()).setOverlayLocation(67, 75);
        }

        if(Greenfoot.mouseClicked(null)) {
            if(((FloodWorld)getWorld()).backgroundMusic.getVolume() == 100 &&(getX() == 78) &&(getY() == 75)||(getX() == 77) &&(getY() == 75)){
               ((FloodWorld)getWorld()).backgroundMusic.setVolume(0);
            }
           
            else if(((FloodWorld)getWorld()).backgroundMusic.getVolume() == 0 &&(getX() == 78) &&(getY() == 75)||(getX() == 77) &&(getY() == 75)){
               ((FloodWorld)getWorld()).backgroundMusic.setVolume(100);
            }
            
            else if((getX() == 78) &&(getY() == 78)||(getX() == 77) &&(getY() == 78)){
               Greenfoot.stop();
            }
            
            else{
            SandbagBag bag = SandbagBag.createBag(bagType);
            if(bag.getCost() <= ((FloodWorld)getWorld()).getCoinCounter().coinValue) {
                MouseInfo mouse = Greenfoot.getMouseInfo();
                ((FloodWorld)getWorld()).getCoinCounter().remove(bag.getCost());
                getWorld().addObject(bag, mouse.getX(), (mouse.getY()));
                
                if (sandbagSound == null) {
                    sandbagSound = new GreenfootSound("sandbag.wav");
                    ((FloodWorld)getWorld()).getMuteButton().registerSound(sandbagSound);
                }
                sandbagSound.play();
            }
        }
            
        }

        Actor water = getOneObjectAtOffset(0, -1, SandbagWater.class);
        if (water != null) {
            switchImageBack();
            move(0, 1);
            if (Greenfoot.isKeyDown("w")) {
                move(0, 1);
            }
        }

        Actor coin = getOneIntersectingObject(SandbagCoin.class);

        if (coin != null) {        
            if (coinSound == null) {
                coinSound = new GreenfootSound("Coin.wav");
                ((FloodWorld)getWorld()).getMuteButton().registerSound(coinSound);
            }
            coinSound.setVolume(75); 
            coinSound.play();
            
            getWorld().removeObject(coin);
            ((FloodWorld)getWorld()).getCoinCounter().add(10);
        }
    }

    private void move(int dx, int dy) {
        if(getY() > 69) {
            setLocation(getX() + dx, 69);
            return;
        }

        setLocation(getX() + dx, getY() + dy);
    }

    public void carryBag(SandbagBag bag) {
        if (bag.getWorld() != null) bag.getWorld().removeObject(bag);
        speed = initialSpeed - bag.getWeight();
        bagType = bag.getType();
    }

    public void setBagType(SandbagBag.BagType type) {
        carryBag(SandbagBag.createBag(type));
    }

    protected void switchImageLeft()
    {
        if (getImage() == image1)
        {
            setImage(image2);
        }
        else
        {
            setImage(image1);
        }
    }

    protected void switchImageRight()
    {
        if (getImage() == image3)
        {
            setImage(image4);
        }
        else
        {
            setImage(image3);
        }
    }

    protected void switchImageStraight()
    {
        if (getImage() == image5)
        {
            setImage(image6);
        }
        else
        {
            setImage(image5);
        }
    }

    protected void switchImageBack()
    {
        if (getImage() == image7)
        {
            setImage(image8);
        }
        else
        {
            setImage(image7);
        }
    }
}
