package Handlers;

import Game.GameState;
import Objects.Direction;
import Objects.Ghost;
import Objects.MovingGameObject;
import java.awt.Graphics2D;

/**
 *
 * @author lukasz
 */
public class GhostInteractionHandler
        extends AbstractHandler {

    @Override
    void drawData(Graphics2D data, GameState state) {
    }

    @Override
    void behaveData(GameState state) {

        state.getGhosts().stream().forEach((ghost) -> {
            if (state.isOnMiddle(ghost)) {
                tryToChangeDirection(ghost);
                interactWithField(ghost, state);
            }
        });
    }

    private void tryToChangeDirection(MovingGameObject object) {

        if (object.isDirectionValid(object.getNextDirection())) {
            object.setDirection(object.getNextDirection());
        }
        if (!object.isDirectionValid(object.getDirection())) {
            object.setDirection(Direction.NONE);
        }

    }

    private void interactWithField(Ghost ghost, GameState state) {
        if (ghost.getField().isTeleport()) {
            if (ghost.getDirection().equals(Direction.LEFT)) {
                ghost.setField(ghost.getField().getFieldLeft());
            } else {
                ghost.setField(ghost.getField().getFieldRight());
            }
            ghost.setColumn(ghost.getField().getColumn());
            ghost.setRow(ghost.getField().getRow());
            ghost.setX(ghost.getField().getX());
            ghost.setY(ghost.getField().getY());
        }

        if (ghost.isEaten()) {
            if (ghost.getField().equals(state.getGhostBaseField())) {
                ghost.setEaten(false);
            }
        }

    }

}
