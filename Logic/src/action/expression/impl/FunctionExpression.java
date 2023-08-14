package action.expression.impl;

import action.expression.api.AbstractExpression;
import action.helper.function.api.HelperFunction;
import action.helper.function.impl.HelperFunctionFactory;
import action.context.api.Context;
import instance.entity.api.EntityInstance;

public class FunctionExpression extends AbstractExpression {

    public FunctionExpression(String expression, EntityInstance entityInstance) {
        super(expression, entityInstance);
    }

    @Override
    public AbstractExpression convert() {
        return new FunctionExpression(this.expression, this.entityInstance);
    }

    @Override
    public Object getValue(Context context) {
        HelperFunctionFactory valueGenerator = new HelperFunctionFactory();
        HelperFunction function= valueGenerator.convert(context, expression);

        return function.getValue();
    }

    @Override
    public String getExpressionString() {
        return expression;
    }
}
