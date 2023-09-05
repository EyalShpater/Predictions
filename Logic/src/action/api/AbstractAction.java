package action.api;

import definition.entity.api.EntityDefinition;

import impl.ActionDTO;
import instance.entity.api.EntityInstance;
import java.io.Serializable;

public abstract class AbstractAction implements Action, Serializable {
    private final EntityDefinition mainEntity;

    private EntityDefinition secondEntity = null;

    private int secondEntityPopulationCount = 0;

    private final ActionType type;

    public AbstractAction(EntityDefinition mainEntity, ActionType type) {
        this.mainEntity = mainEntity;
        this.secondEntity = secondEntity;
        this.type = type;
    }

    public AbstractAction(EntityDefinition mainEntity, EntityDefinition secondEntity, int populationCount, ActionType type) {
        this.mainEntity = mainEntity;
        this.secondEntity = secondEntity;
        this.secondEntityPopulationCount = populationCount;
        this.type = type;
    }

    @Override
    public EntityDefinition applyOn() {
        return mainEntity;
    }

    @Override
    public EntityDefinition secondaryEntityForAction() {
        return secondEntity;
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
}
