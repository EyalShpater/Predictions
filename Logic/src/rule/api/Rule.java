package rule.api;

import action.api.Action;
import action.context.api.Context;
import api.DTOConvertible;


public interface Rule extends DTOConvertible {
    String getName();

    boolean isActive(int tickNumber, double probability);

    void invoke(Context context);

    void addAction(Action action);
}
