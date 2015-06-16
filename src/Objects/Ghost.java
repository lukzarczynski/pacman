package Objects;

import java.awt.Color;

/**
 *
 * @author lukasz
 */
public class Ghost extends MovingGameObject {

    private final GhostType type;
    private final Color color;
    private int state = 4;
    private final double aggression;

    public Ghost(GhostType type, int posX, int posY, Field field) {
        super(posX, posY, field);
        this.type = type;
        switch (type) {
            case BLUE:
                this.aggression = 0.6;
                this.color = Color.BLUE;
                break;
            case PINK:
                this.aggression = 0.5;
                this.color = Color.PINK;
                break;
            case RED:
                this.aggression = 0.8;
                this.color = Color.RED;
                break;
            case YELLOW:
                this.aggression = 0.7;
                this.color = Color.YELLOW;
                break;
            default:
                this.aggression = 0;
                this.color = null;
        }
    }

    public GhostType getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void nextState() {
        if (getState() == 4) {
            setState(3);
        } else {
            setState(4);
        }
    }

    public double getAggression() {
        return aggression;
    }

}
