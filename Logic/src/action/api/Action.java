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
    ActionType getType();

    String getName();

    void invoke(Context context);

    Map<String, String> getArguments();

    Map<String, String> getAdditionalInformation();

    public EntityDefinition applyOn();

    boolean isIncludesSecondaryEntity();

    EntityDefinition getSecondaryEntity();
}
