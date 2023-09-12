package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import action.context.api.Context;
import action.expression.api.Expression;
import action.expression.impl.ExpressionFactory;
import action.second.entity.SecondaryEntity;
import definition.entity.api.EntityDefinition;
import instance.entity.api.EntityInstance;
import instance.property.api.PropertyInstance;
import definition.property.api.Range;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;


public class SetAction extends AbstractAction implements Serializable {

    private final String propertyName;
    private final String newValue;

    public SetAction(EntityDefinition entity, String propertyName, String newValue) {
        super(entity, ActionType.SET);
        this.propertyName = propertyName;
        this.newValue = newValue;
    }

    public SetAction(EntityDefinition mainEntity, SecondaryEntity secondaryEntity, String propertyName, String newValue) {
        super(mainEntity, secondaryEntity, ActionType.SET);
        this.propertyName = propertyName;
        this.newValue = newValue;
    }

    @Override
    public void apply(Context context) {
        EntityInstance invokeOn = context.getEntityInstance();
        PropertyInstance propertyToUpdate = invokeOn.getPropertyByName(propertyName);
        Expression expression = new ExpressionFactory(newValue, invokeOn);
        Object newValue = expression.getValue(context);
        if (propertyToUpdate.getPropertyDefinition().isNumeric()) {
            if (propertyToUpdate.getPropertyDefinition().isInteger()) {
                setInteger(propertyToUpdate, newValue);
            } else {
                setDouble(propertyToUpdate, newValue);
            }
        } else if (propertyToUpdate.getPropertyDefinition().isBoolean()) {
            setBoolean(propertyToUpdate, newValue);
        } else if (propertyToUpdate.getPropertyDefinition().isString()) {
            setString(propertyToUpdate, newValue);
        }

    }
    private void setInteger(PropertyInstance propertyToUpdate , Object newValue){
        if (!(newValue instanceof Integer)){
            throw new IllegalArgumentException(" New value must be Integer for Integer property ");
        }

        checkRangeAndUpdateNumericValue(propertyToUpdate, (Number) newValue);
    }

    private void setDouble(PropertyInstance propertyToUpdate , Object newValue) {

        checkRangeAndUpdateNumericValue(propertyToUpdate, (Number) newValue);
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
    private void setBoolean(PropertyInstance propertyToUpdate , Object newValue) {
        if (newValue instanceof Boolean){
            propertyToUpdate.setValue(newValue);
        }else {
            throw new IllegalArgumentException("New value must be Boolean for Boolean property");
        }

    }
    private void setString(PropertyInstance propertyToUpdate , Object newValue){
        if (newValue instanceof String)
            propertyToUpdate.setValue(newValue);
        else {
            throw new IllegalArgumentException("New value must be String for String property");
        }
    }

    @Override
    public Map<String, String> getArguments() {
        Map<String, String> arguments = new LinkedHashMap<>();

        arguments.put("property", propertyName);
        arguments.put("value", newValue);

        return arguments;
    }

    @Override
    public Map<String, String> getAdditionalInformation() {
        return null;
    }
}


