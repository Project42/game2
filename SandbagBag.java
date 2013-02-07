import greenfoot.*;
import java.util.List;

public abstract class SandbagBag extends Actor {
    private int weight;
    private int lifetime;

    public enum BagType {
        SANDBAG, GRAVEL_BAG, CEMENT_BAG, WOODEN_DIVIDER, IRON_DIVIDER, CONCRETE_DIVIDER
    }
    
    static SandbagBag createBag(BagType type) {
        switch (type) {
            case SANDBAG: return new Sandbag();
            case GRAVEL_BAG: return new GravelBag();
            case CEMENT_BAG: return new CementBag();
            case WOODEN_DIVIDER: return new WoodenDivider();
            case IRON_DIVIDER: return new IronDivider();
            case CONCRETE_DIVIDER: return new ConcreteDivider();
        }

        assert false;
        return null;
    }

    protected SandbagBag(int weight, int lifetime) {
        this.weight = weight;
        this.lifetime = lifetime;
    }

    public void act() {
        List<Actor> waters = getObjectsInRange(2, SandbagWater.class);
        if (!waters.isEmpty()) {
            --lifetime;
            if (lifetime <= 0) {
                getWorld().removeObject(this);
            }
        }
    }
    
    public abstract int getCost();

    public int getWeight() {
        return weight;
    }

    public BagType getType() {
        if (this.getClass() == Sandbag.class) return BagType.SANDBAG;
        if (this.getClass() == GravelBag.class) return BagType.GRAVEL_BAG;
        if (this.getClass() == CementBag.class) return BagType.CEMENT_BAG;
        if (this.getClass() == WoodenDivider.class) return BagType.WOODEN_DIVIDER;
        if (this.getClass() == IronDivider.class) return BagType.IRON_DIVIDER;
        if (this.getClass() == ConcreteDivider.class) return BagType.CONCRETE_DIVIDER;

        assert false;
        return null;
    }
}
