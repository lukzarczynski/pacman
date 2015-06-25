package Handlers;

import Game.GameReseter;
import Game.GameState;
import Objects.Direction;
import Objects.MovingGameObject;
import Objects.Player;
import Objects.PointType;
import Utils.Calculator;
import java.awt.Graphics2D;

/**
 *
 * @author lukasz
 */
public class PlayerInteractionHandler extends AbstractHandler {

    @Override
    void behaveData(GameState state) {
        Player player = state.getPlayer();
        if (state.isOnMiddle(player)) {
            tryToChangeDirection(player);
            interactWithField(player, state);
        }
    }

    private void interactWithField(Player player, GameState state) {
        if (player.getField().isTeleport()) {
            if (player.getDirection().equals(Direction.LEFT)) {
                player.setField(player.getField().getFieldLeft());
            } else {
                player.setField(player.getField().getFieldRight());
            }
            player.setColumn(player.getField().getColumn());
            player.setRow(player.getField().getRow());
            player.setX(player.getField().getX());
            player.setY(player.getField().getY());
        }

        if (player.getField().getPoint() != null && !player.getField().getPoint().isEaten()) {
            player.getField().getPoint().setEaten(true);
            state.setScore(state.getScore() + player.getField().getPoint().getType().getValue());
            if (PointType.BIG.equals(player.getField().getPoint().getType())) {
                state.setExtraMode(true);
            }
        }

        if (state.isExtraMode()) {
            state.getGhosts().stream()
                    .filter(g -> !g.isEaten())
                    .filter(g -> Calculator.distance(player, g) < state.SQUARE_SIZE)
                    .forEach(g -> {
                        g.setEaten(true);
                        state.eatGhost();
                        state.setScore(state.getScore() + state.getGhostsEaten());
                    });
        } else {
            if (state.getGhosts().stream()
                    .filter(g -> !g.isEaten())
                    .anyMatch(g -> Calculator.distance(player, g) < state.SQUARE_SIZE)) {
                state.setPlayerDead(true);
            }
        }
    }

    private void tryToChangeDirection(MovingGameObject object) {
        if (object.isDirectionValid(object.getNextDirection())) {
            object.setDirection(object.getNextDirection());
        }
        if (!object.isDirectionValid(object.getDirection())) {
            object.setDirection(Direction.NONE);
        }
    }

    @Override
    void drawData(Graphics2D data, GameState state) {
    }

}
