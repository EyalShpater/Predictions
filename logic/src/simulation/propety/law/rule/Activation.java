package simulation.propety.law.rule;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

public class Activation {
    private int numOfTicksToActivate;
    private double probabilityToActivate = 1;

    public int getNumOfTicksToActivate() {
        return numOfTicksToActivate;
    }

    public void setNumOfTicksToActivate(int numOfTicksToActivate) {
        if (numOfTicksToActivate < 0) {
            throw new IllegalArgumentException("Ticks can not be negative!");
        }

        this.numOfTicksToActivate = numOfTicksToActivate;
    }

    public double getProbabilityToActivate() {
        return probabilityToActivate;
    }

    public void setProbabilityToActivate(double probability) {
        if (probability < 0 || probability > 1) {
            throw new IllegalArgumentException("Probability must be between 0 to 1");
        }

        this.probabilityToActivate = probability;
    }
}
