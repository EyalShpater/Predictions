package simulation.propety.law.rule;

import simulation.propety.law.action.api.Action;

import java.util.ArrayList;

public class Rule {
    private String title;
    private ArrayList<Action> actions;
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
