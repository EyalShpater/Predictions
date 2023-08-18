package action.impl.condition;

import action.context.api.Context;

public interface Condition {
    boolean evaluate(Context context);
    String getOperationSign();
}
