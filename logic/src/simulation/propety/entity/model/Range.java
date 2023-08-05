package simulation.propety.entity.model;

import java.util.InvalidPropertiesFormatException;

public class Range {
    double from;
    double to;

    public Range(double from, double to) {
        if (from > to) {
            throw new IllegalArgumentException("Invalid range!" + System.lineSeparator()
                    + "first argument must be lower than second.");
        }

        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("%f - %f", from, to);
    }
}
