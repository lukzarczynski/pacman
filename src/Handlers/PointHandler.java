package Handlers;

import Game.GameState;
import Objects.PointType;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author lukasz
 */
public class PointHandler extends AbstractHandler {

    private final int smallRadius;
    private final int bigRadius;
    private final int HALF_SQUARE;

    public PointHandler(int SQUARE_SIZE, int WINDOW_WIDTH, int WINDOW_HEIGHT) {
        super(SQUARE_SIZE, WINDOW_WIDTH, WINDOW_HEIGHT);

        this.smallRadius = SQUARE_SIZE / 5;
        this.bigRadius = SQUARE_SIZE / 3;
        this.HALF_SQUARE = SQUARE_SIZE / 2;
    }

    @Override
    void handleData(Graphics2D data, GameState state) {
        data.setColor(Color.YELLOW);
        state.getPoints().stream().filter(p -> !p.isEaten()).forEach(p -> {
            if (p.getType().equals(PointType.SMALL)) {
                data.fillOval(
                        (p.getColumn() * SQUARE_SIZE) + HALF_SQUARE - smallRadius,
                        (p.getRow() * SQUARE_SIZE) + HALF_SQUARE - smallRadius,
                        2 * smallRadius,
                        2 * smallRadius);
            } else {
                data.fillOval((p.getColumn() * SQUARE_SIZE) + HALF_SQUARE - bigRadius, (p.getRow() * SQUARE_SIZE) + HALF_SQUARE - bigRadius, 2 * bigRadius, 2 * bigRadius);
            }
        });
    }

}
