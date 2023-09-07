package action.impl;

import action.api.AbstractAction;
import action.api.Action;
import action.api.ActionType;
import action.context.api.Context;
import definition.entity.api.EntityDefinition;
import impl.ActionDTO;

import java.util.Map;

public class Proximity extends AbstractAction {
    public Proximity(EntityDefinition mainEntity) {
        super(mainEntity, ActionType.PROXIMITY);
    }

    @Override
    public void applyAction(Context context) {

    }

    @Override
    public Map<String, String> getArguments() {
        return null;
    }
}
