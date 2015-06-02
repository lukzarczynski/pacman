package Objects.drawings;

/**
 *
 * @author lukasz
 */
public class Arc extends Line {

    private int startAngle;
    private int arcAngle;

    public Arc(int x1, int y1, int x2, int y2, int startAngle, int arcAngle) {
        super(x1, y1, x2, y2);
        this.startAngle = startAngle;
        this.arcAngle = arcAngle;
    }

    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public int getArcAngle() {
        return arcAngle;
    }

    public void setArcAngle(int arcAngle) {
        this.arcAngle = arcAngle;
    }

}
