package Objects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lukasz
 */
public class Field extends GameObject {

    private Field fieldUp;
    private Field fieldDown;
    private Field fieldLeft;
    private Field fieldRight;
    private Point point;
    private boolean teleport;
    private boolean cross = false;
    private boolean ghostBase;

    public Field(int posX, int posY) {
        super(posX, posY);
        this.teleport = false;
    }

    public Field(int posX, int posY, Point point) {
        super(posX, posY);
        this.teleport = false;
        this.point = point;
    }

    public Field(int posX, int posY, boolean teleport) {
        super(posX, posY);
        this.teleport = teleport;
    }

    public Field(boolean ghostBase, int posX, int posY) {
        super(posX, posY);
        this.ghostBase = ghostBase;
    }

    public Field(int posX, int posY, Point point, boolean teleport) {
        super(posX, posY);
        this.teleport = teleport;
        this.point = point;
    }

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

}
