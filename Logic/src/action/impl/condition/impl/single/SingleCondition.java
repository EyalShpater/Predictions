package action.impl.condition.impl.single;

import action.context.api.Context;
import action.impl.condition.Condition;
import definition.entity.api.EntityDefinition;
import instance.entity.api.EntityInstance;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class SingleCondition implements Condition, Serializable {
    protected EntityDefinition entityToEvaluate;
    protected String expression1;
    protected String expression2;

    public SingleCondition(EntityDefinition entityToEvaluate, String expression1, String expression2) {
        this.entityToEvaluate = entityToEvaluate;
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public Boolean evaluate(Context context) {
        return evaluate(expression1, expression2, context);
    }

//    private boolean isSecondaryEntityInstanceExist(EntityInstance secondEntityInstance) {
//        return secondEntityInstance != null;
//    }
//
//    private Boolean evaluateSingleConditionWithContextEntity(Context context, EntityInstance secondEntityInstance) {
//        String primaryEntityName = context.getPrimaryEntityInstance().getName();
//        String contextConditionEntityName = contextConditionEntity.getName();
//
//        if (primaryEntityName.equals(contextConditionEntityName)) {
//            return evaluate(expression1, expression2, context);
//        }else {
//            if (isSecondaryEntityInstanceExist(secondEntityInstance)) {
//                return evaluate(expression1, expression2, context.duplicateContextWithEntityInstance(secondEntityInstance));
//            }
//        }
//        return null;
//    }

    abstract protected boolean evaluate(String expression1, String expression2, Context context);

    @Override
    public Map<String, String> getArguments() {
        Map<String, String> arguments = new LinkedHashMap<>();

        arguments.put("property", expression1);
        arguments.put("operator", getOperationSign());
        arguments.put("value", expression2);

        return arguments;
    }

    @Override
    public Map<String, String> getAdditionalInformation() {
        return null;
    }

    @Override
    public EntityDefinition getPrimaryEntity() {
        return entityToEvaluate;
    }

    @Override
    public boolean isSingleCondition() {
        return true;
    }
}