package action.impl.condition;

import action.api.Action;
import action.context.api.Context;

import java.util.Map;

public interface Condition {
    boolean evaluate(Context context);
    String getOperationSign();

    Map<String, String> getArguments();
}
