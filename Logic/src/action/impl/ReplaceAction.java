package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import action.api.ReplaceMode;
import action.context.api.Context;
import definition.entity.api.EntityDefinition;

import java.util.Map;

public class ReplaceAction extends AbstractAction {

    ReplaceMode mode;

    EntityDefinition entityToCreate;

    public ReplaceAction(EntityDefinition entityToKill, EntityDefinition entityToCreate, String mode) {
        super(entityToKill, null, ActionType.REPLACE);
        this.entityToCreate = entityToCreate;
        this.mode = mode.equals("scratch") ? ReplaceMode.SCRATCH : mode.equals("derived") ? ReplaceMode.DERIVED : null;

        if (this.mode != ReplaceMode.SCRATCH && this.mode != ReplaceMode.DERIVED) {
            throw new IllegalArgumentException("Invalid mode: " + mode);
        }
    }

    Override
    protected void apply(Context context) {
        // Find the entity to kill
        EntityInstance entityToKill = context.getEntityInstance();

        // Set "isAlive" to false to "kill" the entity
        entityToKill.getPropertyByName("isAlive").setValue(false);

        // Create a new entity based on entityToCreate definition
        EntityInstance newEntity = context.getEntityInstanceManager()
                .createEntityInstance(entityToCreate);

        // Determine the mode and create the entity accordingly
        if (mode == ReplaceMode.SCRATCH) {
            // Create the entity from scratch
            // You may need to initialize its properties as needed
        } else if (mode == ReplaceMode.DERIVED) {
            // Create the entity and copy properties from the killed entity
            for (String propertyName : entityToKill.getPropertyNames()) {
                // Check if the property exists in entityToCreate
                if (newEntity.getPropertyNames().contains(propertyName)) {
                    // Copy property value from entityToKill to newEntity
                    Object value = entityToKill.getPropertyByName(propertyName).getValue();
                    newEntity.getPropertyByName(propertyName).setValue(value);
                }
            }
        }

        // Create a list to store entities to add
        List<EntityInstance> entitiesToAdd = new ArrayList<>();

        // Add the newEntity to the list of entities to add
        entitiesToAdd.add(newEntity);

        // Add any other entities you want to add to entitiesToAdd here

        // Add the entities to the list of entities managed by entityInstanceManager
        context.getEntityInstanceManager().addAllEntityInstances(entitiesToAdd);
    }

    @Override
    public Map<String, String> getArguments() {
        return null;
    }

    @Override
    public Map<String, String> getAdditionalInformation() {
        return null;
    }
}
