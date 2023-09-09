package action.impl;

import action.api.AbstractAction;
import action.api.Action;
import action.api.ActionType;
import action.context.api.Context;
import definition.entity.api.EntityDefinition;
import impl.ActionDTO;

import java.util.Map;

public class Proximity extends AbstractAction {
    EntityDefinition targetEntity;
    double circleDepth;

    public Proximity(EntityDefinition sourceEntity, EntityDefinition targetEntity, String ofExpression) {
        super(sourceEntity, ActionType.PROXIMITY);
        this.targetEntity = targetEntity;
        //this.circleDepth = Double.parseDouble(of);
    }

    @Override
    public void applyAction(Context context) {

    }

    @Override
    public Map<String, String> getArguments() {
        return null;
    }
}
