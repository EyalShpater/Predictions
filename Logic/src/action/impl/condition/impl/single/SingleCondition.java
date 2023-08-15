package action.impl.condition.impl.single;

import action.context.api.Context;
import action.expression.api.Expression;
import action.expression.impl.ExpressionFactory;
import action.impl.condition.Condition;
import instance.entity.api.EntityInstance;
import instance.property.api.PropertyInstance;

public abstract class SingleCondition implements Condition {
    protected Context context;
    protected Expression expression1;
    protected Expression expression2;

    public SingleCondition(Context context, String expression1, String expression2) {
        this.context = context;
        this.expression1 = new ExpressionFactory(expression1, context.getEntityInstance());
        this.expression2 = new ExpressionFactory(expression2, context.getEntityInstance());
    }

    @Override
    public boolean evaluate() {
        return evaluate(expression1, expression2);
    }

    abstract protected boolean evaluate(Expression expression1, Expression expression2);
}