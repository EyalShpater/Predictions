package action.api;

import definition.entity.api.EntityDefinition;

public interface Action {
    EntityDefinition applyOn();

    ActionType getActionType();

    void invoke();
}
