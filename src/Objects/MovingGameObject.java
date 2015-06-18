package Objects;

import Utils.Calculator;

/**
 *
 * @author lukasz
 */
public class MovingGameObject
        extends GameObject {

    private Direction direction;
    private Direction nextDirection;
    private double speed = 3;
    private Field field;
    private final Field baseField;

    private boolean eaten = false;

    public MovingGameObject(int column, int row, Field field) {
        super(column, row);
        direction = Direction.NONE;
        nextDirection = Direction.LEFT;
        this.field = field;
        this.baseField = field;
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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void move(int h, int v) {
        setY(getY() + (v * getSpeed()));
        setX(getX() + (h * getSpeed()));
    }

    public void move(int h, int v, boolean isExtraMode) {
        if (isExtraMode) {
            setY(getY() + (v * (getSpeed() / 2)));
            setX(getX() + (h * (getSpeed() / 2)));
        } else {
            move(h, v);
        }
    }

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

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
        setColumn(field.getColumn());
        setRow(field.getRow());
    }

    public void setFieldIfNear(Field nextField, double distance) {
        if (nextField != null
                && !(nextField.isTeleport()
                && this.getField().isTeleport())
                && Calculator.distance(this, nextField) <= distance) {
            setField(nextField);
        }

    }

    public void resetToBase() {
        setDirection(Direction.NONE);
        setField(baseField);
        setX(0);
    }

    public boolean isEaten() {
        return eaten;
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }

}
