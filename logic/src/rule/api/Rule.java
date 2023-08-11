package rule.api;

import definition.entity.api.EntityDefinition;
import action.api.Action;
import instance.entity.api.EntityInstance;


public interface Rule {
    String getName();

    boolean isActive(int tickNumber, double probability);

    void invoke(EntityInstance entity);

    void addAction(Action action);
}
