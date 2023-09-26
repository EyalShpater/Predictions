package action.api;

import action.context.api.Context;
import action.second.entity.SecondaryEntity;
import definition.entity.api.EntityDefinition;

import impl.ActionDTO;
import impl.EntityDefinitionDTO;
import instance.entity.api.EntityInstance;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractAction implements Action, Serializable {
    private final EntityDefinition primaryEntity;
    private final SecondaryEntity secondaryEntity;
    private final ActionType type;

    public AbstractAction(EntityDefinition primaryEntity, ActionType type) {
        this(primaryEntity, null, type);
    }

    public AbstractAction(EntityDefinition primaryEntity, SecondaryEntity secondaryEntity, ActionType type) {
        this.primaryEntity = primaryEntity;
        this.secondaryEntity = secondaryEntity;
        this.type = type;
    }

    @Override
    public EntityDefinition applyOn() {
        return primaryEntity;
    }

    @Override
    public ActionType getType() {
        return type;
    }

    @Override
    public String getName() {
        return type.name();
    }

    @Override
    public boolean isIncludesSecondaryEntity() {
        return secondaryEntity != null;
    }

    @Override
    public EntityDefinition getSecondaryEntity() {
        return secondaryEntity != null ?
                secondaryEntity.getSecondaryEntity() :
                null;
    }

    @Override
    public ActionDTO convertToDTO() {
        EntityDefinitionDTO secondaryDTO = secondaryEntity != null ?
                secondaryEntity.getSecondaryEntity().convertToDTO() :
                null;

        return new ActionDTO(
                type.name(),
                primaryEntity.convertToDTO(),
                secondaryDTO,
                getArguments(),
                getAdditionalInformation()
        );
    }

    @Override
    public void invoke(Context context) {
        if (isActionValid(context)) {
            if (isSecondaryEntityExist()) {
                List<EntityInstance> secondaryEntitiesInstances = context.getSecondEntityFilteredList(secondaryEntity);

                if (secondaryEntitiesInstances.isEmpty()) {
                    try {
                        apply(context);
                    } catch (Exception ignored) {
                    }
                }

                for (EntityInstance secondary : secondaryEntitiesInstances) {
                    context.setSecondaryEntity(secondary);
                    apply(context);
                }
            } else {
                apply(context);
            }
        }
    }

    protected abstract void apply(Context context);

    protected Boolean isSecondaryEntityExist() {
        return secondaryEntity != null;
    }

    private boolean isActionValid(Context context) {
        EntityInstance primaryEntity = context.getPrimaryEntityInstance();

        boolean isAlive = primaryEntity.isAlive();
        boolean isSameEntities = primaryEntity.getName().equals(context.getPrimaryEntityInstance().getName());

        return isAlive && isSameEntities;
    }

}
