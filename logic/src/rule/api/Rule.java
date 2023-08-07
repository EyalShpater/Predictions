package rule.api;

import definition.entity.api.EntityDefinition;
import rule.impl.ActivationImpl;
import action.api.Action;

import java.util.List;

public interface Rule {
    String getName();

    boolean isActive(int tickNumber, double probability);

    void invoke(EntityDefinition entity);
    void addAction(Action action);
}
