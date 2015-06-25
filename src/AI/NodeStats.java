package AI;

import java.util.Random;

/**
 *
 * @author lukasz
 */
public class NodeStats {

    private static final double epsilon = 1e-6;
    private static final Random random = new Random();

    private int timesVisited = 0;
    private double estimatedValue = 0;
    private double calculatedValue = Double.NEGATIVE_INFINITY;

    public int getTimesVisited() {
        return timesVisited;
    }

    public double getEstimatedValue() {
        return estimatedValue;
    }

    public void incrementTimesVisited() {
        this.timesVisited++;
        resetCalculatedValue();
    }

    public void addScore(double value) {
        estimatedValue += value;
        resetCalculatedValue();
    }

    private void resetCalculatedValue() {
        this.calculatedValue = Double.NEGATIVE_INFINITY;
    }

    public double getCalculatedValue() {
        if (calculatedValue == Double.NEGATIVE_INFINITY) {
            calculatedValue = getEstimatedValue() / (getTimesVisited() + epsilon)
                    + Math.sqrt((getTimesVisited() + 1) / (getTimesVisited() + epsilon)) + random.nextDouble() * epsilon;
        }
        return calculatedValue;
    }

}
