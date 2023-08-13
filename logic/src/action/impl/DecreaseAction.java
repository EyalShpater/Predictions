package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import action.expression.api.Expression;
import action.expression.impl.ExpressionFactory;
import action.expression.update.api.NewNumericValueGenerator;
import action.expression.update.impl.NewIncreaseNumericValueGeneratorImpl;
import action.expression.update.impl.NewdecreaseNumericValueGeneratorImpl;
import definition.entity.api.EntityDefinition;
import definition.property.api.PropertyDefinition;
import definition.property.api.PropertyType;
import execution.context.api.Context;
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
        //entity.getPropertyByName().
        EntityInstance invokeOnMe = context.getPrimaryEntityInstance();
        PropertyInstance theProperty = invokeOnMe.getPropertyByName(propertyName);
        if (!checkIfThePropertyIsNumeric(theProperty.getPropertyDefinition())) {
            throw new IllegalArgumentException("value must be of numeric type ");
        }

        //create a new expression factory to generate the expression we need
        Expression theExpression = new ExpressionFactory(byExpression, invokeOnMe);

        //generate the updated value from the expression
        NewNumericValueGenerator valueGeneratorForTheProperty = new NewdecreaseNumericValueGeneratorImpl();
        Object newValue = valueGeneratorForTheProperty.calcUpdatedValue(theExpression.getValue(context), theProperty.getValue());

        //set the property for the entity
        invokeOnMe.getPropertyByName(propertyName).updateValue(newValue);
    }

    public boolean checkIfThePropertyIsNumeric(PropertyDefinition PropertyDefinitionToCheck) {
        return checkIfThePropertyIsInterger(PropertyDefinitionToCheck) ||
                checkIfThePropertyIsDouble(PropertyDefinitionToCheck);
    }

    public boolean checkIfThePropertyIsInterger(PropertyDefinition PropertyDefinitionToCheck) {
        return PropertyDefinitionToCheck.getType().equals(PropertyType.INT);
    }

    public boolean checkIfThePropertyIsDouble(PropertyDefinition PropertyDefinitionToCheck) {
        return PropertyDefinitionToCheck.getType().equals(PropertyType.DOUBLE);
    }
}