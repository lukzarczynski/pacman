package AI;

import Game.GameInitializer;
import Game.GameState;
import Game.GameStateCloner;
import Handlers.AbstractHandler;
import Objects.Direction;
import Objects.Player;
import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author lukasz
 */
public class AITask {

    private static final Logger LOG = Logger.getLogger(AITask.class.getName());

    private static final AITask instance = new AITask();
    private static final GameState state = new GameState();
    private Player player;
    private Long timeout;
    private Long timeStarted;
    private final MCTS simulator = new MCTS();
    static{
        GameInitializer.initialazeGameState(state);
    }

    public static AITask getInstance() {
        return instance;
    }

    public void simulate() {
        simulator.updateMCTS(state);
        while (new Date().getTime() < (timeStarted + timeout)) {
            simulator.runSimulation();
        }
        Direction bd = simulator.bestDirection();
        if (bd != null && !bd.equals(Direction.NONE)) {
            player.setNextDirection(bd);
        }

    }

    public AITask withState(GameState st) {
        GameStateCloner.cloneState(state, st);
        return instance;
    }

    public AITask withPlayer(Player p) {
        player = p;
        return instance;
    }

    public AITask withHandler(AbstractHandler handler) {
        simulator.setHandler(handler);
        return instance;
    }

    public AITask withTimeout(Long timeout) {
        this.timeStarted = new Date().getTime();
        this.timeout = timeout;
        return instance;
    }

}
