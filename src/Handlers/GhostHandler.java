package Handlers;

import Game.GameState;
import Objects.Ghost;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author lukasz
 */
public class GhostHandler
        extends AbstractHandler {

    private final int GHOST_HEAD_HEIGHT;
    private Timer mainTimer = null;

    public GhostHandler(GameState state) {
        super();
        this.GHOST_HEAD_HEIGHT = (int) (state.getSQUARE_SIZE() * 0.4);
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

    }

    @Override
    public void drawData(Graphics2D data, GameState state) {
        state.getGhosts().forEach((ghost) -> {
            if (ghost.isEaten()) {
                data.setColor(Color.WHITE);
            } else if (state.isExtraMode()) {
                data.setColor(Color.BLUE);
            } else {
                data.setColor(ghost.getColor());
            }
            data.drawRect(
                    ghost.getField().getColumn() * state.getSQUARE_SIZE(),
                    ghost.getField().getRow() * state.getSQUARE_SIZE(),
                    state.getSQUARE_SIZE(),
                    state.getSQUARE_SIZE());

            data.fillArc((int) ghost.getX() - state.getHALF_SQUARE(),
                    (int) ghost.getY() - state.getHALF_SQUARE(),
                    state.getSQUARE_SIZE(),
                    GHOST_HEAD_HEIGHT * 2,
                    0,
                    180);
            data.fillRect((int) ghost.getX() - state.getHALF_SQUARE(),
                    (int) ghost.getY() - state.getHALF_SQUARE() + GHOST_HEAD_HEIGHT,
                    state.getSQUARE_SIZE(),
                    GHOST_HEAD_HEIGHT);
            int legWidth = state.getSQUARE_SIZE() / ghost.getState();
            data.fillArc((int) ghost.getX() - state.getHALF_SQUARE(),
                    (int) ghost.getY() + state.getHALF_SQUARE() - (legWidth * 2),
                    legWidth,
                    (legWidth * 2),
                    180,
                    180);
            data.fillArc((int) ghost.getX() - state.getHALF_SQUARE() + legWidth,
                    (int) ghost.getY() + state.getHALF_SQUARE() - (legWidth * 2),
                    legWidth,
                    (legWidth * 2),
                    180,
                    180);
            data.fillArc((int) ghost.getX() - state.getHALF_SQUARE() + 2 * legWidth,
                    (int) ghost.getY() + state.getHALF_SQUARE() - (legWidth * 2),
                    legWidth,
                    (legWidth * 2),
                    180,
                    180);
            if (ghost.getState() == 4) {
                data.fillArc((int) ghost.getX() - state.getHALF_SQUARE() + 3 * legWidth,
                        (int) ghost.getY() + state.getHALF_SQUARE() - (legWidth * 2),
                        legWidth,
                        (legWidth * 2),
                        180,
                        180);
            }
        });
    }

    @Override
    void behaveData(GameState state) {
        state.getGhosts().forEach((ghost) -> {
            if (ghost.getX() == 0) {
                init(ghost, state);
            }
            switch (ghost.getDirection()) {
                case DOWN:
                    ghost.move(0, 1, state.isExtraMode());
                    ghost.setFieldIfNear(ghost.getField().getFieldDown(), state.getHALF_SQUARE());
                    break;
                case UP:
                    ghost.move(0, -1, state.isExtraMode());
                    ghost.setFieldIfNear(ghost.getField().getFieldUp(), state.getHALF_SQUARE());
                    break;
                case LEFT:
                    ghost.move(-1, 0, state.isExtraMode());
                    ghost.setFieldIfNear(ghost.getField().getFieldLeft(), state.getHALF_SQUARE());
                    break;
                case RIGHT:
                    ghost.move(1, 0, state.isExtraMode());
                    ghost.setFieldIfNear(ghost.getField().getFieldRight(), state.getHALF_SQUARE());
                    break;
            }

        });

    }

    private void init(Ghost ghost, GameState state) {
        ghost.setX(state.getSQUARE_SIZE() * ghost.getColumn() + state.getHALF_SQUARE());
        ghost.setY(state.getSQUARE_SIZE() * ghost.getRow() + state.getHALF_SQUARE());
    }

}
