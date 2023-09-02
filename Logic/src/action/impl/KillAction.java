package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import action.context.api.Context;
import definition.entity.api.EntityDefinition;

import java.io.Serializable;
import java.util.Map;

public class KillAction extends AbstractAction implements Serializable {
    public KillAction(EntityDefinition entity ) {
        super(entity, ActionType.KILL);
    }

    @Override
    public void invoke(Context context) {
        context.removeEntity(context.getEntityInstance());
    }

    //todo: what to do with that
    @Override
    public Map<String, String> getArguments() {
        return null;
    }
}
