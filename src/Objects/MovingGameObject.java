package Objects;

/**
 *
 * @author lukasz
 */
public class MovingGameObject extends GameObject {

    private Direction direction;
    private Direction nextDirection;
    private double speed = 1.5;
    private Field field;

    public MovingGameObject(int column, int row, Field field) {
        super(column, row);
        direction = Direction.LEFT;
        nextDirection = Direction.LEFT;
        this.field = field;
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

    public boolean isDirectionValid(Direction dir) {
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

}
