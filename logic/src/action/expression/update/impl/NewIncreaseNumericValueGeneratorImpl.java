package action.expression.update.impl;

import action.expression.update.api.AbstractNewNumericValueGenerator;
import action.expression.update.api.NewNumericValueGenerator;

public class NewIncreaseNumericValueGeneratorImpl extends AbstractNewNumericValueGenerator implements NewNumericValueGenerator {
    @Override
    public Object calcUpdatedValue(Object expressionToConvert, Object propertyValue) {
        //TODO: Need to add a property range check
        if (propertyValue instanceof Integer && expressionToConvert instanceof Integer) {
            return ((Integer) expressionToConvert + (Integer) propertyValue);
        } else if (propertyValue instanceof Integer && expressionToConvert instanceof Double) {
            return ((Double) expressionToConvert + (Integer) propertyValue);
        } else if (propertyValue instanceof Double && expressionToConvert instanceof Integer) {
            return ((Integer) expressionToConvert + (Double) propertyValue);
        } else if (propertyValue instanceof Double && expressionToConvert instanceof Double) {
            return ((Double) expressionToConvert + (Double) propertyValue);
        } else {
            throw new IllegalArgumentException("Unsupported type");
        }
    }

}