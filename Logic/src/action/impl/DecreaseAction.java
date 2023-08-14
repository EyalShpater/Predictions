package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import action.expression.api.Expression;
import action.expression.impl.ExpressionFactory;
import definition.entity.api.EntityDefinition;
import definition.property.api.PropertyDefinition;
import definition.property.api.PropertyType;
import action.context.api.Context;
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
        EntityInstance invokeOn = context.getPrimaryEntityInstance();
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

    private void decreaseInteger(PropertyInstance propertyToUpdate, Object increaseBy) {
        if (!(increaseBy instanceof Integer)) {
            throw new IllegalArgumentException("Increase on integer number can get only another integer.");
        }

        Integer propertyValue = (Integer) propertyToUpdate.getValue();
        Integer result = propertyValue - (Integer) increaseBy;

        propertyToUpdate.setValue(result);
    }

    private void decreaseDouble(PropertyInstance propertyToUpdate, Object increaseBy) {
        if (!(increaseBy instanceof Integer || increaseBy instanceof Double)) {
            throw new IllegalArgumentException("Increase can get only numeric values.");
        }

        Double propertyValue = (Double) propertyToUpdate.getValue();
        Double result = propertyValue - (Double) increaseBy;

        propertyToUpdate.setValue(result);
    }
}