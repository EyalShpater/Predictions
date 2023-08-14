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

public class MultiplyAction extends AbstractAction {

    private final String propertyName;
    private final String Expression1;
    private final String Expression2;

    public MultiplyAction(EntityDefinition entity, String propertyName, String expression1, String expression2) {
        super(entity, ActionType.CALCULATION);
        this.propertyName = propertyName;
        this.Expression1 = expression1;
        this.Expression2 = expression2;
    }

    @Override
    public void invoke(Context context) {
        EntityInstance invokeOn = context.getEntityInstance();
        PropertyInstance propertyToUpdate = invokeOn.getPropertyByName(propertyName);
        Expression firstExpression = new ExpressionFactory(this.Expression1, invokeOn);
        Object firstExpressionValue = firstExpression.getValue(context);
        Expression secoundExpression = new ExpressionFactory(this.Expression2, invokeOn);
        Object secoundExpressionValue = secoundExpression.getValue(context);
        if (propertyToUpdate.getPropertyDefinition().isNumeric()) {
            if (propertyToUpdate.getPropertyDefinition().isInteger()) {
                multiplyInteger(propertyToUpdate, firstExpressionValue ,secoundExpressionValue );
            } else {
                multiplyDouble(propertyToUpdate, firstExpressionValue ,secoundExpressionValue);
            }
        } else {
            throw new IllegalArgumentException("Increase action only available  on numeric type!");
        }

    }

    private void multiplyInteger(PropertyInstance propertyToUpdate, Object firstExpressionValue , Object secoundExpressionValue){

        if (!(areBothIntegers(firstExpressionValue,secoundExpressionValue ))){
            throw new IllegalArgumentException("Multiply on integer number can get only two integers.");
        }
        Integer result = (Integer) firstExpressionValue * (Integer) secoundExpressionValue;
        checkRangeAndUpdateValue(propertyToUpdate , result , true);
    }


    private void multiplyDouble(PropertyInstance propertyToUpdate, Object firstExpressionValue , Object secoundExpressionValue){

        if (!areExpressionsNumeric(firstExpressionValue,secoundExpressionValue)){
            throw new IllegalArgumentException("value of expression must be numeric");
        }

    }

    private boolean areExpressionsNumeric(Object firstExpressionValue , Object secoundExpressionValue){
        return (areBothIntegers(firstExpressionValue,secoundExpressionValue)||
                areBothDoubles( firstExpressionValue , secoundExpressionValue)||
                areMixedTypes( firstExpressionValue , secoundExpressionValue));
    }

    private boolean areBothIntegers(Object firstExpressionValue , Object secoundExpressionValue){
        return firstExpressionValue instanceof Integer && secoundExpressionValue instanceof Integer;
    }

    private boolean areBothDoubles(Object firstExpressionValue , Object secoundExpressionValue){
        return firstExpressionValue instanceof Double && secoundExpressionValue instanceof Double;
    }

    private boolean areMixedTypes(Object firstExpressionValue , Object secoundExpressionValue){
        return (firstIntegerSecoundDouble(firstExpressionValue ,secoundExpressionValue)||
                firstDoubleSecoundInteger(firstExpressionValue ,secoundExpressionValue));
    }

    private boolean firstDoubleSecoundInteger(Object firstExpressionValue , Object secoundExpressionValue){
        return firstExpressionValue instanceof Double && secoundExpressionValue instanceof Integer);
    }

    private boolean firstIntegerSecoundDouble(Object firstExpressionValue , Object secoundExpressionValue){
        return firstExpressionValue instanceof Integer && secoundExpressionValue instanceof Double;
    }


    private void checkRangeAndUpdateValue(PropertyInstance propertyToUpdate, Number result, boolean isResultInteger) {
        Range range = propertyToUpdate.getPropertyDefinition().getRange();
        if (range != null) {
            double min = range.getMin();
            double max = range.getMax();
            if (isResultInteger) {
                Integer IntegerResult = (Integer) result;
                if (IntegerResult > min && IntegerResult < max) {
                    propertyToUpdate.setValue(result);
                }
            } else {
                Double DoubleResult = (Double) result;
                if (DoubleResult > min && DoubleResult < max) {
                    propertyToUpdate.setValue(result);
                }
            }
        } else {
            propertyToUpdate.setValue(result);
        }
    }
}
