package action.api;

//import definition.entity.api.EntityDefinition;

import instance.entity.api.EntityInstance;

public abstract class AbstractAction implements Action {
    private final EntityInstance entity;
    private final ActionType type;

    public AbstractAction(EntityInstance entity, ActionType type) {
        this.entity = entity;
        this.type = type;
    }

    @Override
    public EntityInstance applyOn() {
        return entity;
    }

    @Override
    public ActionType getActionType() {
        return type;
    }
}
