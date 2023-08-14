package menu.api;

public enum MenuOptions {
    LOAD_FILE (1),
    SHOW_SIMULATION_DETAILS (2),
    RUN_SIMULATION (3),
    SHOW_PREVIOUS_SIMULATIONS (4),
    EXIT (5);

    private final int value;

    MenuOptions(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MenuOptions fromInt(int value) {
        switch (value) {
            case 1: return LOAD_FILE;
            case 2: return SHOW_SIMULATION_DETAILS;
            case 3: return RUN_SIMULATION;
            case 4: return SHOW_PREVIOUS_SIMULATIONS;
            case 5: return EXIT;
            default: throw new IllegalArgumentException();
        }
    }
}
