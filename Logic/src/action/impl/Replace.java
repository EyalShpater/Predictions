package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import action.context.api.Context;
import action.second.entity.SecondaryEntity;
import definition.entity.api.EntityDefinition;

import java.util.Map;

public class Replace extends AbstractAction {

    String mode;

    EntityDefinition entityToCreate;

    public Replace(EntityDefinition entityToKill, EntityDefinition entityToCreate, String mode) {
        super(entityToKill, null, ActionType.REPLACE);
        this.entityToCreate = entityToCreate;
        this.mode = mode;
    }

    @Override
    protected void applyAction(Context context) {

    }

    @Override
    public Map<String, String> getArguments() {
        return null;
    }
}
