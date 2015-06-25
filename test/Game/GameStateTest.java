package Game;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author lukasz
 */
public class GameStateTest {

    public GameStateTest() {
    }

    @Test
    public void testSomeMethod() {
        GameState state1 = new GameState();
        GameInitializer.initialazeGameState(state1);
        GameState state2 = new GameState();
        GameInitializer.initialazeGameState(state2);

        assertEquals(state1.convertToString(), state2.convertToString());
    }

}
