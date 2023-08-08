package expression.api;

public abstract class AbstractExpression implements expression {
    private String byExpression;
    private expressionType expressionType;

    public AbstractExpression(String byExpression) {
        this.byExpression = byExpression;
        expressionTypeDecipherer expDecipher = new expressionTypeDecipherer();
        this.expressionType = expDecipher.deciphere(byExpression);
    }
}
