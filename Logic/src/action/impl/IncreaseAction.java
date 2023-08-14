package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import definition.entity.api.EntityDefinition;
import action.expression.api.Expression;
import action.expression.impl.ExpressionFactory;
import action.context.api.Context;
import instance.entity.api.EntityInstance;
import instance.property.api.PropertyInstance;

public class IncreaseAction extends AbstractAction {
    private final String propertyName;
    private final String byExpression; //Expression instead of String?

    public IncreaseAction(EntityDefinition entity, String propertyName, String byExpression) {
        super(entity, ActionType.INCREASE);
        this.propertyName = propertyName;
        this.byExpression = byExpression;
    }
/*
    @Override
    public void invoke(Context context) {
        //entity.getPropertyByName().
        EntityInstance invokeOnMe = context.getPrimaryEntityInstance();
        PropertyInstance theProperty = invokeOnMe.getPropertyByName(propertyName);
        if (!checkIfThePropertyIsNumeric(theProperty.getPropertyDefinition())) {
            throw new IllegalArgumentException("value must be of numeric type ");
        }

        //create a new expression factory to generate the expression we need
        Expression theExpression = new ExpressionFactory(byExpression, invokeOnMe);

        //TODO: Ask eyal about get method (theExpression.getValue()) getting a HelperFunctionContext variable
        //TODO: because we need to iterate the environment variables in the EnvironmentHelperFunction
        //TODO: the risk is that it gets a lot of data that it does not need , but i do not see another way to solve it
        //TODO: because even if i want to give it the activeEnvironment i cant , there is no get method on context
        //generate the updated value from the expression
        context.setExpression(theExpression);  // why do we need to change the context?
        NewNumericValueGenerator valueGeneratorForTheProperty = new NewIncreaseNumericValueGeneratorImpl();
        Object newValue = valueGeneratorForTheProperty.calcUpdatedValue(theExpression.getValue(context), theProperty.getValue());

        //set the property for the entity
        invokeOnMe.getPropertyByName(propertyName).updateValue(newValue);
    }
    */

    @Override
    public void invoke(Context context) {
        EntityInstance invokeOn = context.getEntityInstance();
        PropertyInstance propertyToUpdate = invokeOn.getPropertyByName(propertyName);
        Expression expression = new ExpressionFactory(byExpression, invokeOn);
        Object increaseBy = expression.getValue(context);

        if (propertyToUpdate.getPropertyDefinition().isNumeric()) {
            if (propertyToUpdate.getPropertyDefinition().isInteger()) {
                increaseInteger(propertyToUpdate, increaseBy);
            } else {
                increaseDouble(propertyToUpdate, increaseBy);
            }
        } else {
            throw new IllegalArgumentException("Increase action only available  on numeric type!");
        }
    }

    private void increaseInteger(PropertyInstance propertyToUpdate, Object increaseBy) {
        if (!(increaseBy instanceof Integer)) {
            throw new IllegalArgumentException("Increase on integer number can get only another integer.");
        }

        Integer propertyValue = (Integer) propertyToUpdate.getValue();
        Integer result = propertyValue + (Integer) increaseBy;

        propertyToUpdate.setValue(result);
    }

    private void increaseDouble(PropertyInstance propertyToUpdate, Object increaseBy) {
        if (!(increaseBy instanceof Integer || increaseBy instanceof Double)) {
            throw new IllegalArgumentException("Increase can get only numeric values.");
        }

        Double propertyValue = (Double) propertyToUpdate.getValue();
        Double result = propertyValue + (Double) increaseBy;

        propertyToUpdate.setValue(result);
    }
}

