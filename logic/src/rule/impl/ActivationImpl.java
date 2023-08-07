package rule.impl;

import rule.api.Activation;

public class ActivationImpl implements Activation {
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

    @Override
    public boolean isActive(int tickNumber, double probability) {
        return (tickNumber % numOfTicksToActivate == 0) || (probability < probabilityToActivate);
    }
}