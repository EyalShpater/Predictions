package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import action.expression.api.Expression;
import action.expression.impl.ExpressionFactory;
import definition.entity.api.EntityDefinition;
import action.context.api.Context;
import definition.property.api.Range;
import instance.entity.api.EntityInstance;
import instance.property.api.PropertyInstance;

public class DecreaseAction extends AbstractAction {
    private final String propertyName;
    private final String byExpression; //Expression instead of String?

    public DecreaseAction(EntityDefinition entity, String propertyName, String byExpression) {
        super(entity, ActionType.INCREASE);
        this.propertyName = propertyName;
        this.byExpression = byExpression;
    }

    @Override
    public void invoke(Context context) {
        EntityInstance invokeOn = context.getEntityInstance();
        PropertyInstance propertyToUpdate = invokeOn.getPropertyByName(propertyName);
        Expression expression = new ExpressionFactory(byExpression, invokeOn);
        Object increaseBy = expression.getValue(context);

        if (propertyToUpdate.getPropertyDefinition().isNumeric()) {
            if (propertyToUpdate.getPropertyDefinition().isInteger()) {
                decreaseInteger(propertyToUpdate, increaseBy);
            } else {
                decreaseDouble(propertyToUpdate, increaseBy);
            }
        } else {
            throw new IllegalArgumentException("Increase action only available  on numeric type!");
        }
    }

    private void decreaseInteger(PropertyInstance propertyToUpdate, Object decreaseBy) {
        if (!(decreaseBy instanceof Integer)) {
            throw new IllegalArgumentException("Increase on integer number can get only another integer.");
        }
        Integer propertyValue = (Integer) propertyToUpdate.getValue();
        Integer result = propertyValue - (Integer) decreaseBy;

        checkRangeAndUpdateNumericValue(propertyToUpdate , result);

    }

    private void decreaseDouble(PropertyInstance propertyToUpdate, Object decreaseBy) {
        Double propertyValue = (Double) propertyToUpdate.getValue();
        if (decreaseBy instanceof Number){
            double res  = ((Number)decreaseBy).doubleValue();
            Double result = propertyValue - res;
            checkRangeAndUpdateNumericValue(propertyToUpdate , result );
        }else{
            throw new IllegalArgumentException("Increase can get only numeric values.");
        }
        /*if(decreaseBy instanceof Integer){
            Double result = propertyValue - (Integer)decreaseBy;
            checkRangeAndUpdateValue(propertyToUpdate , result , false);
        } else if (decreaseBy instanceof Double) {
            Double result = propertyValue - (Double)decreaseBy;
            checkRangeAndUpdateValue(propertyToUpdate , result , false);
        }*/
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
}