package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import action.api.ReplaceMode;
import action.context.api.Context;
import action.second.entity.SecondaryEntity;
import definition.entity.api.EntityDefinition;

import java.util.Map;

public class Replace extends AbstractAction {

    ReplaceMode mode;

    EntityDefinition entityToCreate;

    public Replace(EntityDefinition entityToKill, EntityDefinition entityToCreate, String mode) {
        super(entityToKill, null, ActionType.REPLACE);
        this.entityToCreate = entityToCreate;
        this.mode = mode.equals("scratch") ? ReplaceMode.SCRATCH : ReplaceMode.DERIVED;// TODO : make sure that xml validation checks if its either one of both
    }

    @Override
    protected void apply(Context context) {

    }

    @Override
    public Map<String, String> getArguments() {
        return null;
    }
}
