package action.api;

import definition.entity.api.EntityInstance;

public interface Action {
    EntityInstance applyOn();

    ActionType getActionType();

    void invoke(instance.entity.api.EntityInstance invokeOnMe);

}
