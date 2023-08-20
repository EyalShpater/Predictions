package action.expression.api;

import action.context.api.Context;

public interface Expression {

    Object getValue(Context context);

    String getExpressionString();

}
