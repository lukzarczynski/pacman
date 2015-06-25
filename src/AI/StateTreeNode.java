package AI;

import Game.GameState;
import Objects.Direction;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lukasz
 */
public class StateTreeNode {

    private StateTreeNode parent;
    private final Map<String, StateTreeNode> states = new HashMap<>();
    private final Map<Direction, NodeStats> statistics = new HashMap<>();
    private Direction directionToGetHere;

    public StateTreeNode() {
    }

    public StateTreeNode(StateTreeNode parent, Direction direction) {
        this.parent = parent;
        this.directionToGetHere = direction;
    }

    public boolean isLeaf() {
        return states.isEmpty();
    }

    public boolean isRoot() {
        return parent == null;
    }

    public StateTreeNode getParent() {
        return parent;
    }

    public void setParent(StateTreeNode parent) {
        this.parent = parent;
    }

    public Direction getDirectionToGetHere() {
        return directionToGetHere;
    }

    public StateTreeNode getState(GameState state) {
        if (this.states.get(state.convertToString()) == null) {
            StateTreeNode node = new StateTreeNode(this, state.getPlayer().getDirection());
            if (!statistics.containsKey(state.getPlayer().getDirection())) {
                statistics.put(state.getPlayer().getDirection(), new NodeStats());
            }
            statistics.get(state.getPlayer().getDirection()).incrementTimesVisited();
            this.states.put(state.convertToString(), node);
        }
        return this.states.get(state.convertToString());
    }

    public Collection<StateTreeNode> getStates() {
        return this.states.values();
    }

    public void updateScore(int score) {
        this.parent.statistics.get(directionToGetHere).addScore(score);
    }

    public void incrementTimesVisited() {
        this.parent.statistics.get(directionToGetHere).incrementTimesVisited();
    }

    public int getTimesVisited() {
        return this.parent.statistics.get(directionToGetHere).getTimesVisited();
    }

    public void expand(GameState state) {
        for (Direction d : Direction.values()) {
            if (state.getPlayer().isDirectionValid(d) && !statistics.containsKey(d)) {
                statistics.put(d, new NodeStats());
            }
        }
    }

    public Collection<NodeStats> getValues() {
        return statistics.values();
    }

    public Map<Direction, NodeStats> getStatistics() {
        return statistics;
    }

}
