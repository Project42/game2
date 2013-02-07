import greenfoot.*;

public class FloodWorld extends World {
    public boolean stopped;
    public SandbagCounter scoreCounter;
    private SandbagPlayer player;
    private SandbagCoins coinCounter;
    private SandbagOverlay overlay;
    private SandbagMuteButton muteButton;

    GreenfootSound backgroundMusic = new GreenfootSound("zeerstoer.mp3");

    public FloodWorld(SandbagPlayer.PlayerType playerType)  {
        super(80, 80, 10);
        backgroundMusic.playLoop();

        setPaintOrder(SandbagMuteButton.class, SandbagOverlay.class, SandbagCounter.class, SandbagCoins.class, SandbagMenuBar.class, SandbagPlayer.class, SandbagBag.class, SandbagCoin.class, SandbagWater.class, SandbagFloodbank.class);

        for(int i=0; i<=80; i++) {
            for(int j=0; j<=30; j++) {

                addObject(new SandbagWater(), i, j);
            }
        }

        for(int i = 0; i <= 80; i++) {
            for(int j = 50; j <= 70; j++) {

                addObject(new SandbagMeadow(), i, j);
            }
        }

       addObject(new SandbagMenuBar(), 39, 75);

        for(int i = 0; i <= 80; i++) {
            for(int j = 30; j <= 50; j++) {

                addObject(new SandbagFloodbank(), i, j);
            }
        }

        addObject(player = SandbagPlayer.createPlayer(playerType), 40, 67);

        scoreCounter = new SandbagCounter("Score: ");
        addObject(scoreCounter, 6, 74);

        coinCounter = new SandbagCoins("Coins: ");
        addObject(coinCounter, 6, 76);

        addObject(muteButton = new SandbagMuteButton(), 75, 75);
        muteButton.registerSound(backgroundMusic);
    }

    public void act(){
        int randomNumber = Greenfoot.getRandomNumber(30);
        if(randomNumber == 0) {
              int randomX = Greenfoot.getRandomNumber(80);
              int randomY = 50 + Greenfoot.getRandomNumber(70 - 50);
              addObject(new SandbagCoin(), randomX, randomY);
        }
        scoreCounter.add(2);

    }

    public SandbagCounter getScoreCounter() {
        return scoreCounter;
    }

    
    public SandbagCoins getCoinCounter() {
        return coinCounter;
    }

    public SandbagPlayer getPlayer() {
        return player;
    }

    public void setOverlayLocation(int x, int y) {
        if (overlay == null) {
            overlay = new SandbagOverlay();
            addObject(overlay, 20, 75);
        }
        overlay.setLocation(x, y);
    }
    
    public SandbagMuteButton getMuteButton() {
        return muteButton;
    }

    public void gameOver() {
        Greenfoot.setWorld(new GameOverWorld(Game.SANDBAG_GAME, scoreCounter.getValue()));
    }
}
