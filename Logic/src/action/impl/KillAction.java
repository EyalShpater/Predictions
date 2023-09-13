package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import action.context.api.Context;
import action.second.entity.SecondaryEntity;
import definition.entity.api.EntityDefinition;

import java.io.Serializable;
import java.util.Map;

public class KillAction extends AbstractAction implements Serializable {
    public KillAction(EntityDefinition entity) {
        super(entity, ActionType.KILL);
    }

    public KillAction(EntityDefinition mainEntity, SecondaryEntity secondaryEntity) {
        super(mainEntity, secondaryEntity, ActionType.KILL);
    }

    @Override
    public void apply(Context context) {
        if (isSecondaryEntityExist()) {
            context.getPrimaryEntityInstance().kill();
        } else {
            context.getPrimaryEntityInstance().kill();
        }

    }

    @Override
    public Map<String, String> getArguments() {
        return null;
    }

    @Override
    public Map<String, String> getAdditionalInformation() {
        return null;
    }
}
