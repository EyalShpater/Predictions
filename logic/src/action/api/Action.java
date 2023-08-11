package action.api;

//import definition.entity.api.EntityDefinition;

import instance.entity.api.EntityInstance;

public interface Action {
    EntityInstance applyOn();

    ActionType getActionType();

    void invoke(EntityInstance invokeOnMe);

}
