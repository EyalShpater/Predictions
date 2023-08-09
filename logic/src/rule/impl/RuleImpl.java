package rule.impl;

import action.api.Action;
import definition.entity.api.EntityInstance;
import rule.api.Activation;
import rule.api.Rule;
import java.util.List;

public class RuleImpl implements Rule {
    private String name;
    private List<Action> actions;
    private Activation activation;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isActive(int tickNumber, double probability) {
        return activation.isActive(tickNumber, probability);
    }


    @Override
    public void invoke(EntityInstance entity) {
        actions.forEach(action -> invoke(entity));
    }

    @Override
    public void addAction(Action action) {
        if (action == null) {
            throw new NullPointerException("Action can not be null");
        }

        actions.add(action);
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
}