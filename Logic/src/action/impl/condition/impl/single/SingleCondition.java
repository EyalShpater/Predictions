package action.impl.condition.impl.single;

import action.context.api.Context;
import action.impl.condition.Condition;

import java.io.Serializable;

public abstract class SingleCondition implements Condition  , Serializable {
    protected String expression1;
    protected String expression2;

    public SingleCondition(String expression1, String expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public boolean evaluate(Context context) {
        return evaluate(expression1, expression2, context);
    }

    abstract protected boolean evaluate(String expression1, String expression2, Context context);
}