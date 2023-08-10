package instance.property.value.generator;

import definition.property.api.PropertyDefinition;
import definition.property.api.PropertyType;
import definition.property.api.Range;

import java.util.Random;

public class RandomValueGenerator {
    private final static String VALID_RANDOM_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789().-_,?! ";
    private final static int MIN_STRING_LENGTH = 1;
    private final static int MAX_STRING_LENGTH = 50;

    PropertyDefinition propertyDefinition;

    public RandomValueGenerator(PropertyDefinition propertyDefinition) {
        this.propertyDefinition = propertyDefinition;
    }

    public Object generateValue() {
        switch (propertyDefinition.getType()) {
            case INT:
                return initRandomlyInteger();
            case DOUBLE:
                return initRandomlyDouble();
            case STRING:
                return initRandomlyString();
            case BOOLEAN:
                return initRandomlyBoolean();
            default:
                throw new IllegalArgumentException("Invalid argument!");
        }
    }

    private Integer initRandomlyInteger() {
        return initRandomlyDouble().intValue();
    }

    private Double initRandomlyDouble() {
        Random random = new Random();
        Range range = propertyDefinition.getRange();
        double valueInitializer;

        if (range != null) {
            valueInitializer = range.getMin() + (range.getMax() - range.getMin()) * random.nextDouble();
        } else {
            valueInitializer = random.nextDouble();
        }

        return valueInitializer;
    }

    private static String initRandomlyString() {
        Random random = new Random();
        int length = random.nextInt(MAX_STRING_LENGTH - MIN_STRING_LENGTH) + MIN_STRING_LENGTH;
        StringBuilder str = new StringBuilder();

        for (int i = 1; i <= length; i++) {
            str.append(getRandomChar());
        }

        return str.toString();
    }

    private static char getRandomChar() {
        Random random = new Random();
        int index = random.nextInt(VALID_RANDOM_CHARS.length());

        return VALID_RANDOM_CHARS.charAt(index);
    }

    private static Boolean initRandomlyBoolean() {
        Random random = new Random();

        return random.nextBoolean();
    }
}
