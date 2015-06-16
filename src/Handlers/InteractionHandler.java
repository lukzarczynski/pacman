package Handlers;

import Game.GameState;
import Objects.Direction;
import Objects.Ghost;
import Objects.MovingGameObject;
import Objects.Player;
import Objects.PointType;
import java.awt.Graphics2D;

/**
 *
 * @author lukasz
 */
public class InteractionHandler extends AbstractHandler {

    public InteractionHandler(int SQUARE_SIZE, int WINDOW_WIDTH, int WINDOW_HEIGHT) {
        super(SQUARE_SIZE, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    @Override
    void handleData(Graphics2D data, GameState state) {
        if (state.isExtraModeFinished()) {
            state.setExtraMode(false);
        }

        Player player = state.getPlayer();
        if (player.getColumn() * SQUARE_SIZE + HALF_SQUARE == (int) player.getX()
                && player.getRow() * SQUARE_SIZE + HALF_SQUARE == (int) player.getY()) {
            tryToChangeDirection(player);
            interactWithField(player, state);
        }
        state.getGhosts().stream().forEach((g) -> {
            if (g.getColumn() * SQUARE_SIZE + HALF_SQUARE == (int) g.getX()
                    && g.getRow() * SQUARE_SIZE + HALF_SQUARE == (int) g.getY()) {
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
            player.setX(SQUARE_SIZE * player.getColumn() + HALF_SQUARE);
            player.setY(SQUARE_SIZE * player.getRow() + HALF_SQUARE);
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
                        .filter(g -> g.getField().equals(player.getField()))
                        .forEach(g -> {
                            g.setEaten(true);
                            state.eatGhost();
                            state.setScore(state.getScore() + state.getGhostsEaten());
                        });
            } else {
                if (state.getGhosts().stream()
                        .filter(g -> !g.isEaten())
                        .anyMatch(g -> g.getField().equals(player.getField()))) {
                    state.setPlayerDead(true);
                    state.reset(SQUARE_SIZE, false);
                }
            }
        }
        if(player instanceof Ghost){
            if(player.isEaten()){
                if(player.getField().equals(state.getGhostBaseField())){
                    player.setEaten(false);
                }
            }
        }

    }

}
