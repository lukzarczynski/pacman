package Objects;

import Utils.Calculator;
import java.util.logging.Logger;

/**
 *
 * @author lukasz
 */
public class MovingGameObject
        extends GameObject {

    private static final Logger LOG = Logger.getLogger(MovingGameObject.class.getName());

    private Direction direction = Direction.NONE;
    private Direction nextDirection = Direction.LEFT;
    private final int speed = 3;
    private Field field;
    private int baseFieldColumn;
    private int baseFieldRow;
    private boolean inExtraMode = false;
    private boolean eaten = false;

    public boolean isDirectionValid(Direction dir) {
        if (dir == null) {
            return false;
        }
        switch (dir) {
            case DOWN:
                if (getField().getFieldDown() != null) {
                    return true;
                }
                break;
            case UP:
                if (getField().getFieldUp() != null) {
                    return true;
                }
                break;
            case LEFT:
                if (getField().getFieldLeft() != null) {
                    return true;
                }
                break;
            case RIGHT:
                if (getField().getFieldRight() != null) {
                    return true;
                }
                break;
        }
        return false;
    }

    public void setFieldIfNear(Field nextField, double distance) {
        if (nextField != null
                && !(nextField.isTeleport()
                && this.getField().isTeleport())
                && Calculator.distance(this, nextField) <= distance) {
            setField(nextField);
            setColumn(nextField.getColumn());
            setRow(nextField.getRow());
        }
    }

    public final void setField(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public int getBaseFieldColumn() {
        return baseFieldColumn;
    }

    public int getBaseFieldRow() {
        return baseFieldRow;
    }

    public boolean isEaten() {
        return eaten;
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }

    public boolean isInExtraMode() {
        return inExtraMode;
    }

    public void setInExtraMode(boolean inExtraMode) {
        this.inExtraMode = inExtraMode;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getNextDirection() {
        return nextDirection;
    }

    public void setNextDirection(Direction nextDirection) {
        this.nextDirection = nextDirection;
    }

    public void setBaseFieldColumn(int baseFieldColumn) {
        this.baseFieldColumn = baseFieldColumn;
    }

    public void setBaseFieldRow(int baseFieldRow) {
        this.baseFieldRow = baseFieldRow;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getColumn()).append("|");
        builder.append(getRow()).append("|");
        builder.append(isEaten() ? "Y" : "N").append("|");
        builder.append(isInExtraMode() ? "Y" : "N").append("|");
        builder.append(getDirection().name());
        return builder.toString();
    }

}
