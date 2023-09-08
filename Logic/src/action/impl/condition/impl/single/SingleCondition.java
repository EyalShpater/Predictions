package action.impl.condition.impl.single;

import action.context.api.Context;
import action.impl.condition.Condition;
import definition.entity.api.EntityDefinition;
import instance.entity.api.EntityInstance;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class SingleCondition implements Condition, Serializable {
    protected String expression1;
    protected String expression2;
    EntityDefinition contextConditionEntity;

    public SingleCondition(String expression1, String expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    public SingleCondition(String expression1, String expression2, EntityDefinition contextConditionEntity) {
        this(expression1, expression2);
        this.contextConditionEntity = contextConditionEntity;
    }

    @Override
    public boolean evaluate(Context context, EntityInstance secondEntityInstance) {
        if (contextConditionEntity != null) {
            return evaluateSingleConditionWithContextEntity(context, secondEntityInstance);
        } else {
            return evaluate(expression1, expression2, context);
        }
    }

    private boolean evaluateSingleConditionWithContextEntity(Context context, EntityInstance secondEntityInstance) {
        String primaryEntityName = context.getEntityInstance().getName();
        String contextConditionEntityName = contextConditionEntity.getName();

        if (primaryEntityName.equals(contextConditionEntityName)) {
            return evaluate(expression1, expression2, context);
        } else {
            return evaluate(expression1, expression2, context.duplicateContextWithEntityInstance(secondEntityInstance));
        }
    }

    abstract protected boolean evaluate(String expression1, String expression2, Context context);

    @Override
    public Map<String, String> getArguments() {
        Map<String, String> arguments = new LinkedHashMap<>();

        arguments.put("property", expression1);
        arguments.put("operator", getOperationSign());
        arguments.put("value", expression2);

        return arguments;
    }
}