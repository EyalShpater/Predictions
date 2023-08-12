package action.expression.update.impl;

import action.expression.update.api.AbstractNewNumericValueGenerator;
import action.expression.update.api.NewNumericValueGenerator;

public class NewdecreaseNumericValueGeneratorImpl extends AbstractNewNumericValueGenerator implements NewNumericValueGenerator {
    @Override
    public Object calcUpdatedValue(Object expressionToConvert, Object propertyValue) {
        //TODO: Need to add a property range check
        if (propertyValue instanceof Integer && expressionToConvert instanceof Integer) {
            return ((Integer) propertyValue - (Integer) expressionToConvert);
        } else if (propertyValue instanceof Integer && expressionToConvert instanceof Double) {
            return ((Integer) propertyValue - (Double) expressionToConvert);
        } else if (propertyValue instanceof Double && expressionToConvert instanceof Integer) {
            return ((Double) propertyValue - (Integer) expressionToConvert);
        } else if (propertyValue instanceof Double && expressionToConvert instanceof Double) {
            return ((Double) propertyValue - (Double) expressionToConvert);
        } else {
            throw new IllegalArgumentException("Unsupported type");
        }
    }
}
