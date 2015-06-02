package Objects;

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

}
