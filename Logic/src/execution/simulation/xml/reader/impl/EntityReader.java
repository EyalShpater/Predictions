package execution.simulation.xml.reader.impl;

import definition.entity.api.EntityDefinition;
import definition.entity.impl.EntityDefinitionImpl;
import definition.world.api.World;
import resources.xml.ex2.generated.PRDEntity;
import resources.xml.ex2.generated.PRDProperty;
import resources.xml.ex2.generated.PRDWorld;
/*import resources.xml.ex1.generated.PRDEntity;
import resources.xml.ex1.generated.PRDProperty;
import resources.xml.ex1.generated.PRDWorld;*/

import java.util.List;

public class EntityReader {

    public void read (PRDWorld prdWorld , World world){
        List<PRDEntity> entityList = prdWorld.getPRDEntities().getPRDEntity();
        entityList.forEach(prdEntity -> readDataFromPRDEntityToEntityDefinition(prdEntity , world));
    }

    private void readDataFromPRDEntityToEntityDefinition(PRDEntity prdEntity, World world) {

        //  TODO: Change EntityDefinition to fit no population for xml

        EntityDefinition newEntity = new EntityDefinitionImpl(prdEntity.getName());

        //Object that reads into Property
        List<PRDProperty> propertyListOfPRDEntity = prdEntity.getPRDProperties().getPRDProperty();
        for (PRDProperty prdProperty : propertyListOfPRDEntity) {
            PropertyReader propertyReader = new PropertyReader();
            newEntity.addProperty(propertyReader.read(prdProperty));
        }

        world.addEntity(newEntity);
    }
}
