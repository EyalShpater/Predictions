package action.api;

import action.second.entity.SecondEntity;
import api.DTOConvertible;
import definition.entity.api.EntityDefinition;

import action.context.api.Context;
import impl.ActionDTO;
import instance.entity.api.EntityInstance;

import java.util.List;
import java.util.Map;

public interface Action extends DTOConvertible<ActionDTO> {
    EntityDefinition applyOn();

    EntityDefinition secondaryEntityForAction();

    ActionType getType();

    String getName();

    void invoke(Context context);

    Map<String, String> getArguments();

    SecondEntity getSecondaryEntityForAction();

    Boolean isSecondaryEntityExist();

    List<EntityInstance> getSecondEntityFilteredList(Context context);
}
