package definition.property.api;

import java.io.Serializable;

public class Range implements Serializable {
    double min;
    double max;

    public Range(double from, double to) {
        if (from > to) {
            throw new IllegalArgumentException("Invalid range!" + System.lineSeparator()
                    + "first argument must be lower than second.");
        }

        this.min = from;
        this.max = to;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    @Override
    public String toString() {
        return String.format("%f - %f", min, max);
    }
}
