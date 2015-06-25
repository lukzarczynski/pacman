package Utils;

import Objects.Field;
import Objects.Ghost;
import Objects.Player;
import Objects.Point;

/**
 *
 * @author lukasz
 */
public class FieldBuilder extends AbstractBuilder<Field> {

    public FieldBuilder withTeleport(boolean teleport) {
        this.item.setTeleport(teleport);
        return this;
    }

    public FieldBuilder withCross(boolean cross) {
        this.item.setCross(cross);
        return this;
    }

    public FieldBuilder withGhostBase(boolean ghostBase) {
        this.item.setGhostBase(ghostBase);
        return this;
    }

    public FieldBuilder withPoint(Point point) {
        this.item.setPoint(point);
        return this;
    }

    @Override
    Field createObject() {
        return new Field();
    }

}
