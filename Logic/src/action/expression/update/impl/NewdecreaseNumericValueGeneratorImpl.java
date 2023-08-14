package action.expression.update.impl;

import action.expression.update.api.AbstractNewNumericValueGenerator;
import action.expression.update.api.NewNumericValueGenerator;

public class NewdecreaseNumericValueGeneratorImpl extends AbstractNewNumericValueGenerator implements NewNumericValueGenerator {
    @Override
    public Object calcUpdatedValue(Object expressionToConvert, Object propertyValue) {
        //---------------------TODO WITH EYAL----------------
        //TODO: Need to add a property range check -> might want to pass propertyInstance to this function
        // TODO: Need to add type check -> if propertyValue is int it cant get double
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
