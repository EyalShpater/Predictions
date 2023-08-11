package Main;

import api.DTO;
import definition.entity.api.EntityDefinition;
import definition.entity.impl.EntityDefinitionImpl;
import definition.property.api.PropertyDefinition;
import definition.property.api.PropertyType;
import definition.property.api.Range;
import definition.property.impl.PropertyDefinitionImpl;
import environment.variable.EnvironmentVariableDTO;
import execution.simulation.api.PredictionsLogic;
import execution.simulation.impl.PredictionsLogicImpl;
import instance.entity.manager.api.EntityInstanceManager;
import instance.entity.manager.impl.EntityInstanceManagerImpl;
import instance.property.api.PropertyInstance;
import instance.property.impl.PropertyInstanceImpl;
import temporary.SomeObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
//        PropertyDefinition p1 = new PropertyDefinitionImpl("name", PropertyType.STRING, false, "Avi");
//        PropertyDefinition p2 = new PropertyDefinitionImpl("age", PropertyType.INT, true, new Range(10, 50));
//
//        EntityDefinition student = new EntityDefinitionImpl("Student", 140);
//        student.addProperty(p1);
//        student.addProperty(p2);
//
//        System.out.println(student);
//
//        System.out.println("===============");
//
//        PropertyInstance inst1 = new PropertyInstanceImpl(p1);
//        PropertyInstance inst2 = new PropertyInstanceImpl(p2);
//
//        EntityInstanceManager manager = new EntityInstanceManagerImpl();
//
//        for (int i = 1; i <= 50; i++) {
//            manager.create(student);
//        }
//
//        for (int i = 0; i < 100; i++) {
//            System.out.println(manager.getInstances().get(i).getPropertyByName("name").getValue());
//            System.out.println(manager.getInstances().get(i).getPropertyByName("age").getValue());
//        }

        PredictionsLogic system = new PredictionsLogicImpl();
        system.loadXML("bla bla bla");
        List<DTO> values = system.getEnvironmentVariablesToSet();
        system.setEnvironmentVariablesValues(values);
        system.runNewSimulation();
        system.getSimulationDetails();
//
//        PropertyDefinition p1 = new PropertyDefinitionImpl("name", PropertyType.STRING, false, "Avi");
//        PropertyDefinition p2 = new PropertyDefinitionImpl("age", PropertyType.INT, true, new Range(10, 50));
//
//        List<PropertyDefinition> properties = new ArrayList<>();
//        properties.add(p1);
//        properties.add(p2);
//
//        List<DTO> dto = properties.stream()
//                .map(propertyDefinition -> new EnvironmentVariableDTO(
//                        propertyDefinition.getName(),
//                        propertyDefinition.getType().toString(),
//                        propertyDefinition.getRange() != null
//                                ? propertyDefinition.getRange().getMin()
//                                : null,
//                        propertyDefinition.getRange() != null
//                                ? propertyDefinition.getRange().getMax()
//                                : null,
//                        propertyDefinition.isValueInitializeRandomly(),
//                        propertyDefinition.getDefaultValue()
//                ))
//                .collect(Collectors.toList());
//
//        System.out.println(dto);

    }
}
