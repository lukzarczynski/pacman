package AI;

import Game.GameState;
import Utils.Calculator;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 *
 * @author lukasz
 */
public class ScoreEvaluator {

    private static final double safetyArea = 100;
    private static final double bigPointAward = 1000;
    private static final double closerToGhostAward = 50;
    private static final double ghostEatenAward = 2000;
    private static final double pointEatenAward = 50;

    public static double evaluateScore(GameState state, int level, int lives, int score, long bigPoints, long pointsEaten, double distToGhost, int ghostsEaten) {
        double calculatedScore = 0;
        double distanceToClosestGhost = distanceToClosestGhost(state);
        if (state.isExtraMode()) {
            if (bigPoints > state.countBigPoints()) {
                calculatedScore -= bigPointAward;
            }
            if (distanceToClosestGhost < distToGhost) {
                calculatedScore += closerToGhostAward;
            }
            if (ghostsEaten < state.getGhostsEaten()) {
                calculatedScore += ghostEatenAward;
            }
        } else if (distanceToClosestGhost < safetyArea) {
            if (bigPoints > state.countBigPoints()) {
                calculatedScore += bigPointAward;
            }
            if (distanceToClosestGhost < distToGhost) {
                calculatedScore -= closerToGhostAward;
            }
        } else {
            if (pointsEaten < state.countPointsEaten()) {
                calculatedScore += pointEatenAward;
            }
        }

        return calculatedScore;
    }

    public static double distanceToClosestGhost(GameState state) {
        return Collections.min(state.getGhosts().stream().map(g -> Calculator.distance(state.getPlayer(), g)).collect(Collectors.toList()));
    }

}
