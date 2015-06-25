package Handlers;

import Game.GameState;
import Objects.Direction;
import Objects.Field;
import Utils.Dijsktra;
import java.awt.Graphics2D;
import java.util.Arrays;
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
                List<Direction> dirs;
                double rand = Math.random();
                if (g.isInExtraMode() || rand >= g.getAggression()) {
                    dirs = Arrays.asList(Direction.values());
                    dirs = dirs.stream().filter(d -> g.isDirectionValid(d)).collect(Collectors.toList());
                    Collections.shuffle(dirs);
                    g.setNextDirection(dirs.get(0));
                } else {
                    dirs = Dijsktra.getDirections(g.getField(), state.getPlayer().getField());
//                    dirs = dirs.stream().filter(d -> g.isDirectionValid(d)).collect(Collectors.toList());
                    g.setNextDirection(dirs.isEmpty() ? Direction.NONE : dirs.get(0));
                }
            }
        });

        state.getGhosts().stream().filter(g -> g.isEaten()).forEach((g) -> {
            if (g.getField().isCross()) {
                List<Direction> dirs = Dijsktra.getDirections(g.getField(), state.getGhostBaseField());
                g.setNextDirection(dirs.isEmpty() ? Direction.NONE : dirs.get(0));
            }
        });
    }

}
