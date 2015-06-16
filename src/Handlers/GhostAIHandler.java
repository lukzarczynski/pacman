package Handlers;

import Game.GameState;
import Objects.Direction;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author lukasz
 */
public class GhostAIHandler extends AbstractHandler {

    private static final Logger LOG = Logger.getLogger(GhostAIHandler.class.getName());

    public GhostAIHandler(int SQUARE_SIZE, int WINDOW_WIDTH, int WINDOW_HEIGHT) {
        super(SQUARE_SIZE, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    @Override
    void handleData(Graphics2D data, GameState state) {
        state.getGhosts().stream().filter(g -> !g.isEaten()).forEach((g) -> {
            if (g.getField().isCross()) {
                List<Direction> dirs = resolveDirections(state.getPlayer().getX(), state.getPlayer().getY(), g.getX(), g.getY());
                dirs = dirs.stream().filter(d -> g.isDirectionValid(d)).collect(Collectors.toList());
                if (state.isExtraMode()) {
                    Collections.reverse(dirs);
                }
                double d = Math.random();
                if (d < g.getAggression()) {
                    g.setNextDirection(dirs.get(0));
                } else {
                    g.setNextDirection(dirs.get(dirs.size() - 1));
                }
            }
        });

        state.getGhosts().stream().filter(g -> g.isEaten()).forEach((g) -> {
            if (g.getField().isCross()) {
                List<Direction> dirs = resolveDirections(
                        SQUARE_SIZE * state.getGhostBaseField().getColumn() + HALF_SQUARE,
                        SQUARE_SIZE * state.getGhostBaseField().getRow() + HALF_SQUARE,
                        g.getX(),
                        g.getY());
                dirs = dirs.stream().filter(d -> g.isDirectionValid(d)).collect(Collectors.toList());
                g.setNextDirection(dirs.get(0));
            }
        });
    }

    private List<Direction> resolveDirections(double px, double py, double gx, double gy) {
        List<Direction> dirs = new ArrayList<>();
        double deltaX = Math.abs(px - gx);
        double deltaY = Math.abs(py - gy);
        if (py < gy) {
            if (px < gx) {
                if (deltaX < deltaY) {
                    dirs.add(Direction.UP);
                    dirs.add(Direction.LEFT);
                    dirs.add(Direction.RIGHT);
                    dirs.add(Direction.DOWN);
                } else {
                    dirs.add(Direction.LEFT);
                    dirs.add(Direction.UP);
                    dirs.add(Direction.DOWN);
                    dirs.add(Direction.RIGHT);
                }
            } else {
                if (deltaX < deltaY) {
                    dirs.add(Direction.UP);
                    dirs.add(Direction.RIGHT);
                    dirs.add(Direction.LEFT);
                    dirs.add(Direction.DOWN);
                } else {
                    dirs.add(Direction.RIGHT);
                    dirs.add(Direction.UP);
                    dirs.add(Direction.DOWN);
                    dirs.add(Direction.LEFT);
                }
            }
        } else {
            if (px < gx) {
                if (deltaX < deltaY) {
                    dirs.add(Direction.DOWN);
                    dirs.add(Direction.LEFT);
                    dirs.add(Direction.RIGHT);
                    dirs.add(Direction.UP);
                } else {
                    dirs.add(Direction.LEFT);
                    dirs.add(Direction.DOWN);
                    dirs.add(Direction.UP);
                    dirs.add(Direction.RIGHT);
                }
            } else {
                if (deltaX < deltaY) {
                    dirs.add(Direction.DOWN);
                    dirs.add(Direction.RIGHT);
                    dirs.add(Direction.LEFT);
                    dirs.add(Direction.UP);
                } else {
                    dirs.add(Direction.RIGHT);
                    dirs.add(Direction.DOWN);
                    dirs.add(Direction.UP);
                    dirs.add(Direction.LEFT);
                }
            }
        }
        return dirs;
    }

}
