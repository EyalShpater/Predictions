package action.expression.update.api;

public interface NewNumericValueGenerator {

    Object calcUpdatedValue(Object expressionToConvert, Object propertyValue);
}
