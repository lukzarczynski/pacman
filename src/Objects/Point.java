package Objects;

import java.util.Objects;

/**
 *
 * @author lukasz
 */
public class Point extends GameObject {

    private PointType type;
    private boolean eaten = false;

    public PointType getType() {
        return type;
    }

    public boolean isEaten() {
        return eaten;
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }

    public Point clonePoint() {
        return this;
    }

    public void setType(PointType type) {
        this.type = type;
    }

}
