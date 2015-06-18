package Objects;

/**
 *
 * @author lukasz
 */
public class Player
        extends MovingGameObject {

    private int state = 0;
    private int statedir = 1;

    public Player(int posX, int posY, Field field) {
        super(posX, posY, field);
    }

    public int getState() {
        return state;
    }

    public void nextState() {
        this.state += statedir;
        if (state == 6 || state == 0) {
            statedir = -statedir;
        }
    }

}
