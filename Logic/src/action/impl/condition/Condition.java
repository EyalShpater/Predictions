package action.impl.condition;

import action.context.api.Context;
import instance.entity.api.EntityInstance;

import java.util.Map;

public interface Condition {
    boolean evaluate(Context context, EntityInstance secondEntityInstance);

    String getOperationSign();

    Map<String, String> getArguments();

    boolean isSingleCondition();
}
