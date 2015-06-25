package Objects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lukasz
 */
public class Field
        extends GameObject {

    private Field fieldUp;
    private Field fieldDown;
    private Field fieldLeft;
    private Field fieldRight;
    private Point point;
    private boolean teleport = false;
    private boolean cross = false;
    private boolean ghostBase = false;

    public Field getFieldUp() {
        return fieldUp;
    }

    public void setFieldUp(Field fieldUp) {
        this.fieldUp = fieldUp;
    }

    public Field getFieldDown() {
        return fieldDown;
    }

    public void setFieldDown(Field fieldDown) {
        this.fieldDown = fieldDown;
    }

    public Field getFieldLeft() {
        return fieldLeft;
    }

    public void setFieldLeft(Field fieldLeft) {
        this.fieldLeft = fieldLeft;
    }

    public Field getFieldRight() {
        return fieldRight;
    }

    public void setFieldRight(Field fieldRight) {
        this.fieldRight = fieldRight;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public boolean isTeleport() {
        return teleport;
    }

    public boolean isCross() {
        return cross;
    }

    public void setCross(boolean cross) {
        this.cross = cross;
    }

    public boolean isGhostBase() {
        return ghostBase;
    }

    public void setGhostBase(boolean ghostBase) {
        this.ghostBase = ghostBase;
    }

    public void setTeleport(boolean teleport) {
        this.teleport = teleport;
    }

    public boolean hasPoint() {
        return point != null;
    }

    public List<Field> getFields() {
        List<Field> result = new ArrayList<>();
        if (getFieldDown() != null) {
            result.add(getFieldDown());
        }
        if (getFieldUp() != null) {
            result.add(getFieldUp());
        }
        if (getFieldLeft() != null) {
            result.add(getFieldLeft());
        }
        if (getFieldRight() != null) {
            result.add(getFieldRight());
        }
        return result;
    }

    public Direction getDirectionToField(Field field) {
        if (field.equals(getFieldDown())) {
            return Direction.DOWN;
        }
        if (field.equals(getFieldUp())) {
            return Direction.UP;
        }
        if (field.equals(getFieldLeft())) {
            return Direction.LEFT;
        }
        if (field.equals(getFieldRight())) {
            return Direction.RIGHT;
        } else {
            return Direction.NONE;
        }
    }

    public boolean isDirectionValid(Direction dir) {
        if (dir == null) {
            return false;
        }
        switch (dir) {
            case DOWN:
                if (getFieldDown() != null) {
                    return true;
                }
                break;
            case UP:
                if (getFieldUp() != null) {
                    return true;
                }
                break;
            case LEFT:
                if (getFieldLeft() != null) {
                    return true;
                }
                break;
            case RIGHT:
                if (getFieldRight() != null) {
                    return true;
                }
                break;
        }
        return false;
    }

    public Field getFieldByDirection(Direction dir) {
        if (dir == null) {
            return null;
        }
        switch (dir) {
            case DOWN:
                return getFieldDown();
            case UP:
                return getFieldUp();
            case LEFT:
                return getFieldLeft();
            case RIGHT:
                return getFieldRight();
        }
        return null;
    }

    @Override
    public String toString() {
        return getColumn() + "|" + getRow() + "|" + teleport + "|" + cross + "|" + ghostBase;
    }

}
