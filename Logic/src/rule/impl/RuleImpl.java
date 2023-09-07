package rule.impl;

import action.api.Action;
import action.context.api.Context;
import impl.RuleDTO;
import rule.api.Activation;
import rule.api.Rule;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class RuleImpl implements Rule , Serializable {
    private final String name;
    private List<Action> actions;
    private Activation activation;

    public RuleImpl(String name, Activation activation) {
        this.name = name;
        this.actions = new ArrayList<>();
        this.activation = activation;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isActive(int tickNumber, double probability) {
        return activation.isActive(tickNumber, probability);
    }

    @Override
    public void invoke(Context context) {
        actions.forEach(action -> action.invoke(context));
    }

    @Override
    public void addAction(Action action) {
        if (action == null) {
            throw new NullPointerException("Action can not be null");
        }

        actions.add(action);
    }

    @Override
    public RuleDTO convertToDTO() {
        return new RuleDTO(
                name,
                activation.getNumOfTicksToActivate(),
                activation.getProbabilityToActivate(),
                actions.stream()
                        .map(Action::convertToDTO)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(name).append(System.lineSeparator());
        str.append(activation).append(System.lineSeparator());
        str.append(actions.size()).append("# actions:").append(System.lineSeparator());
        for (Action action : actions) {
            str.append(action).append(System.lineSeparator());
        }

        return str.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleImpl rule = (RuleImpl) o;
        return Objects.equals(name, rule.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
