package Game;

import Objects.Direction;

/**
 *
 * @author lukasz
 */
public class GameReseter {

    public static void reset(GameState state, boolean finishedLevel) {
        resetPlayer(state);
        resetGhosts(state);
        if (finishedLevel) {
            resetPoints(state);
            state.incrementLevel();
        }
        if (state.getLives() == 0) {
            resetPoints(state);
            resetGame(state);
        }
        state.setPlayerDead(false);
        state.setExtraMode(false);
    }

    private static void resetPlayer(GameState state) {
        state.getPlayer().setField(
                GameInitializer.findFieldAtPosition(state, state.getPlayer().getBaseFieldColumn(), state.getPlayer().getBaseFieldRow()));
        state.getPlayer().setX(state.getPlayer().getField().getX());
        state.getPlayer().setY(state.getPlayer().getField().getY());
        state.getPlayer().setColumn(state.getPlayer().getField().getColumn());
        state.getPlayer().setRow(state.getPlayer().getField().getRow());
        state.getPlayer().setEaten(false);
        state.getPlayer().setInExtraMode(false);
        state.getPlayer().setDirection(Direction.LEFT);
        state.getPlayer().setNextDirection(Direction.LEFT);
    }

    private static void resetGhosts(GameState state) {
        state.getGhosts().stream().forEach(g -> {
            g.setField(GameInitializer.findFieldAtPosition(state, g.getBaseFieldColumn(), g.getBaseFieldRow()));
            g.setX(g.getField().getX());
            g.setY(g.getField().getY());
            g.setColumn(g.getField().getColumn());
            g.setRow(g.getField().getRow());
            g.setEaten(false);
            g.setInExtraMode(false);
            g.setDirection(Direction.LEFT);
            g.setNextDirection(Direction.LEFT);
        });
    }

    private static void resetPoints(GameState state) {
        state.getPoints().stream().forEach(p -> p.setEaten(false));
    }

    private static void resetGame(GameState state) {
        state.setLives(3);
        state.setScore(0);
    }

}
