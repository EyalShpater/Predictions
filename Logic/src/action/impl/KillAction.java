package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import action.context.api.Context;
import definition.entity.api.EntityDefinition;

public class KillAction extends AbstractAction {
    public KillAction(EntityDefinition entity ) {
        super(entity, ActionType.KILL);
    }

    @Override
    public void invoke(Context context) {
        context.removeEntity(context.getEntityInstance());
    }
}
