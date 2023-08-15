package action.impl.condition.impl.multiple;

import action.impl.condition.Condition;

public class And extends MultipleCondition{

    public And(Condition condition1, Condition condition2) {
        super(condition1, condition2);
    }

    @Override
    public String getOperationSign() {
        return "and";
    }

    @Override
    protected boolean evaluate(Condition condition1, Condition condition2) {
        return condition1.evaluate() && condition2.evaluate();
    }
}

