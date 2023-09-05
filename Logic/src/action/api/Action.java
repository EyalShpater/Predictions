package action.api;

import api.DTOConvertible;
import definition.entity.api.EntityDefinition;

import action.context.api.Context;
import impl.ActionDTO;

import java.util.Map;

public interface Action extends DTOConvertible<ActionDTO> {
    EntityDefinition applyOn();

    EntityDefinition secondaryEntityForAction();

    ActionType getType();

    String getName();

    void invoke(Context context);

    Map<String, String> getArguments();
}
