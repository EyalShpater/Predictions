package rule.api;

import definition.entity.api.EntityDefinition;
import action.api.Action;

public interface Rule {
    String getName();

    boolean isActive(int tickNumber, double probability);

    void invoke(EntityDefinition entity);
    void addAction(Action action);
}
