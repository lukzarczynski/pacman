package Handlers;

import Game.GameState;
import Objects.Ghost;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author lukasz
 */
public class GhostHandler extends AbstractHandler {

    private final int GHOST_HEAD_HEIGHT;
    private Timer mainTimer = null;

    public GhostHandler(int SQUARE_SIZE, int WINDOW_WIDTH, int WINDOW_HEIGHT) {
        super(SQUARE_SIZE, WINDOW_WIDTH, WINDOW_HEIGHT);
        this.GHOST_HEAD_HEIGHT = (int) (SQUARE_SIZE * 0.4);
    }

    @Override
    void handleData(Graphics2D data, GameState state) {
        if (mainTimer == null) {
            TimerTask ghostLegs = new TimerTask() {
                @Override
                public void run() {
                    state.getGhosts().stream().forEach((g) -> {
                        g.nextState();
                    });
                }
            };
            mainTimer = new Timer();
            mainTimer.scheduleAtFixedRate(ghostLegs, 0, 500);
        }
        state.getGhosts().forEach((ghost) -> {
            data.setColor(ghost.getColor());
            data.drawRect(ghost.getField().getColumn() * SQUARE_SIZE, ghost.getField().getRow() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

            if (ghost.getX() == 0) {
                init(ghost);
            }

            switch (ghost.getDirection()) {
                case DOWN:
                    ghost.move(0, 1);
                    if (ghost.getField().getFieldDown() != null && ghost.getY() > (ghost.getField().getFieldDown().getRow() * SQUARE_SIZE)) {
                        ghost.setField(ghost.getField().getFieldDown());
                    }
                    break;
                case UP:
                    ghost.move(0, -1);
                    if (ghost.getField().getFieldUp() != null && ghost.getY() < ((ghost.getField().getFieldUp().getRow() + 1) * SQUARE_SIZE)) {
                        ghost.setField(ghost.getField().getFieldUp());
                    }
                    break;
                case LEFT:
                    ghost.move(-1, 0);
                    if (ghost.getField().getFieldLeft() != null
                            && !(ghost.getField().isTeleport() && ghost.getField().getFieldLeft().isTeleport())
                            && ghost.getX() < ((ghost.getField().getFieldLeft().getColumn() + 1) * SQUARE_SIZE)) {
                        ghost.setField(ghost.getField().getFieldLeft());
                    }
                    break;
                case RIGHT:
                    ghost.move(1, 0);
                    if (ghost.getField().getFieldRight() != null
                            && !(ghost.getField().isTeleport() && ghost.getField().getFieldRight().isTeleport())
                            && ghost.getX() > (ghost.getField().getFieldRight().getColumn() * SQUARE_SIZE)) {
                        ghost.setField(ghost.getField().getFieldRight());
                    }
                    break;
            }

            data.fillArc((int) ghost.getX() - HALF_SQUARE,
                    (int) ghost.getY() - HALF_SQUARE,
                    SQUARE_SIZE,
                    GHOST_HEAD_HEIGHT * 2,
                    0,
                    180);
            data.fillRect((int) ghost.getX() - HALF_SQUARE,
                    (int) ghost.getY() - HALF_SQUARE + GHOST_HEAD_HEIGHT,
                    SQUARE_SIZE,
                    GHOST_HEAD_HEIGHT);
            int legWidth = SQUARE_SIZE / ghost.getState();
            data.fillArc((int) ghost.getX() - HALF_SQUARE,
                    (int) ghost.getY() + HALF_SQUARE - (legWidth * 2),
                    legWidth,
                    (legWidth * 2),
                    180,
                    180);
            data.fillArc((int) ghost.getX() - HALF_SQUARE + legWidth,
                    (int) ghost.getY() + HALF_SQUARE - (legWidth * 2),
                    legWidth,
                    (legWidth * 2),
                    180,
                    180);
            data.fillArc((int) ghost.getX() - HALF_SQUARE + 2 * legWidth,
                    (int) ghost.getY() + HALF_SQUARE - (legWidth * 2),
                    legWidth,
                    (legWidth * 2),
                    180,
                    180);
            if (ghost.getState() == 4) {
                data.fillArc((int) ghost.getX() - HALF_SQUARE + 3 * legWidth,
                        (int) ghost.getY() + HALF_SQUARE - (legWidth * 2),
                        legWidth,
                        (legWidth * 2),
                        180,
                        180);
            }
        });

    }

    private void init(Ghost ghost) {

        ghost.setX(SQUARE_SIZE * ghost.getColumn() + HALF_SQUARE);
        ghost.setY(SQUARE_SIZE * ghost.getRow() + HALF_SQUARE);
    }

}
