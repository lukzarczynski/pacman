package Game;

import Objects.Field;
import Objects.MovingGameObject;
import java.util.logging.Logger;

/**
 *
 * @author lukasz
 */
public class GameStateCloner {

    private static final Logger LOG = Logger.getLogger(GameStateCloner.class.getName());

    public static void cloneState(GameState clonedState, GameState staticState) {

        for (int i = 0; i < staticState.getFields().size(); i++) {
            if (clonedState.getFields().get(i).hasPoint()) {
                clonedState.getFields().get(i).getPoint().setEaten(staticState.getFields().get(i).getPoint().isEaten());
            }
        }

        staticState.getGhosts().stream().forEach((ghost) -> {
            clonedState.getGhosts().stream()
                    .filter(g -> g.getType().equals(ghost.getType()))
                    .forEach(g -> updateObject(g, ghost, clonedState));
        });

        updateObject(clonedState.getPlayer(), staticState.getPlayer(), clonedState);
        clonedState.getPlayer().setLastCrossColumn(staticState.getPlayer().getLastCrossColumn());
        clonedState.getPlayer().setLastCrossRow(staticState.getPlayer().getLastCrossRow());

        clonedState.setExtraMode(staticState.isExtraMode());
        clonedState.setExtraModeCounter(staticState.getExtraModeCounter());
        clonedState.setGhostsEaten(staticState.getGhostsEaten());
        clonedState.setLevel(staticState.getLevel());
        clonedState.setLives(staticState.getLives());
        clonedState.setPlayerDead(staticState.isPlayerDead());
        clonedState.setScore(staticState.getScore());
    }

    private static void updateObject(MovingGameObject object, MovingGameObject staticObject, GameState clonedState) {
        object.setColumn(staticObject.getColumn());
        object.setRow(staticObject.getRow());
        object.setX(staticObject.getX());
        object.setY(staticObject.getY());
        object.setDirection(staticObject.getDirection());
        object.setNextDirection(staticObject.getNextDirection());
        object.setInExtraMode(staticObject.isInExtraMode());
        object.setEaten(staticObject.isEaten());
        object.setField(findFieldAtPosition(clonedState, object.getColumn(), object.getRow()));
    }

    private static Field findFieldAtPosition(GameState state, int column, int row) {
        return state.getFields().stream().filter(c -> c.getColumn() == column && c.getRow() == row).findAny().orElse(null);
    }

}
