package Handlers;

import Game.GameCore;
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

    private final int smallRadius = GameCore.getSQUARE_SIZE() / 5;
    private final int bigRadius = GameCore.getSQUARE_SIZE() / 3;

    @Override
    void drawData(Graphics2D data, GameState state) {
        data.setColor(Color.YELLOW);
        state.getPoints().stream().filter(p -> !p.isEaten()).forEach(p -> {
            if (p.getType().equals(PointType.SMALL)) {
                data.fillOval(
                        p.getX() - smallRadius,
                        p.getY() - smallRadius,
                        2 * smallRadius,
                        2 * smallRadius);
            } else {
                data.fillOval(
                        p.getX() - bigRadius,
                        p.getY() - bigRadius,
                        2 * bigRadius,
                        2 * bigRadius);
            }
        });
    }

    @Override
    void behaveData(GameState state) {
    }

}
