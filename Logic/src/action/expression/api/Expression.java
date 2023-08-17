package action.expression.api;

import action.context.api.Context;

public interface Expression {
    //ExpressionType getType();
    Object getValue(Context context);

    String getExpressionString();

}
