package Objects;

/**
 *
 * @author lukasz
 */
public class Point extends GameObject {

    private final PointType type;
    private boolean eaten = false;

    public Point(int posX, int posY, PointType type) {
        super(posX, posY);
        this.type = type;
    }

    public PointType getType() {
        return type;
    }

    public boolean isEaten() {
        return eaten;
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }
    

}
