package Utils;

import Objects.Point;
import Objects.PointType;

/**
 *
 * @author lukasz
 */
public class PointBuilder extends AbstractBuilder<Point> {

    @Override
    Point createObject() {
        return new Point();
    }

    public PointBuilder withType(PointType type) {
        this.item.setType(type);
        return this;
    }

}
