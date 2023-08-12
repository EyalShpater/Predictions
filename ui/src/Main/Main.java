package Main;

import action.impl.decreaseAction;
import api.DTO;
import definition.entity.api.EntityDefinition;
import definition.entity.impl.EntityDefinitionImpl;
import definition.environment.api.EnvironmentVariableManager;
import definition.environment.impl.EnvironmentVariableManagerImpl;
import definition.property.api.PropertyDefinition;
import definition.property.api.PropertyType;
import definition.property.api.Range;
import definition.property.impl.PropertyDefinitionImpl;
import environment.variable.EnvironmentVariableDTO;
import execution.context.api.Context;
import execution.context.impl.ContextImpl;
import execution.simulation.api.PredictionsLogic;
import execution.simulation.impl.PredictionsLogicImpl;
import instance.entity.manager.api.EntityInstanceManager;
import instance.entity.manager.impl.EntityInstanceManagerImpl;
import instance.enviornment.api.ActiveEnvironment;
import instance.property.api.PropertyInstance;
import instance.property.impl.PropertyInstanceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import rule.api.Rule;
import rule.impl.RuleImpl;
import action.impl.IncreaseAction;
import instance.entity.api.EntityInstance;

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
  /*

     THIS IS SHAVIT'S MAIN FUNCTION FOR CHECKING RULE AND ACTIONS
        PropertyDefinition agePropertyDefinition = new PropertyDefinitionImpl("age", PropertyType.INT, true, new Range(10, 50));
        PropertyDefinition smokingInDayPropertyDefinition = new PropertyDefinitionImpl("smokingInDay", PropertyType.DOUBLE, false,11.5 );
        PropertyDefinition cancerPrecentage = new PropertyDefinitionImpl("cancerPrecentage", PropertyType.DOUBLE, true, new Range(0, 100) );
        PropertyDefinition cancerAdvanement = new PropertyDefinitionImpl("cancerAdvancement", PropertyType.DOUBLE, true, new Range(0, 150) );

        EntityDefinition smokerEntityDefinition = new EntityDefinitionImpl("smoker", 100);
        smokerEntityDefinition.addProperty(agePropertyDefinition);
        smokerEntityDefinition.addProperty(smokingInDayPropertyDefinition);
        smokerEntityDefinition.addProperty(cancerPrecentage);
        smokerEntityDefinition.addProperty(cancerAdvanement);

        Rule rule1 = new RuleImpl("rule 1");
        rule1.addAction(new IncreaseAction(smokerEntityDefinition, "age", "1"));
        rule1.addAction(new IncreaseAction(smokerEntityDefinition, "smokingInDay", "3"));
        rule1.addAction(new decreaseAction(smokerEntityDefinition, "cancerPrecentage", "5"));
        rule1.addAction(new IncreaseAction(smokerEntityDefinition, "cancerAdvancement", "cancerPrecentage"));

        EnvironmentVariableManager envVariablesManager = new EnvironmentVariableManagerImpl();
        EntityInstanceManager manager = new EntityInstanceManagerImpl();
        for (int i = 1; i <= 3; i++) {
            manager.create(smokerEntityDefinition);
            manager.getInstances().get(i-1).setEntityFirstName("shavit"+(i-1));
        }
        ActiveEnvironment activeEnvironment = envVariablesManager.createActiveEnvironment();
        PropertyDefinition taxAmountEnvironmentVariablePropertyDefinition = new PropertyDefinitionImpl("tax-amount", PropertyType.INT, true, new Range(10, 100));
        envVariablesManager.addEnvironmentVariable(taxAmountEnvironmentVariablePropertyDefinition);
        PropertyInstance taxAmountEnvironmentVariablePropertyInstance = new PropertyInstanceImpl(taxAmountEnvironmentVariablePropertyDefinition);
        activeEnvironment.addPropertyInstance(taxAmountEnvironmentVariablePropertyInstance);

        EntityInstance entityInstance = manager.getInstances().get(0);
        manager.
                getInstances().
                forEach(instance ->{
                    Context context =new ContextImpl(instance , manager , activeEnvironment );
                    System.out.println("first name :"+ instance.getEntityFirstName());
                    System.out.println("before the change the value of age is :"+instance.getPropertyByName("age").getValue());
                    System.out.println("before the change the value of smokingInDay is :"+instance.getPropertyByName("smokingInDay").getValue());
                    System.out.println("before the change the value of cancerP is :"+instance.getPropertyByName("cancerPrecentage").getValue());
                    System.out.println("before the change the value of cancerAdvancement is :"+instance.getPropertyByName("cancerAdvancement").getValue());
                    rule1.invoke(context);
                    System.out.println("----------------------------------------------");
                    System.out.println("after the change the value of age is :"+instance.getPropertyByName("age").getValue());
                    System.out.println("after the change the value of smokingInDay is :"+instance.getPropertyByName("smokingInDay").getValue());
                    System.out.println("after the change the value of cancerP is :"+instance.getPropertyByName("cancerPrecentage").getValue());
                    System.out.println("after the change the value of cancerAdvancement is :"+instance.getPropertyByName("cancerAdvancement").getValue());
                    System.out.println("----------------------------------------------------------------------------------------------");

                });
*/
    }

}
