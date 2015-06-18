package Handlers;

import Game.GameState;
import Objects.Player;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author lukasz
 */
public class PlayerHandler
        extends AbstractHandler {

    private int startangle = 0;

    @Override
    void drawData(Graphics2D data, GameState state) {
        data.setColor(Color.YELLOW);
        Player player = state.getPlayer();
        state.getFields().stream()
                .filter(f -> f.isCross())
                .forEach((f) -> {
                    data.setColor(Color.red);
                    data.drawRect(f.getColumn() * state.getSQUARE_SIZE(),
                            f.getRow() * state.getSQUARE_SIZE(), state.getSQUARE_SIZE(), state.getSQUARE_SIZE());
                    data.setColor(Color.yellow);
                });

        if (!player.isEaten()) {
            switch (player.getDirection()) {
                case DOWN:
                    startangle = 270;
                    break;
                case UP:
                    startangle = 90;
                    break;
                case LEFT:
                    startangle = 180;
                    break;
                case RIGHT:
                    startangle = 0;
                    break;
            }
        }

        data.drawRect(player.getField().getColumn() * state.getSQUARE_SIZE(),
                player.getField().getRow() * state.getSQUARE_SIZE(), state.getSQUARE_SIZE(), state.getSQUARE_SIZE());
        data.fillArc(
                (int) player.getX() - state.getHALF_SQUARE(),
                (int) player.getY() - state.getHALF_SQUARE(),
                state.getSQUARE_SIZE(),
                state.getSQUARE_SIZE(),
                startangle + (player.getState() * 9),
                360 - (player.getState() * 18));
    }

    @Override
    void behaveData(GameState state) {
        Player player = state.getPlayer();
        if (player.getX() == 0) {
            init(player, state);
        }

        if (!player.isEaten()) {
            switch (player.getDirection()) {
                case DOWN:
                    player.move(0, 1);
                    player.nextState();
                    player.setFieldIfNear(player.getField().getFieldDown(), state.getHALF_SQUARE());
                    break;
                case UP:
                    player.move(0, -1);
                    player.nextState();
                    player.setFieldIfNear(player.getField().getFieldUp(), state.getHALF_SQUARE());
                    break;
                case LEFT:
                    player.move(-1, 0);
                    player.nextState();
                    player.setFieldIfNear(player.getField().getFieldLeft(), state.getHALF_SQUARE());
                    break;
                case RIGHT:
                    player.move(1, 0);
                    player.nextState();
                    player.setFieldIfNear(player.getField().getFieldRight(), state.getHALF_SQUARE());
                    break;
            }

        }
    }

    private void init(Player player, GameState state) {
        player.setX(state.getSQUARE_SIZE() * player.getColumn() + state.getHALF_SQUARE());
        player.setY(state.getSQUARE_SIZE() * player.getRow() + state.getHALF_SQUARE());
    }

}
