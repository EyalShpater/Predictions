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

        Double result = 0.0;
        if (!areExpressionsNumeric(firstExpressionValue,secoundExpressionValue)){
            throw new IllegalArgumentException("value of expression must be numeric");
        }
        if (areBothIntegers(firstExpressionValue,secoundExpressionValue)){
            result = (double)((Integer) firstExpressionValue * (Integer) secoundExpressionValue);
        } else if (areBothDoubles(firstExpressionValue,secoundExpressionValue)) {
            result = (Double) firstExpressionValue * (Double) secoundExpressionValue;
        } else if (firstDoubleSecondInteger(firstExpressionValue , secoundExpressionValue)) {
            result = (Double) firstExpressionValue * (Integer) secoundExpressionValue;
        } else if (firstIntegerSecondDouble(firstExpressionValue , secoundExpressionValue)) {
            result = (Integer) firstExpressionValue * (Double) secoundExpressionValue;
        }
        checkRangeAndUpdateValue(propertyToUpdate , result , false);

    }

    private boolean areExpressionsNumeric(Object firstExpressionValue , Object secondExpressionValue){
        return (areBothIntegers(firstExpressionValue,secondExpressionValue)||
                areBothDoubles( firstExpressionValue , secondExpressionValue)||
                areMixedTypes( firstExpressionValue , secondExpressionValue));
    }

    private boolean areBothIntegers(Object firstExpressionValue , Object secondExpressionValue){
        return firstExpressionValue instanceof Integer && secondExpressionValue instanceof Integer;
    }

    private boolean areBothDoubles(Object firstExpressionValue , Object secondExpressionValue){
        return firstExpressionValue instanceof Double && secondExpressionValue instanceof Double;
    }

    private boolean areMixedTypes(Object firstExpressionValue , Object secondExpressionValue){
        return (firstIntegerSecondDouble(firstExpressionValue ,secondExpressionValue)||
                firstDoubleSecondInteger(firstExpressionValue ,secondExpressionValue));
    }

    private boolean firstDoubleSecondInteger(Object firstExpressionValue , Object secondExpressionValue){
        return firstExpressionValue instanceof Double && secondExpressionValue instanceof Integer;
    }

    private boolean firstIntegerSecondDouble(Object firstExpressionValue , Object secondExpressionValue){
        return firstExpressionValue instanceof Integer && secondExpressionValue instanceof Double;
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
