package action.api;

import definition.entity.api.EntityDefinition;

import action.context.api.Context;

public interface Action {
    EntityDefinition applyOn();

    ActionType getActionType();

    void invoke(Context context);

}
