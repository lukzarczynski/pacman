package Objects;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author lukasz
 */
public class Player
        extends MovingGameObject {

    private int state = 0;
    private int statedir = 1;

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
        return this.getField().isCross();
    }

    public void move(int h, int v) {
        setY(getY() + (v * getSpeed()));
        setX(getX() + (h * getSpeed()));
        nextState();
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
