package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import action.context.api.Context;
import action.expression.api.Expression;
import action.expression.impl.ExpressionFactory;
import action.second.entity.SecondaryEntity;
import definition.entity.api.EntityDefinition;
import definition.property.api.Range;
import instance.entity.api.EntityInstance;
import instance.property.api.PropertyInstance;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class DivideAction extends AbstractAction implements Serializable {
    private final String propertyName;
    private final String arg1;
    private final String arg2;

    public DivideAction(EntityDefinition entity, String propertyName, String arg1, String arg2) {
        super(entity, ActionType.DIVIDE);
        this.propertyName = propertyName;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public DivideAction(EntityDefinition mainEntity, SecondaryEntity secondaryEntity, String propertyName, String arg1, String arg2) {
        super(mainEntity, secondaryEntity, ActionType.DIVIDE);
        this.propertyName = propertyName;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public void apply(Context context) {
        if (isSecondaryEntityExist()) {
            applyDivideWithSecondaryEntity(context);
        } else {
            applyDividePrimaryEntity(context);
        }
    }

    private void applyDividePrimaryEntity(Context context) {
        EntityInstance invokeOn = context.getPrimaryEntityInstance();
        PropertyInstance propertyToUpdate = invokeOn.getPropertyByName(propertyName);
        Expression firstExpression = new ExpressionFactory(this.arg1, invokeOn);
        Object firstExpressionValue = firstExpression.getValue(context);
        Expression secoundExpression = new ExpressionFactory(this.arg2, invokeOn);
        Object secondExpressionValue = secoundExpression.getValue(context);

        if (propertyToUpdate.getPropertyDefinition().isNumeric()) {
            if (propertyToUpdate.getPropertyDefinition().isInteger()) {
                divideInteger(propertyToUpdate, firstExpressionValue, secondExpressionValue);
            } else {
                divideDouble(propertyToUpdate, firstExpressionValue, secondExpressionValue);
            }
        } else {
            throw new IllegalArgumentException("Divide action only available  on numeric type!");
        }
    }

    private void applyDivideWithSecondaryEntity(Context context) {
        if (secondaryEntitiesInstances != null && !secondaryEntitiesInstances.isEmpty()) {
            for (EntityInstance secondEntityInstance : secondaryEntitiesInstances) {
                context.setSecondaryEntity(secondEntityInstance);
                applyDividePrimaryEntity(context);
            }
        }
        if (isActionWithoutSecondaryEntity()) {
            applyDividePrimaryEntity(context);
        } else if (!secondaryEntitiesInstances.isEmpty()) {
            for (EntityInstance secondEntityInstance : secondaryEntitiesInstances) {
                context.setSecondaryEntity(secondEntityInstance);
                applyDividePrimaryEntity(context);
            }
        } else {
            //IF ACTION IS NOT IN CONTEXT THIS WILL THROW EXCEPTION THAT WILL BE CAUGHT HERE AND WONT BE EXECUTED
            try {
                applyDividePrimaryEntity(context);
            } catch (IllegalArgumentException ignored) {
            }
        }
    }

    private void divideInteger(PropertyInstance propertyToUpdate, Object firstExpressionValue, Object secondExpressionValue) {

        if (!(areBothIntegers(firstExpressionValue, secondExpressionValue))) {
            throw new IllegalArgumentException("Divide on integer number can get only two integers.");
        }

        if ((Integer) secondExpressionValue == 0) {
            throw new IllegalArgumentException("Cannot divide by 0");
        }

        //if the division is not Integer
        if ((Integer) firstExpressionValue % (Integer) secondExpressionValue != 0){
            throw new IllegalArgumentException("New value of Integer property must be Integer ");
        }

        Integer result = (Integer) firstExpressionValue / (Integer) secondExpressionValue;
        checkRangeAndUpdateNumericValue(propertyToUpdate , result);
    }

    private void divideDouble(PropertyInstance propertyToUpdate, Object firstExpressionValue , Object secondExpressionValue){

        if (!areExpressionsNumeric(firstExpressionValue,secondExpressionValue)){
            throw new IllegalArgumentException("value of expression must be numeric");
        }
        if (secondExpressionValue.equals(0)) {
            throw new IllegalArgumentException("Cannot divide by 0");
        }
        Double arg1 = ((Number) firstExpressionValue).doubleValue();
        Double arg2 = ((Number) secondExpressionValue).doubleValue();
        Double result = arg1 / arg2;
        checkRangeAndUpdateNumericValue(propertyToUpdate , result);
    }



    private boolean areExpressionsNumeric(Object firstExpressionValue , Object secondExpressionValue){
        return (firstExpressionValue instanceof Number &&secondExpressionValue instanceof Number);
    }

    private boolean areBothIntegers(Object firstExpressionValue , Object secondExpressionValue){
        return firstExpressionValue instanceof Integer && secondExpressionValue instanceof Integer;
    }

    private void checkRangeAndUpdateNumericValue(PropertyInstance propertyToUpdate, Number result){
        Range range = propertyToUpdate.getPropertyDefinition().getRange();

        if (range != null) {
            double min = range.getMin();
            double max = range.getMax();

            double resultValue = result.doubleValue(); // Convert Number to double

            if (resultValue > min && resultValue < max) {
                propertyToUpdate.setValue(result);
            }
        } else {
            propertyToUpdate.setValue(result);
        }
    }

    @Override
    public Map<String, String> getArguments() {
        Map<String, String> arguments = new LinkedHashMap<>();

        arguments.put("arg1", arg1);
        arguments.put("arg2", arg2);

        return arguments;
    }

    @Override
    public Map<String, String> getAdditionalInformation() {
        return null;
    }
}
