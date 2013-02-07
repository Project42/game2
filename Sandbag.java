import greenfoot.*;

public class Sandbag extends SandbagBag {
    public Sandbag() {
        super(1, 5);
        getImage().scale(50,50);
    }
    
    @Override
    public int getCost() {
        return 10;
    }
}
