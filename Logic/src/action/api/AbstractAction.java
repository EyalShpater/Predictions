package action.api;

import action.second.entity.SecondEntity;
import definition.entity.api.EntityDefinition;

import impl.ActionDTO;
import instance.entity.api.EntityInstance;
import java.io.Serializable;

public abstract class AbstractAction implements Action, Serializable {
    private final EntityDefinition mainEntity;

    private SecondEntity secondEntity;

    private final ActionType type;

    public AbstractAction(EntityDefinition mainEntity, ActionType type) {
        this.mainEntity = mainEntity;
        this.type = type;
    }

    public AbstractAction(EntityDefinition mainEntity, SecondEntity secondEntity, ActionType type) {
        this.mainEntity = mainEntity;
        this.secondEntity = secondEntity;
        this.type = type;
    }

    @Override
    public EntityDefinition applyOn() {
        return mainEntity;
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
    public ActionDTO convertToDTO() {
        return new ActionDTO(
                type.name(),
                mainEntity.convertToDTO(),
                null, // todo: should add secondary entity to the class
                getArguments()
        );
    }

    @Override
    public EntityDefinition secondaryEntityForAction() {
        return secondEntity.getSecondEntity();
    }

    @Override
    public SecondEntity getSecondaryEntityForAction() {
        return secondEntity;
    }
}
