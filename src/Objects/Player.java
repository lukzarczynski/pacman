package Objects;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author lukasz
 */
public class Player
        extends MovingGameObject {

    private static final Logger LOG = Logger.getLogger(Player.class.getName());

    private int state = 0;
    private int statedir = 1;
    private int lastCrossColumn;
    private int lastCrossRow;

    public int getState() {
        return state;
    }

    public void nextState() {
        this.state += statedir;
        if (state == 6 || state == 0) {
            statedir = -statedir;
        }
    }

    public boolean isAtCross() {
        if (this.getField().isCross()) {
            this.lastCrossColumn = this.getField().getColumn();
            this.lastCrossRow = this.getField().getRow();
        }
        return this.getField().isCross();
    }

    public void move(int h, int v) {
        setY(getY() + (v * getSpeed()));
        setX(getX() + (h * getSpeed()));
        nextState();
    }

    public int getStatedir() {
        return statedir;
    }

    public void setStatedir(int statedir) {
        this.statedir = statedir;
    }

    public int getLastCrossColumn() {
        return lastCrossColumn;
    }

    public void setLastCrossColumn(int lastCrossColumn) {
        this.lastCrossColumn = lastCrossColumn;
    }

    public int getLastCrossRow() {
        return lastCrossRow;
    }

    public void setLastCrossRow(int lastCrossRow) {
        this.lastCrossRow = lastCrossRow;
    }

    public Direction selectRandomMove() {
        List<Direction> dirs = Arrays.asList(Direction.values()).stream().filter(d -> isDirectionValid(d)).collect(Collectors.toList());
        Collections.shuffle(dirs);
        setNextDirection(dirs.get(0));
        return dirs.get(0);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
