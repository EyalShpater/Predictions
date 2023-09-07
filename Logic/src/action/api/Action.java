package action.api;

import action.second.entity.SecondaryEntity;
import api.DTOConvertible;
import definition.entity.api.EntityDefinition;

import action.context.api.Context;
import impl.ActionDTO;
import instance.entity.api.EntityInstance;

import java.util.List;
import java.util.Map;

public interface Action extends DTOConvertible<ActionDTO> {
    //EntityDefinition applyOn();

    //EntityDefinition getSecondaryEntityInstanceForAction();

    ActionType getType();

    String getName();

    void invoke(Context context);

    Map<String, String> getArguments();

    //SecondaryEntity getSecondaryEntityForAction();

    //Boolean isSecondaryEntityExist();

    List<EntityInstance> getSecondEntityFilteredList(Context context);
}
