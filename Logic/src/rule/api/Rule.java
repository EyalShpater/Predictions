package rule.api;

import action.api.Action;
import action.context.api.Context;
import api.DTOConvertible;
import impl.RuleDTO;

import java.util.List;

public interface Rule extends DTOConvertible<RuleDTO> {
    String getName();

    boolean isActive(int tickNumber, double probability);

    void invoke(Context context);

    void addAction(Action action);

    List<Action> getActions();
}
