package Handlers;

import Game.GameState;
import Objects.Direction;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author lukasz
 */
public class GhostAIHandler extends AbstractHandler {

    public GhostAIHandler(int SQUARE_SIZE, int WINDOW_WIDTH, int WINDOW_HEIGHT) {
        super(SQUARE_SIZE, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    @Override
    void handleData(Graphics2D data, GameState state) {
        state.getGhosts().stream()
                .forEach((g) -> {
                    if (g.getField().isCross()) {
                        List<Direction> dirs = Arrays.asList(Direction.values());
                        Collections.shuffle(dirs, new Random(System.nanoTime()));
                        g.setNextDirection(
                                dirs
                                .stream()
                                .filter(d -> g.isDirectionValid(d))
                                .findAny()
                                .orElse(Direction.NONE)
                        );
                    }
                });
    }

}
