package Objects;

import java.awt.Color;

/**
 *
 * @author lukasz
 */
public class Ghost extends MovingGameObject {

    private GhostType type;
    private Color color;
    private int state = 4;
    private int counter = 0;
    private double aggression;

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

    public void setType(GhostType type) {
        this.type = type;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setAggression(double aggression) {
        this.aggression = aggression;
    }

    public void nextState() {
        if (getState() == 4) {
            setState(3);
        } else {
            setState(4);
        }
        counter = 1;
    }

    public void move(int h, int v) {
        this.counter++;
        if (counter % 8 == 0) {
            nextState();
        }
        if ((isInExtraMode() && counter % 2 == 0) || !isInExtraMode()) {
            setY(getY() + (v * getSpeed()));
            setX(getX() + (h * getSpeed()));
        }
    }

    public double getAggression() {
        return aggression;
    }

    @Override
    public String toString() {
        return super.toString().concat("|").concat(this.color.toString());
    }

}
