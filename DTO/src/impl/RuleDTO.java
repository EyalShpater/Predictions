package impl;

import api.DTO;

import java.util.List;

public class RuleDTO implements DTO {
    private final String name;
    private final int ticks;
    private final double probability;
    private List<String> actions;

    public RuleDTO(String name, int ticks, double probability, List<String> actions) {
        this.name = name;
        this.ticks = ticks;
        this.probability = probability;
        this.actions = actions;
    }

    public String getName() {
        return name;
    }

    public int getTicks() {
        return ticks;
    }

    public double getProbability() {
        return probability;
    }

    public List<String> getActionsNames() {
        return actions;
    }
}
