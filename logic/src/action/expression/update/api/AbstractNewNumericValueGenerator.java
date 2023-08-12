package action.expression.update.api;

public abstract class AbstractNewNumericValueGenerator implements NewNumericValueGenerator {
    @Override
    public abstract Object calcUpdatedValue(Object expressionToConvert, Object propertyValue);
}
