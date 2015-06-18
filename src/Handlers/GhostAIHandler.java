package Handlers;

import Game.GameState;
import Objects.Direction;
import Utils.Dijsktra;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author lukasz
 */
public class GhostAIHandler
        extends AbstractHandler {

    @Override
    void drawData(Graphics2D data, GameState state) {
//        state.getGhosts().stream().forEach((g) -> {
//            data.setColor(g.getColor());
//            List<Field> fields = Dijsktra.getFields(g.getField(), state.getGhostBaseField());
//            fields.stream().forEach(f -> {
//                data.drawOval(
//                        (int) f.getX() - state.getHALF_SQUARE(),
//                        (int) f.getY() - state.getHALF_SQUARE(),
//                        state.getSQUARE_SIZE(), state.getSQUARE_SIZE());
//            });
//        });
    }

    @Override
    void behaveData(GameState state) {
        state.getGhosts().stream().filter(g -> !g.isEaten()).forEach((g) -> {
            if (g.getField().isCross()) {
                List<Direction> dirs = Dijsktra.getDirections(g.getField(), state.getPlayer().getField());
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
                List<Direction> dirs = Dijsktra.getDirections(g.getField(), state.getGhostBaseField());
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
