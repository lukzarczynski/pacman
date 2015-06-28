package AI;

import Game.GameInitializer;
import Game.GameState;
import Game.GameStateCloner;
import Handlers.AbstractHandler;
import Objects.Direction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author lukasz
 */
public class MCTS {

    private static final Logger LOG = Logger.getLogger(MCTS.class.getName());

    private final int nodeExpansionThreshold = 10;
    private final int maximumSimulationLength = 100;
    private final int maximumDepht = 4;
    private final int deathPenalty = 1000;
    private final int levelAward = 1000;
    private final static GameState game = new GameState();
    private final static GameState tempState = new GameState();
    private StateTreeNode root = new StateTreeNode();
    private AbstractHandler handler;

    static {
        GameInitializer.initialazeGameState(game);
        GameInitializer.initialazeGameState(tempState);
    }

    public void updateState(GameState newState) {
        if (newState.convertToString().equals(MCTS.game.convertToString())
                || (newState.getPlayer().getLastCrossColumn() == MCTS.game.getPlayer().getLastCrossColumn()
                && newState.getPlayer().getLastCrossRow() == MCTS.game.getPlayer().getLastCrossRow())) {
            return;
        }
        root = root.getState(newState);
        if (root == null) {
            root = new StateTreeNode();
        }
        root.setParent(null);
        GameStateCloner.cloneState(this.game, newState);
    }

    public void runSimulation() {
        List<StateTreeNode> visitedNodes = new ArrayList<>();

        StateTreeNode node = root;
        GameStateCloner.cloneState(tempState, game);
        int lives = tempState.getLives();
        int depth = 1;

        while (!node.isLeaf() && depth++ < maximumDepht) {
            Direction d = selectDirection(node);
            changePlayerDirection(d);
            advanceGameToNextNode();
            node = node.getState(tempState);
            node.incrementTimesVisited();
            visitedNodes.add(node);
        }

        if (node == root || node.getTimesVisited() >= nodeExpansionThreshold) {
            node.expand(tempState);
            tempState.getPlayer().selectRandomMove();
            advanceGameToNextNode();
            node = node.getState(tempState);
            node.incrementTimesVisited();
            visitedNodes.add(node);
        }
        runSimulation(visitedNodes, lives);
    }

    private void changePlayerDirection(Direction dir) {
        tempState.getPlayer().setNextDirection(dir);
    }

    private void advanceGameToNextNode() {
        handler.behave(tempState);
        while (!(tempState.isOnMiddle(tempState.getPlayer()) && tempState.getPlayer().isAtCross())) {
            handler.behave(tempState);
        }
        tempState.getPlayer().setColumn(tempState.getPlayer().getField().getColumn());
        tempState.getPlayer().setRow(tempState.getPlayer().getField().getRow());
    }

    private int runSimulation(List<StateTreeNode> visitedNodes, int lives) {

        int score = 0;

        if (tempState.getLives() < lives) {
            score -= deathPenalty;
        }

        score += rollout();

        for (StateTreeNode n : visitedNodes) {
            n.updateScore(score);
        }

        return score;
    }

    private int rollout() {
        int level = tempState.getLevel();
        int lives = tempState.getLives();
        int score = tempState.getScore();
        long bigPoints = tempState.countBigPoints();
        long pointsEaten = tempState.countPointsEaten();
        int ghostsEaten = tempState.getGhostsEaten();
        String field = "";
        double dist = ScoreEvaluator.distanceToClosestGhost(tempState);
        int i = 0;
        while (i++ < maximumSimulationLength
                && tempState.getLives() > 0
                && tempState.getLevel() == level) {
            handler.behave(tempState);
            tempState.getPlayer().setColumn(tempState.getPlayer().getField().getColumn());
            tempState.getPlayer().setRow(tempState.getPlayer().getField().getRow());
            if (tempState.isOnMiddle(tempState.getPlayer())) {
                tempState.getPlayer().selectRandomMove();
                if (!tempState.getPlayer().getField().toString().equals(field.toString())) {
                    field = tempState.getPlayer().getField().toString();
                    score += ScoreEvaluator.evaluateScore(tempState, level, lives, score, bigPoints, pointsEaten, dist, ghostsEaten);
                    level = tempState.getLevel();
                    lives = tempState.getLives();
                    score = tempState.getScore();
                    bigPoints = tempState.countBigPoints();
                    pointsEaten = tempState.countPointsEaten();
                    dist = ScoreEvaluator.distanceToClosestGhost(tempState);
                }
            }
        }

        int scoreGained = (tempState.getScore() - score);

        if (tempState.getLives() < lives) {
            scoreGained -= deathPenalty;
        }
        if (tempState.getLevel() > level) {
            scoreGained += levelAward;
        }

        return scoreGained;
    }

    public Direction bestDirection() {
        return selectDirection(root);
    }

    private Direction selectDirection(StateTreeNode searchNode) {
        Collection<Double> vals = searchNode.getValues().stream().map(ns -> ns.getCalculatedValue()).collect(Collectors.toList());
        if (vals.isEmpty()) {
            return Direction.NONE;
        }
        Double max = Collections.max(vals);
        for (Direction d : searchNode.getStatistics().keySet()) {
            if (max.equals(searchNode.getStatistics().get(d).getCalculatedValue())) {
                return d;
            }
        }
        return Direction.NONE;
    }

    public GameState getGame() {
        return game;
    }

    public StateTreeNode getRoot() {
        return root;
    }

    public void setRoot(StateTreeNode root) {
        this.root = root;
    }

    public AbstractHandler getHandler() {
        return handler;
    }

    public void setHandler(AbstractHandler handler) {
        this.handler = handler;
    }

}
