package rule.impl;

import action.api.Action;

import java.util.List;

public class Rule {
    private String title;
    private List<Action> actions;
    private Activation activation;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(title).append(System.lineSeparator());
        str.append(activation).append(System.lineSeparator());
        str.append(actions.size()).append("# actions:").append(System.lineSeparator());
        for (Action action : actions) {
            str.append(action).append(System.lineSeparator());
        }

        return str.toString();
    }
}
