package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import action.context.api.Context;
import action.expression.api.Expression;
import action.expression.impl.ExpressionFactory;
import definition.entity.api.EntityDefinition;
import definition.property.api.Range;
import instance.entity.api.EntityInstance;
import instance.property.api.PropertyInstance;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class MultiplyAction extends AbstractAction implements Serializable {

    private final String propertyName;
    private final String arg1;
    private final String arg2;

    public MultiplyAction(EntityDefinition entity, String propertyName, String arg1, String arg2) {
        super(entity, ActionType.CALCULATION);
        this.propertyName = propertyName;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public MultiplyAction(EntityDefinition mainEntity, EntityDefinition secondaryEntity, int populationCount, String propertyName, String arg1, String arg2) {
        super(mainEntity, secondaryEntity, populationCount, ActionType.CALCULATION);
        this.propertyName = propertyName;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public void invoke(Context context) {
        EntityInstance invokeOn = context.getEntityInstance();
        PropertyInstance propertyToUpdate = invokeOn.getPropertyByName(propertyName);
        Expression firstExpression = new ExpressionFactory(this.arg1, invokeOn);
        Object firstExpressionValue = firstExpression.getValue(context);
        Expression secoundExpression = new ExpressionFactory(this.arg2, invokeOn);
        Object secondExpressionValue = secoundExpression.getValue(context);

        if (propertyToUpdate.getPropertyDefinition().isNumeric()) {
            if (propertyToUpdate.getPropertyDefinition().isInteger()) {
                multiplyInteger(propertyToUpdate, firstExpressionValue, secondExpressionValue);
            } else {
                multiplyDouble(propertyToUpdate, firstExpressionValue, secondExpressionValue);
            }
        } else {
            throw new IllegalArgumentException("Increase action only available on numeric type!");
        }

    }

    private void multiplyInteger(PropertyInstance propertyToUpdate, Object firstExpressionValue , Object secondExpressionValue){

        if (!(areBothIntegers(firstExpressionValue,secondExpressionValue ))){
            throw new IllegalArgumentException("Multiply on integer number can get only two integers.");
        }
        Integer result = (Integer) firstExpressionValue * (Integer) secondExpressionValue;
        checkRangeAndUpdateNumericValue(propertyToUpdate , result );
    }


    private void multiplyDouble(PropertyInstance propertyToUpdate, Object firstExpressionValue , Object secondExpressionValue){

        if (!areExpressionsNumeric(firstExpressionValue,secondExpressionValue)){
            throw new IllegalArgumentException("value of expression must be numeric");
        }
        Double arg1 = ((Number) firstExpressionValue).doubleValue();
        Double arg2 = ((Number) secondExpressionValue).doubleValue();
        Double result = arg1 * arg2;
        checkRangeAndUpdateNumericValue(propertyToUpdate , result );

    }

    private boolean areExpressionsNumeric(Object firstExpressionValue , Object secondExpressionValue){
        return firstExpressionValue instanceof Number && secondExpressionValue instanceof Number;
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
}
