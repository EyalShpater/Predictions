package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import definition.entity.api.EntityDefinition;
import definition.property.api.PropertyDefinition;
import definition.property.api.PropertyType;

public class IncreaseAction extends AbstractAction {
    private final String propertyName;
    private final String byExpression; //Expression instead of String?

    public IncreaseAction(EntityDefinition entity, String propertyName, String byExpression) {
        super(entity, ActionType.INCREASE);
        this.propertyName = propertyName;
        this.byExpression = byExpression;
    }

    @Override
    public void invoke(EntityDefinition invokeOnMe) {
        //entity.getPropertyByName().
        //PropertyInstance theProperty = invokeOnMe.getPropertyByName(propertyName);
//        if (!checkIfThePropertyIsNumeric(theProperty.getPropertyDefinition())) {
//            throw new IllegalArgumentException("value must be of numeric type ");
//        }

    }

    public boolean checkIfThePropertyIsNumeric(PropertyDefinition PropertyDefinitionToCheck) {
        return PropertyDefinitionToCheck.getType().equals(PropertyType.INT) ||
                PropertyDefinitionToCheck.getType().equals(PropertyType.DOUBLE);
    }
}

