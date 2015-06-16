package Game;

import Objects.Direction;
import Objects.WallType;
import Utils.Dijsktra;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author lukasz
 */
public class GameStateTest {

    GameState state;

    @Before
    public void setUp() {
        state = new GameState();
        state.initGameState();
    }

    @Test
    public void testSomeMethod() {
        assertFalse(state.getGhosts().isEmpty());
        assertFalse(state.getPoints().isEmpty());
        assertFalse(state.getWalls().isEmpty());
    }

    @Test
    public void testGhosts() {
        assertEquals(4, state.getGhosts().size());
    }

    @Test
    public void testPoints() {
        assertEquals(244, state.getPoints().size());
    }

    @Test
    public void testIfAllWallsPresent() {
        for (WallType type : WallType.values()) {
            assertTrue(state.getWalls().stream().anyMatch(o -> o.getType().equals(type)));
        }
    }
    
    @Test
    public void testDijkstra(){
        List<Direction> result = Dijsktra.getDirections(state.getGhostBaseField(), state.getPlayer().getField());
    }

}
