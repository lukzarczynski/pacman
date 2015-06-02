package Handlers;

import Game.GameState;
import Objects.Player;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author lukasz
 */
public class PlayerHandler extends AbstractHandler {

    private int startangle = 0;

    public PlayerHandler(int SQUARE_SIZE, int WINDOW_WIDTH, int WINDOW_HEIGHT) {
        super(SQUARE_SIZE, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    @Override
    void handleData(Graphics2D data, GameState state) {
        data.setColor(Color.YELLOW);
        Player player = state.getPlayer();
        if (player.getX() == 0) {
            player.setX(SQUARE_SIZE * player.getColumn() + HALF_SQUARE);
            player.setY(SQUARE_SIZE * player.getRow() + HALF_SQUARE);
        }
        state.getFields().stream()
                .filter(f->f.isCross())
                .forEach((f) -> {
                    data.setColor(Color.red);
                    data.drawRect(f.getColumn() * SQUARE_SIZE,
                            f.getRow() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                    data.setColor(Color.yellow);
                }
                );

        switch (player.getDirection()) {
            case DOWN:
                player.move(0, 1);
                player.nextState();
                if (player.getField().getFieldDown() != null && player.getY() > (player.getField().getFieldDown().getRow() * SQUARE_SIZE)) {
                    player.setField(player.getField().getFieldDown());
                }
                startangle = 270;
                break;
            case UP:
                player.move(0, -1);
                player.nextState();
                if (player.getField().getFieldUp() != null && player.getY() < ((player.getField().getFieldUp().getRow() + 1) * SQUARE_SIZE)) {
                    player.setField(player.getField().getFieldUp());
                }
                startangle = 90;
                break;
            case LEFT:
                player.move(-1, 0);
                player.nextState();
                if (player.getField().getFieldLeft() != null
                        && !(player.getField().isTeleport() && player.getField().getFieldLeft().isTeleport())
                        && player.getX() < ((player.getField().getFieldLeft().getColumn() + 1) * SQUARE_SIZE)) {
                    player.setField(player.getField().getFieldLeft());
                }
                startangle = 180;
                break;
            case RIGHT:
                player.move(1, 0);
                player.nextState();
                if (player.getField().getFieldRight() != null
                        && !(player.getField().isTeleport() && player.getField().getFieldRight().isTeleport())
                        && player.getX() > (player.getField().getFieldRight().getColumn() * SQUARE_SIZE)) {
                    player.setField(player.getField().getFieldRight());
                }
                startangle = 0;
                break;
        }

        data.drawRect(player.getField().getColumn() * SQUARE_SIZE,
                player.getField().getRow() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        data.fillArc(
                (int) player.getX() - HALF_SQUARE,
                (int) player.getY() - HALF_SQUARE,
                SQUARE_SIZE,
                SQUARE_SIZE,
                startangle + (player.getState() * 9),
                360 - (player.getState() * 18));
    }

}
