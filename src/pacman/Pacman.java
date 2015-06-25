package pacman;

import Game.GameCore;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author lukasz
 */
public class Pacman {

    public static void main(String[] args) throws InterruptedException, CloneNotSupportedException, ExecutionException {
        GameCore core = new GameCore();
        core.init();
        core.mainThreadLoop();
    }

}
