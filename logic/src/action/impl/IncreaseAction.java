package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import definition.entity.api.EntityDefinition;

public class IncreaseAction extends AbstractAction {
    private final String propertyName;
    private final String byExpression;

    public IncreaseAction(EntityDefinition entity, String propertyName, String byExpression) {
        super(entity, ActionType.INCREASE);
        this.propertyName = propertyName;
        this.byExpression = byExpression;
    }

    @Override
    public void invoke() {
        //entity.getPropertyByName().
    }
}
