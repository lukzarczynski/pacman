package AI;

import Game.GameState;
import Handlers.AbstractHandler;
import Objects.Direction;
import Objects.Field;
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

    private final int nodeExpansionThreshold = 5;
    private final int maximumSimulationLength = 20;
    private final int maximumDepht = 8;
    private final int deathPenalty = 1000;
    private final int levelAward = 1000;
    private GameState game = null;
    private StateTreeNode root;
    private AbstractHandler handler;

    public MCTS() {
        this.root = new StateTreeNode();
    }

    public void updateMCTS(GameState newState) {
        if (this.game != null && newState.convertToString().equals(this.game.convertToString())) {
            return;
        }
        root = root.getState(newState);
        if (root == null) {
            root = new StateTreeNode();
        }
        root.setParent(null);
        this.game = newState;
    }

    public void runSimulation() {
        List<StateTreeNode> visitedNodes = new ArrayList<>();

        int lives = game.getLives();

        StateTreeNode node = root;
        int depth = 1;

        while (!node.isLeaf() && depth++ < maximumDepht) {
            Direction d = selectDirection(node);
            changePlayerDirection(d);
            advanceGameToNextNode();
            node = node.getState(game);
            node.incrementTimesVisited();
            visitedNodes.add(node);
        }
        LOG.info("end loop");

        if (node == root || node.getTimesVisited() >= nodeExpansionThreshold) {
            node.expand(game);
            movePlayerRandomly();
            advanceGameToNextNode();
            node = node.getState(game);
            node.incrementTimesVisited();
            visitedNodes.add(node);
        }
        runSimulation(visitedNodes, lives);
    }

    private void changePlayerDirection(Direction dir) {
        game.getPlayer().setNextDirection(dir);
    }

    private Direction movePlayerRandomly() {
        return game.getPlayer().selectRandomMove();
    }

    private void advanceGameToNextNode() {
        handler.behave(game);
        while (!(game.isOnMiddle(game.getPlayer()) || game.getPlayer().isAtCross())) {
            handler.behave(game);
        }
    }

    private int runSimulation(List<StateTreeNode> visitedNodes, int lives) {

        int score = 0;

        if (game.getLives() < lives) {
            score -= deathPenalty;
        }

        score += rollout();

        for (StateTreeNode n : visitedNodes) {
            n.updateScore(score);
        }

        return score;
    }

    private int rollout() {
        int level = game.getLevel();
        int lives = game.getLives();
        int score = game.getScore();
        long bigPoints = game.countBigPoints();
        long pointsEaten = game.countPointsEaten();
        int ghostsEaten = game.getGhostsEaten();
        String field = "";
        double dist = ScoreEvaluator.distanceToClosestGhost(game);
        int i = 0;
        int x = 0;
        while (i++ < maximumSimulationLength
                && game.getLives() > 0
                && game.getLevel() == level) {
            handler.behave(game);
            if (game.isOnMiddle(game.getPlayer())) {
                game.getPlayer().selectRandomMove();
                if (!game.getPlayer().getField().toString().equals(field)) {
                    field = game.getPlayer().getField().toString();
                    score += ScoreEvaluator.evaluateScore(game, level, lives, score, bigPoints, pointsEaten, dist, ghostsEaten);
                    level = game.getLevel();
                    lives = game.getLives();
                    score = game.getScore();
                    bigPoints = game.countBigPoints();
                    pointsEaten = game.countPointsEaten();
                    dist = ScoreEvaluator.distanceToClosestGhost(game);
                }
            }
        }

        int scoreGained = (game.getScore() - score) * 10;
        LOG.info("gained score: " + scoreGained);

        if (game.getLives() < lives) {
            scoreGained -= deathPenalty;
        }
        if (game.getLevel() > level) {
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

    public void setGame(GameState game) {
        this.game = game;
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
