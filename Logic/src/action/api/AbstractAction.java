package action.api;

import definition.entity.api.EntityDefinition;

import impl.ActionDTO;
import instance.entity.api.EntityInstance;
import java.io.Serializable;

public abstract class AbstractAction implements Action , Serializable {
    private final EntityDefinition entity;
    private final ActionType type;

    public AbstractAction(EntityDefinition entity, ActionType type) {
        this.entity = entity;
        this.type = type;
    }

    @Override
    public EntityDefinition applyOn() {
        return entity;
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
                entity.convertToDTO(),
                null, // todo: should add secondary entity to the class
                getArguments()
        );
    }
}
