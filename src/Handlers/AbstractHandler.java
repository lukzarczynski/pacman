package Handlers;

import Game.GameState;
import java.awt.Graphics2D;

public abstract class AbstractHandler {

    private AbstractHandler next;

    public void behave(GameState state) {
        this.behaveData(state);
        if (next != null) {
            this.next.behave(state);
        }
    }

    public void draw(Graphics2D data, GameState state) {
        this.drawData(data, state);
        if (next != null) {
            this.next.draw(data, state);
        }
    }

    abstract void behaveData(GameState state);

    abstract void drawData(Graphics2D data, GameState state);

    public void attachHandler(AbstractHandler h) {
        if (this.next != null) {
            this.next.attachHandler(h);
        } else {
            this.next = h;
        }
    }
}
