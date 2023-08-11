package rule.api;

import definition.entity.api.EntityDefinition;
import action.api.Action;
import execution.context.api.Context;
import instance.entity.api.EntityInstance;


public interface Rule {
    String getName();

    boolean isActive(int tickNumber, double probability);

    void invoke(Context context);

    void addAction(Action action);
}
