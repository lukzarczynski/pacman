package Handlers;

import Game.GameState;
import Objects.Direction;
import Objects.Ghost;
import Objects.MovingGameObject;
import Objects.Player;
import Objects.PointType;
import Utils.Calculator;
import java.awt.Graphics2D;
import java.util.logging.Logger;

/**
 *
 * @author lukasz
 */
public class InteractionHandler
        extends AbstractHandler {

    private static final Logger LOG = Logger.getLogger(InteractionHandler.class.getName());

    @Override
    void drawData(Graphics2D data, GameState state) {
    }

    @Override
    void behaveData(GameState state) {
        if (state.isExtraModeFinished()) {
            state.setExtraMode(false);
        }

        Player player = state.getPlayer();
        if (player.getColumn() * state.getSQUARE_SIZE() + state.getHALF_SQUARE() == (int) player.getX()
                && player.getRow() * state.getSQUARE_SIZE() + state.getHALF_SQUARE() == (int) player.getY()) {
            tryToChangeDirection(player);
            interactWithField(player, state);
        }
        state.getGhosts().stream().forEach((g) -> {
            if (g.getColumn() * state.getSQUARE_SIZE() + state.getHALF_SQUARE() == (int) g.getX()
                    && g.getRow() * state.getSQUARE_SIZE() + state.getHALF_SQUARE() == (int) g.getY()) {
                tryToChangeDirection(g);
                interactWithField(g, state);
            }
        });
    }

    private void tryToChangeDirection(MovingGameObject player) {

        if (player.isDirectionValid(player.getNextDirection())) {
            player.setDirection(player.getNextDirection());
        }
        if (!player.isDirectionValid(player.getDirection())) {
            player.setDirection(Direction.NONE);
        }

    }

    private void interactWithField(MovingGameObject player, GameState state) {
        if (player.getField().isTeleport()) {
            if (player.getDirection().equals(Direction.LEFT)) {
                player.setField(player.getField().getFieldLeft());
            } else {
                player.setField(player.getField().getFieldRight());
            }
            player.setColumn(player.getField().getColumn());
            player.setRow(player.getField().getRow());
            player.setX(state.getSQUARE_SIZE() * player.getColumn() + state.getHALF_SQUARE());
            player.setY(state.getSQUARE_SIZE() * player.getRow() + state.getHALF_SQUARE());
        }
        if (player instanceof Player && player.getField().getPoint() != null && !player.getField().getPoint().isEaten()) {
            player.getField().getPoint().setEaten(true);
            state.setScore(state.getScore() + player.getField().getPoint().getType().getValue());
            if (PointType.BIG.equals(player.getField().getPoint().getType())) {
                state.setExtraMode(true);
            }
        }
        if (player instanceof Player) {
            if (state.isExtraMode()) {
                state.getGhosts().stream()
                        .filter(g -> !g.isEaten())
                        .filter(g -> Calculator.distance((Player) player, g) < state.SQUARE_SIZE)
                        .forEach(g -> {
                            g.setEaten(true);
                            state.eatGhost();
                            state.setScore(state.getScore() + state.getGhostsEaten());
                        });
            } else {
                if (state.getGhosts().stream()
                        .filter(g -> !g.isEaten())
                        .anyMatch(g -> Calculator.distance((Player) player, g) < state.SQUARE_SIZE)) {
                    state.setPlayerDead(true);
                    state.reset(state.getSQUARE_SIZE(), false);
                }
            }
        }
        if (player instanceof Ghost) {
            if (player.isEaten()) {
                if (player.getField().equals(state.getGhostBaseField())) {
                    player.setEaten(false);
                }
            }
        }

    }

}
