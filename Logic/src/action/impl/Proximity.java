package action.impl;

import action.api.AbstractAction;
import action.api.Action;
import action.api.ActionType;
import action.context.api.Context;
import action.context.impl.ContextImpl;
import action.expression.api.Expression;
import action.expression.impl.ExpressionFactory;
import definition.entity.api.EntityDefinition;
import impl.ActionDTO;
import instance.entity.api.EntityInstance;

import java.util.*;

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
        actions = new ArrayList<>();
    }

    @Override
    public void apply(Context context) {
        String targetEntityName = targetEntity.getName();
        int radius = evaluateExpressionAsInteger(context);

        context.getEntityInstance()
                .getNearbyEntities(radius)
                .stream()
                .filter(entity -> entity.getName().equals(targetEntityName))
                .findFirst()
                .ifPresent(matchedEntity -> actions.forEach(action -> {
                    Context duplicatedContext = context.duplicateContextWithEntityInstance(matchedEntity);
                    duplicatedContext.setSecondaryEntity((context.getEntityInstance()));
                    action.invoke(duplicatedContext);
                }));
    }


    @Override
    public Map<String, String> getArguments() {
        Map<String, String> arguments = new LinkedHashMap<>();

        arguments.put("source-entity", sourceEntity.getName());
        arguments.put("target-entity", targetEntity.getName());
        arguments.put("of", ofExpression);

        return arguments;
    }

    @Override
    public Map<String, String> getAdditionalInformation() {
        Map<String, String> info = new LinkedHashMap<>();

        info.put("num of actions", String.valueOf(actions.size()));

        return info;
    }

    public void addAction(Action newAction) {
        if (newAction == null) {
            throw new NullPointerException("Action can not be null!");
        }

        actions.add(newAction);
    }

    private int evaluateExpressionAsInteger(Context context) {
        Expression expression = new ExpressionFactory(ofExpression, context.getEntityInstance());
        Object of = expression.getValue(context);

        if (!(of instanceof Integer)) {
            throw new IllegalArgumentException("'of' value must be an integer!");
        }

        return (int) of;
    }
}
