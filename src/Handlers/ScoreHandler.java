package Handlers;

import Game.GameState;
import java.awt.Graphics2D;

/**
 *
 * @author lukasz
 */
public class ScoreHandler
        extends AbstractHandler {

    public ScoreHandler() {
        super();
    }

    @Override
    void drawData(Graphics2D data, GameState state) {
        int x = state.getWINDOW_HEIGHT();
        int y = 20;

        data.drawString("SCORE", x, y);
        data.drawString(state.getScore() + "", x, y + 20);

        data.drawString("LEVEL", x, y + 50);
        data.drawString(state.getLevel() + "", x, y + 70);

        data.drawString("FPS", x, y + 100);
        data.drawString(state.getFps() + "", x, y + 120);
    }

    @Override
    void behaveData(GameState state) {
    }

}
