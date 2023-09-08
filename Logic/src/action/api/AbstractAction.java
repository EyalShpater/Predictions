package action.api;

import action.context.api.Context;
import action.second.entity.SecondaryEntity;
import definition.entity.api.EntityDefinition;

import impl.ActionDTO;
import instance.entity.api.EntityInstance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractAction implements Action, Serializable {
    private final EntityDefinition primaryEntity;
    private final SecondaryEntity secondaryEntity;
    private final ActionType type;
    private EntityInstance primaryEntityInstance;
    protected List<EntityInstance> secondaryEntitiesInstances;

    public AbstractAction(EntityDefinition primaryEntity, ActionType type) {
        this(primaryEntity, null, type);
    }

    public AbstractAction(EntityDefinition primaryEntity, SecondaryEntity secondaryEntity, ActionType type) {
        this.primaryEntity = primaryEntity;
        this.secondaryEntity = secondaryEntity;
        this.type = type;
    }

   /* @Override
    public EntityDefinition applyOn() {
        return primaryEntity;
    }*/

    @Override
    public ActionType getType() {
        return type;
    }

    @Override
    public String getName() {
        return type.name();
    }

    @Override
    public ActionDTO convertToDTO() {
        return new ActionDTO(
                type.name(),
                primaryEntity.convertToDTO(),
                null,
                getArguments()
        );
    }
/*

    @Override
    public EntityDefinition getSecondaryEntityInstanceForAction() {
        return secondaryEntity.getSecondEntity();
    }

    @Override
    public SecondaryEntity getSecondaryEntityForAction() {
        return secondaryEntity;
    }

    @Override
    public Boolean isSecondaryEntityExist() {
        return secondaryEntity != null;
    }
*/



    @Override
    public void invoke(Context context) {
        if (context.getEntityInstance().getName().equals(primaryEntity.getName())) {
            setEntitiesInstances(context);
            applyAction(context);
        }
    }

    private void setEntitiesInstances(Context context) {
        if (isSecondaryEntityExist()) {
            primaryEntityInstance = context.getEntityInstance();
            secondaryEntitiesInstances = context.getSecondEntityFilteredList(secondaryEntity);
        }
    }

    protected abstract void applyAction(Context context);

    protected Boolean isSecondaryEntityExist() {
        return secondaryEntity != null;
    }
}
