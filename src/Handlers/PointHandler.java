package Handlers;

import Game.GameState;
import Objects.PointType;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author lukasz
 */
public class PointHandler
        extends AbstractHandler {

    private final int smallRadius;
    private final int bigRadius;

    public PointHandler(GameState state) {
        super();

        this.smallRadius = state.getSQUARE_SIZE() / 5;
        this.bigRadius = state.getSQUARE_SIZE() / 3;
    }

    @Override
    void drawData(Graphics2D data, GameState state) {
        data.setColor(Color.YELLOW);
        state.getPoints().stream().filter(p -> !p.isEaten()).forEach(p -> {
            if (p.getType().equals(PointType.SMALL)) {
                data.fillOval(
                        (p.getColumn() * state.getSQUARE_SIZE()) + state.getHALF_SQUARE() - smallRadius,
                        (p.getRow() * state.getSQUARE_SIZE()) + state.getHALF_SQUARE() - smallRadius,
                        2 * smallRadius,
                        2 * smallRadius);
            } else {
                data.fillOval(
                        (p.getColumn() * state.getSQUARE_SIZE()) + state.getHALF_SQUARE() - bigRadius,
                        (p.getRow() * state.getSQUARE_SIZE()) + state.getHALF_SQUARE() - bigRadius,
                        2 * bigRadius,
                        2 * bigRadius);
            }
        });
    }

    @Override
    void behaveData(GameState state) {
    }

}
