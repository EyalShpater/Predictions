package execution.simulation.xml.reader.impl;

import definition.property.api.PropertyDefinition;
import definition.property.api.PropertyType;
import definition.property.api.Range;
import definition.property.impl.PropertyDefinitionImpl;
import definition.world.api.World;
import resources.xml.ex2.generated.PRDEnvProperty;
import resources.xml.ex2.generated.PRDRange;
import resources.xml.ex2.generated.PRDWorld;
/*import resources.xml.ex1.generated.PRDEnvProperty;
import resources.xml.ex1.generated.PRDRange;
import resources.xml.ex1.generated.PRDWorld;*/

import java.util.List;

public class EnvReader {

    public void read (PRDWorld prdWorld , World world){
        List<PRDEnvProperty> envVarList = prdWorld.getPRDEnvironment().getPRDEnvProperty();
        envVarList.forEach(prdEnvVar -> readDataFromPRDEnvPropertyToEnvVarManager(prdEnvVar , world));
    }

    private void readDataFromPRDEnvPropertyToEnvVarManager(PRDEnvProperty prdEnvVar, World world) {
        PropertyDefinition newEnvVar = createPropertyDefinitionFromPRDEnvProperty( prdEnvVar );
        world.addEnvironmentVariable( newEnvVar );
    }

    private PropertyDefinition createPropertyDefinitionFromPRDEnvProperty(PRDEnvProperty prdEnvVar) {
        Range range = checkRangeExist(prdEnvVar.getPRDRange());
        PropertyDefinition newEnvVar = null;
        if ( range == null ){
            newEnvVar = createPropertyWithOutRange( prdEnvVar );
        }else{
            newEnvVar = createPropertyWithRange( prdEnvVar , range );
        }
        return newEnvVar;
    }

    private PropertyDefinition createPropertyWithRange(PRDEnvProperty prdEnvVar, Range range) {
        PropertyType type = createPropertyType(prdEnvVar.getType());
        return new PropertyDefinitionImpl( prdEnvVar.getPRDName() , type , true , range ) ;
    }

    private PropertyDefinition createPropertyWithOutRange(PRDEnvProperty prdEnvVar) {
        PropertyType type = createPropertyType(prdEnvVar.getType());
        return new PropertyDefinitionImpl( prdEnvVar.getPRDName() , type , true ) ;
    }

    private PropertyType createPropertyType(String type) {
        switch (type) {
            case "decimal":
                return PropertyType.INT;
            case "float":
                return PropertyType.DOUBLE;
            case "boolean":
                return PropertyType.BOOLEAN;
            default:
                return PropertyType.STRING;
        }
    }

    private Range checkRangeExist(PRDRange prdRange) {
        Range range = null;
        if ( prdRange != null){
            range = new Range(prdRange.getFrom() , prdRange.getTo());
        }
        return range;
    }
}
