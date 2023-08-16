package action.api;

import definition.entity.api.EntityDefinition;

import action.context.api.Context;

public interface Action {
    EntityDefinition applyOn();

    ActionType getType();

    String getName();

    void invoke(Context context);

}
