package pacman;

import Game.GameCore;

/**
 *
 * @author lukasz
 */
public class Pacman {

    public static void main(String[] args) throws InterruptedException {
        GameCore core = new GameCore(1000,800);
        core.init();
        core.mainThreadLoop();
    }

}
