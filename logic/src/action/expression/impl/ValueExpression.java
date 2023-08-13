package action.expression.impl;

import action.expression.api.AbstractExpression;
import action.context.api.Context;
import instance.entity.api.EntityInstance;


public class ValueExpression extends AbstractExpression {
    public ValueExpression(String byExpression, EntityInstance entityInstance) {
        super(byExpression, entityInstance);
    }

    @Override
    public AbstractExpression convert() {
        return new ValueExpression(this.expression, this.entityInstance);
    }

    @Override
    public Object getValue(Context context) {
        Object value = expression;

        if (isBoolean()) {
            value = expression.equals("true");
        } else {
            try {
                value = Double.parseDouble(expression);
            } catch (NumberFormatException ignored) {
            }

            try {
                value = Integer.parseInt(expression);
            } catch (NumberFormatException ignored) {
            }
        }

        return value;
    }

    private boolean isBoolean() {
        return expression.equals("true") || expression.equals("false");
    }

    @Override
    public String getExpressionString() {
        return expression;
    }
}
