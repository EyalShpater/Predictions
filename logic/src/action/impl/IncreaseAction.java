package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import definition.entity.api.EntityDefinition;
import action.expression.api.Expression;
import action.expression.update.api.NewNumericValueGenerator;
import action.expression.impl.ExpressionFactory;
import action.expression.update.impl.NewIncreaseNumericValueGeneratorImpl;
import definition.property.api.PropertyDefinition;
import definition.property.api.PropertyType;
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

    public boolean checkIfThePropertyIsNumeric(PropertyDefinition PropertyDefinitionToCheck) {
        return checkIfThePropertyIsInteger(PropertyDefinitionToCheck) ||
                checkIfThePropertyIsDouble(PropertyDefinitionToCheck);
    }

    public boolean checkIfThePropertyIsInteger(PropertyDefinition PropertyDefinitionToCheck) {
        return PropertyDefinitionToCheck.getType().equals(PropertyType.INT);
    }

    public boolean checkIfThePropertyIsDouble(PropertyDefinition PropertyDefinitionToCheck) {
        return PropertyDefinitionToCheck.getType().equals(PropertyType.DOUBLE);
    }
}

