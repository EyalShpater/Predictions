package rule.api;

import rule.impl.Activation;
import action.api.Action;

import java.util.List;

public interface Rule {
    String getName();
    Activation getActivation();
    List<Action> getActionsToPerform();
    void addAction(Action action);
}
