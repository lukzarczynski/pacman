package Utils;

import Objects.Direction;
import Objects.Ghost;
import Objects.GhostType;
import java.awt.Color;

/**
 *
 * @author lukasz
 */
public class GhostBuilder extends MovingGameObjectBuilder<Ghost> {

    @Override
    Ghost createObject() {
        return new Ghost();
    }

    public GhostBuilder withType(GhostType type) {
        this.item.setType(type);
        switch (type) {
            case BLUE:
                this.item.setAggression(0.6);
                this.item.setColor(Color.BLUE);
                break;
            case PINK:
                this.item.setAggression(0.5);
                this.item.setColor(Color.PINK);
                break;
            case RED:
                this.item.setAggression(0.8);
                this.item.setColor(Color.RED);
                break;
            case YELLOW:
                this.item.setAggression(0.7);
                this.item.setColor(Color.YELLOW);
                break;
            default:
                this.item.setAggression(0);
                this.item.setColor(null);
        }
        return this;
    }



}
