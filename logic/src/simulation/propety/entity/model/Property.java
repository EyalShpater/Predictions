package simulation.propety.entity.model;

import java.util.Random;

public class Property {
    private String title;
    private PropertyType type;
    private Range range = null;
    private Object value;
    boolean isValueInitializeRandomly;

    private final static String VALID_RANDOM_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789().-_,?! ";
    private final static int MIN_STRING_LENGTH = 1;
    private final static int MAX_STRING_LENGTH = 50;

    private Property(String title, PropertyType type, boolean isValueInitializeRandomly) {
        if (title.isEmpty()) {
            throw new NullPointerException("Title can not be empty!");
        }

        if (type == null) {
            throw new NullPointerException("Property type can not be null!");
        }

        this.title = title;
        this.type = type;
        this.isValueInitializeRandomly = isValueInitializeRandomly();
    }

    public Property(String title, PropertyType type, boolean isValueInitializeRandomly, Range range) {
        this(title, type, isValueInitializeRandomly);
        this.range = range;

        if (isValueInitializeRandomly) {
            value = initRandomlyValue();
        } else {
            throw new IllegalArgumentException("Init value must be given!");
        }
    }

    public Property(String title, PropertyType type, boolean isValueInitializeRandomly, Object value) {
        this(title, type, isValueInitializeRandomly);
        if (value == null) {
            throw new IllegalArgumentException("Value can not be null!");
        }

        this.value = value;
    }

    public void setTitle(String title) {
        if (title.isEmpty()) {
            throw new NullPointerException("Title can not be empty!");
        }

        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Object getValue() {
        return value;
    }

    public void setValueInitializeRandomly(boolean valueInitializeRandomly) {
        isValueInitializeRandomly = valueInitializeRandomly;
    }


    private Object initRandomlyValue() {
        switch (type) {
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
        Random random = new Random();
        int valueInitializer;

        if (range != null) {
            valueInitializer = random.nextInt((int) (range.getMax() - range.getMin())) + (int) range.getMax();
        } else {
            valueInitializer = random.nextInt();
        }

        return valueInitializer;
    }

    private Double initRandomlyDouble() {
        Random random = new Random();
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

    public boolean isValueInitializeRandomly() {
        return isValueInitializeRandomly;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(this.title).append(System.lineSeparator());
        str.append("Type: ").append(type).append(System.lineSeparator());
        if (range != null) {
            str.append("Range: ").append(range).append(System.lineSeparator());
        }

        str.append(isValueInitializeRandomly ? "Not random" : "Random").append(" initialize");

        return str.toString();
    }
}
