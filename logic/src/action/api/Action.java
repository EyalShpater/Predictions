package action.api;

import definition.entity.api.EntityDefinition;

import execution.context.api.Context;
import instance.entity.api.EntityInstance;

public interface Action {
    EntityDefinition applyOn();

    ActionType getActionType();

    void invoke(Context context);

}
