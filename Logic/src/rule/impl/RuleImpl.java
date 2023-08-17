package rule.impl;

import action.api.Action;
import action.context.api.Context;
import api.DTO;
import impl.RuleDTO;
import rule.api.Activation;
import rule.api.Rule;

import java.util.*;
import java.util.stream.Collectors;


public class RuleImpl implements Rule {
    private final String name;
    private SortedSet<String> relevantEntities;
    private List<Action> actions;
    private Activation activation;

    
    public RuleImpl(String name, Activation activation, String... relevantEntity) {
        this.name = name;
        this.actions = new ArrayList<>();
        this.activation = activation;
        initRelevantEntities(Arrays.asList(relevantEntity));
    }

    public RuleImpl(RuleDTO dto) {
        this(dto.getName(),
                new ActivationImpl(dto.getTicks(), dto.getProbability()),
                dto.getActionsNames().toArray(new String[0]));
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
        if (relevantEntities.contains(context.getEntityInstance().getName()) && context.getEntityInstance().isAlive()) {
            actions.forEach(action -> action.invoke(context));
        }
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
                        .map(Action::getName)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Rule revertFromDTO(RuleDTO dto) {
        return new RuleImpl(
                dto.getName(),
                new ActivationImpl(dto.getTicks(), dto.getProbability()),
                dto.getActionsNames().toArray(new String[0])
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

    private void initRelevantEntities(List<String> names) {
        relevantEntities = new TreeSet<>();
        relevantEntities.addAll(names);
    }
}