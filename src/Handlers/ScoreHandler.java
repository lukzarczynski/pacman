package Handlers;

import Game.GameState;
import Objects.Direction;
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

        data.drawString("lives", x, y + 150);
        data.drawString(state.getLives() + "", x, y + 170);

        int i = 1;
        for (Direction d : state.getStats().keySet()) {
            data.drawString("direction: " + d.name(), x, y + 150 + (i * 50));
            data.drawString(state.getStats().get(d).getCalculatedValue() + "", x, y + 170 + (i * 50));
            i++;
        }
    }

    @Override
    void behaveData(GameState state) {
    }

}
