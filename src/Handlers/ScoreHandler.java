package Handlers;

import Game.GameState;
import java.awt.Graphics2D;

/**
 *
 * @author lukasz
 */
public class ScoreHandler extends AbstractHandler {

    public ScoreHandler(int SQUARE_SIZE, int WINDOW_WIDTH, int WINDOW_HEIGHT) {
        super(SQUARE_SIZE, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    @Override
    void handleData(Graphics2D data, GameState state) {
        int x = WINDOW_HEIGHT;
        int y = 20;
        
        data.drawString("SCORE", x, y);
        data.drawString(state.getScore() + "",x,y+20);
        
        
        data.drawString("LEVEL", x, y+50);
        data.drawString(state.getLevel() + "",x,y+70);
    }
    
}
