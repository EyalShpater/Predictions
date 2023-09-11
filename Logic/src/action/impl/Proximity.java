package action.impl;

import action.api.AbstractAction;
import action.api.Action;
import action.api.ActionType;
import action.context.api.Context;
import action.expression.api.Expression;
import action.expression.impl.ExpressionFactory;
import definition.entity.api.EntityDefinition;
import impl.ActionDTO;

import java.util.List;
import java.util.Map;

public class Proximity extends AbstractAction {
    private final EntityDefinition sourceEntity;
    private final EntityDefinition targetEntity;
    private final String ofExpression;
    private List<Action> actions;


    public Proximity(EntityDefinition sourceEntity, EntityDefinition targetEntity, String ofExpression) {
        super(sourceEntity, ActionType.PROXIMITY);
        this.sourceEntity = sourceEntity;
        this.targetEntity = targetEntity;
        this.ofExpression = ofExpression;
    }

    @Override
    public void applyAction(Context context) {
        Expression expression = new ExpressionFactory(ofExpression, context.getEntityInstance());
        Object of = expression.getValue(context);

        if (!(of instanceof Integer)) {
            throw new IllegalArgumentException("'of' value must be an integer!");
        }


    }

    @Override
    public Map<String, String> getArguments() {
        return null;
    }
}
