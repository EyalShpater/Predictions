package rule.impl;

import rule.api.Activation;

import java.io.Serializable;

public class ActivationImpl implements Activation , Serializable {
    private static final int DEFAULT_NUM_OF_TICKS = 1;
    private static final double DEFAULT_PROBABILITY = 1;

    private int numOfTicksToActivate;
    private double probabilityToActivate;

    public ActivationImpl() {
        this(DEFAULT_NUM_OF_TICKS, DEFAULT_PROBABILITY);
    }

    public ActivationImpl(int numOfTicksToActivate) {
        this(numOfTicksToActivate, DEFAULT_PROBABILITY);
    }

    public ActivationImpl(double probabilityToActivate) {
        this(DEFAULT_NUM_OF_TICKS, probabilityToActivate);
    }

    // TODO: input check
    public ActivationImpl(int numOfTicksToActivate, double probabilityToActivate) {
        setNumOfTicksToActivate(numOfTicksToActivate);
        setProbabilityToActivate(probabilityToActivate);
    }

    @Override
    public int getNumOfTicksToActivate() {
        return numOfTicksToActivate;
    }

    public void setNumOfTicksToActivate(int numOfTicksToActivate) {
        if (numOfTicksToActivate < 0) {
            throw new IllegalArgumentException("Ticks can not be negative!");
        }

        this.numOfTicksToActivate = numOfTicksToActivate;
    }

    @Override
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
        return (tickNumber % numOfTicksToActivate == 0) && (probability < probabilityToActivate);
    }
}