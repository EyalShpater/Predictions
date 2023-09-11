package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import action.api.ReplaceMode;
import action.context.api.Context;
import definition.entity.api.EntityDefinition;

import java.util.Map;

public class ReplaceAction extends AbstractAction {

    ReplaceMode mode;

    EntityDefinition entityToCreate;

    public ReplaceAction(EntityDefinition entityToKill, EntityDefinition entityToCreate, String mode) {
        super(entityToKill, null, ActionType.REPLACE);
        this.entityToCreate = entityToCreate;
        this.mode = mode.equals("scratch") ? ReplaceMode.SCRATCH : mode.equals("derived") ? ReplaceMode.DERIVED : null;

        if (this.mode != ReplaceMode.SCRATCH && this.mode != ReplaceMode.DERIVED) {
            throw new IllegalArgumentException("Invalid mode: " + mode);
        }
    }

    @Override
    protected void applyAction(Context context) {

    }

    @Override
    public Map<String, String> getArguments() {
        return null;
    }
}
