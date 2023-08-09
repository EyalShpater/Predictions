package rule.api;

import definition.entity.api.EntityInstance;
import action.api.Action;

public interface Rule {
    String getName();

    boolean isActive(int tickNumber, double probability);

    void invoke(EntityInstance entity);
    void addAction(Action action);
}
