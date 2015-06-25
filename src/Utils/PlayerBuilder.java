package Utils;

import Objects.Player;

/**
 *
 * @author lukasz
 */
public class PlayerBuilder extends MovingGameObjectBuilder<Player> {

    @Override
    Player createObject() {
        return new Player();
    }

    

}
