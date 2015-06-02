package Handlers;

import Game.GameState;
import java.awt.Graphics2D;

public abstract class AbstractHandler {

    public final int SQUARE_SIZE;
    public final int HALF_SQUARE;
    public final int WINDOW_WIDTH;
    public final int WINDOW_HEIGHT;

    public AbstractHandler(int SQUARE_SIZE, int WINDOW_WIDTH, int WINDOW_HEIGHT) {
        this.SQUARE_SIZE = SQUARE_SIZE;
        this.HALF_SQUARE = SQUARE_SIZE / 2;
        this.WINDOW_WIDTH = WINDOW_WIDTH;
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
    }

    private AbstractHandler next;

    public void handle(Graphics2D data, GameState state) {
        this.handleData(data, state);
        if (next != null) {
            this.next.handle(data, state);
        }
    }

    abstract void handleData(Graphics2D data, GameState state);

    public void attachHandler(AbstractHandler h) {
        if (this.next != null) {
            this.next.attachHandler(h);
        } else {
            this.next = h;
        }
    }
}
