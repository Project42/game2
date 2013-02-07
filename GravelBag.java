import greenfoot.*;

public class GravelBag extends SandbagBag {
    public GravelBag() {
        super(2, 6);
        getImage().scale(50,50);
    }
    
    @Override
    public int getCost() {
        return 20;
    }
}
