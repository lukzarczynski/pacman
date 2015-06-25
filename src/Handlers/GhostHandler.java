package Handlers;

import Game.GameCore;
import Game.GameState;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.logging.Logger;

/**
 *
 * @author lukasz
 */
public class GhostHandler
        extends AbstractHandler {

    private final int GHOST_HEAD_HEIGHT = (int) (GameCore.getSQUARE_SIZE() * 0.4);

    @Override
    void behaveData(GameState state) {
        state.getGhosts().forEach((ghost) -> {
            switch (ghost.getDirection()) {
                case DOWN:
                    ghost.move(0, 1);
                    ghost.setFieldIfNear(ghost.getField().getFieldDown(), state.getHALF_SQUARE());
                    break;
                case UP:
                    ghost.move(0, -1);
                    ghost.setFieldIfNear(ghost.getField().getFieldUp(), state.getHALF_SQUARE());
                    break;
                case LEFT:
                    ghost.move(-1, 0);
                    ghost.setFieldIfNear(ghost.getField().getFieldLeft(), state.getHALF_SQUARE());
                    break;
                case RIGHT:
                    ghost.move(1, 0);
                    ghost.setFieldIfNear(ghost.getField().getFieldRight(), state.getHALF_SQUARE());
                    break;
            }

        });

    }

    @Override
    public void drawData(Graphics2D data, GameState state) {
        state.getGhosts().forEach((ghost) -> {
            if (ghost.isEaten()) {
                data.setColor(Color.WHITE);
            } else if (ghost.isInExtraMode()) {
                data.setColor(Color.BLUE);
            } else {
                data.setColor(ghost.getColor());
            }

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

}
