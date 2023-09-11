package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import action.expression.api.Expression;
import action.expression.impl.ExpressionFactory;
import action.second.entity.SecondaryEntity;
import definition.entity.api.EntityDefinition;
import action.context.api.Context;
import definition.property.api.Range;
import instance.entity.api.EntityInstance;
import instance.property.api.PropertyInstance;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class DecreaseAction extends AbstractAction implements Serializable {
    private final String propertyName;
    private final String byExpression;


    public DecreaseAction(EntityDefinition entity, String propertyName, String byExpression) {
        super(entity, ActionType.DECREASE);
        this.propertyName = propertyName;
        this.byExpression = byExpression;
    }

    public DecreaseAction(EntityDefinition mainEntity, SecondaryEntity secondaryEntity, String propertyName, String byExpression) {
        super(mainEntity, secondaryEntity, ActionType.DECREASE);
        this.propertyName = propertyName;
        this.byExpression = byExpression;
    }

    @Override
    public void apply(Context context) {
        EntityInstance invokeOn = context.getEntityInstance();
        PropertyInstance propertyToUpdate = invokeOn.getPropertyByName(propertyName);
        Expression expression = new ExpressionFactory(byExpression, invokeOn);
        Object decreaseBy = expression.getValue(context);

        if (propertyToUpdate.getPropertyDefinition().isNumeric()) {
            if (propertyToUpdate.getPropertyDefinition().isInteger()) {
                decreaseInteger(propertyToUpdate, decreaseBy);
            } else {
                decreaseDouble(propertyToUpdate, decreaseBy);
            }
        } else {
            throw new IllegalArgumentException("Decrease action only available  on numeric type!");
        }
    }

    private void decreaseInteger(PropertyInstance propertyToUpdate, Object decreaseBy) {
        if (!(decreaseBy instanceof Integer)) {
            throw new IllegalArgumentException("Decrease on integer number can get only another integer.");
        }
        Integer propertyValue = (Integer) propertyToUpdate.getValue();
        Integer result = propertyValue - (Integer) decreaseBy;

        checkRangeAndUpdateNumericValue(propertyToUpdate, result);

    }

    private void decreaseDouble(PropertyInstance propertyToUpdate, Object decreaseBy) {
        Double propertyValue = (Double) propertyToUpdate.getValue();
        if (decreaseBy instanceof Number) {
            double res = ((Number) decreaseBy).doubleValue();
            Double result = propertyValue - res;
            checkRangeAndUpdateNumericValue(propertyToUpdate, result);
        } else {
            throw new IllegalArgumentException("Decrease can get only numeric values.");
        }
    }

    private void checkRangeAndUpdateNumericValue(PropertyInstance propertyToUpdate, Number result) {
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

        arguments.put("property", propertyName);
        arguments.put("by", byExpression);

        return arguments;
    }
}