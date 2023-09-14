package action.expression.impl;

import action.expression.api.AbstractExpression;
import action.helper.function.api.HelperFunction;
import action.helper.function.impl.HelperFunctionFactory;
import action.context.api.Context;
import instance.entity.api.EntityInstance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class FunctionExpression extends AbstractExpression implements  Serializable {

    public FunctionExpression(String expression, EntityInstance entityInstance) {
        super(expression, entityInstance);
    }

    public FunctionExpression(String expression, EntityInstance primaryEntity, EntityInstance... entityInstance) {
        super(expression, primaryEntity, entityInstance);
    }

    @Override
    public AbstractExpression convert() {
        return new FunctionExpression(this.expression, primaryEntity, entityInstances.toArray(new EntityInstance[0]));
    }

    @Override
    public Object getValue(Context context) {
        HelperFunctionFactory valueGenerator = new HelperFunctionFactory();
        HelperFunction function = valueGenerator.convert(context, expression);

        return function.getValue();
    }

    @Override
    public String getExpressionString() {
        return expression;
    }
}
