package action.expression.impl;

import action.expression.api.AbstractExpression;
import action.helper.function.api.HelperFunctionValueGenerator;
import action.helper.function.impl.HelperFunctionFactory;
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
    public Object getValue() {
        HelperFunctionValueGenerator valueGenerator = new HelperFunctionFactory();
        return null;
    }
}
