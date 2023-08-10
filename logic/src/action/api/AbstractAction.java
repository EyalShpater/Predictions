package action.api;

import definition.entity.api.EntityDefinition;

public abstract class AbstractAction implements Action {
    private final EntityDefinition entity;
    private final ActionType type;

    public AbstractAction(EntityDefinition entity, ActionType type) {
        this.entity = entity;
        this.type = type;
    }

    @Override
    public EntityDefinition applyOn() {
        return entity;
    }

    @Override
    public ActionType getActionType() {
        return type;
    }
}
