package Handlers;

import Game.GameReseter;
import Game.GameState;
import java.awt.Graphics2D;

/**
 *
 * @author lukasz
 */
public class GameHandler extends AbstractHandler {

    @Override
    void behaveData(GameState state) {
        if (state.isPlayerDead()) {
            GameReseter.reset(state, false);
        }
        if (state.isExtraModeFinished()) {
            state.setExtraMode(false);
        }

        if (state.countPointsEaten() == state.getPoints().size()) {
            GameReseter.reset(state, true);
        }
    }

    @Override
    void drawData(Graphics2D data, GameState state) {
    }

}
