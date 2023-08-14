package testmain;

import action.expression.api.Expression;
import action.expression.impl.ExpressionFactory;
import action.impl.DecreaseAction;
import action.impl.MultiplyAction;
import action.impl.SetAction;
import definition.entity.api.EntityDefinition;
import definition.entity.impl.EntityDefinitionImpl;
import definition.environment.api.EnvironmentVariableManager;
import definition.environment.impl.EnvironmentVariableManagerImpl;
import definition.property.api.PropertyDefinition;
import definition.property.api.PropertyType;
import definition.property.api.Range;
import definition.property.impl.PropertyDefinitionImpl;
import action.context.api.Context;
import action.context.impl.ContextImpl;
import execution.simulation.api.PredictionsLogic;
import instance.entity.manager.api.EntityInstanceManager;
import instance.entity.manager.impl.EntityInstanceManagerImpl;
import instance.enviornment.api.ActiveEnvironment;
import instance.enviornment.impl.ActiveEnvironmentImpl;
import instance.property.api.PropertyInstance;
import instance.property.impl.PropertyInstanceImpl;

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
        PropertyDefinition smokingInDayPropertyDefinition = new PropertyDefinitionImpl("smokingInDay", PropertyType.DOUBLE, false, 11.5);
        PropertyDefinition cancerPrecentage = new PropertyDefinitionImpl("cancerPrecentage", PropertyType.DOUBLE, true, new Range(0, 100));
        PropertyDefinition cancerAdvanement = new PropertyDefinitionImpl("cancerAdvancement", PropertyType.DOUBLE, true, new Range(0, 150));

        EntityDefinition smokerEntityDefinition = new EntityDefinitionImpl("smoker", 100);
        smokerEntityDefinition.addProperty(agePropertyDefinition);
        smokerEntityDefinition.addProperty(smokingInDayPropertyDefinition);
        smokerEntityDefinition.addProperty(cancerPrecentage);
        smokerEntityDefinition.addProperty(cancerAdvanement);

        Rule rule1 = new RuleImpl("rule 1");
        rule1.addAction(new IncreaseAction(smokerEntityDefinition, "age", "random(4)"));
        rule1.addAction(new IncreaseAction(smokerEntityDefinition, "smokingInDay", "3"));
        rule1.addAction(new decreaseAction(smokerEntityDefinition, "cancerPrecentage", "5"));
        rule1.addAction(new IncreaseAction(smokerEntityDefinition, "cancerAdvancement", "environment(tax-amount)"));

        EnvironmentVariableManager envVariablesManager = new EnvironmentVariableManagerImpl();
        EntityInstanceManager manager = new EntityInstanceManagerImpl();
        for (int i = 1; i <= 3; i++) {
            manager.create(smokerEntityDefinition);
            manager.getInstances().get(i - 1).setEntityFirstName("shavit" + (i - 1));
        }
        ActiveEnvironment activeEnvironment = envVariablesManager.createActiveEnvironment();
        PropertyDefinition taxAmountEnvironmentVariablePropertyDefinition = new PropertyDefinitionImpl("tax-amount", PropertyType.INT, true, new Range(10, 100));
        envVariablesManager.addEnvironmentVariable(taxAmountEnvironmentVariablePropertyDefinition);
        PropertyInstance taxAmountEnvironmentVariablePropertyInstance = new PropertyInstanceImpl(taxAmountEnvironmentVariablePropertyDefinition);
        activeEnvironment.addPropertyInstance(taxAmountEnvironmentVariablePropertyInstance);

        EntityInstance entityInstance = manager.getInstances().get(0);
        manager.
                getInstances().
                forEach(instance -> {
                    Context context = new ContextImpl(instance, manager, activeEnvironment);
                    System.out.println("first name :" + instance.getEntityFirstName());
                    System.out.println("before the change the value of age is :" + instance.getPropertyByName("age").getValue());
                    System.out.println("before the change the value of smokingInDay is :" + instance.getPropertyByName("smokingInDay").getValue());
                    System.out.println("before the change the value of cancerP is :" + instance.getPropertyByName("cancerPrecentage").getValue());
                    System.out.println("before the change the value of cancerAdvancement is :" + instance.getPropertyByName("cancerAdvancement").getValue());
                    rule1.invoke(context);
                    System.out.println("----------------------------------------------");
                    System.out.println("after the change the value of age is :" + instance.getPropertyByName("age").getValue());
                    System.out.println("after the change the value of smokingInDay is :" + instance.getPropertyByName("smokingInDay").getValue());
                    System.out.println("after the change the value of cancerP is :" + instance.getPropertyByName("cancerPrecentage").getValue());
                    System.out.println("after the change the value of cancerAdvancement is :" + instance.getPropertyByName("cancerAdvancement").getValue());
                    System.out.println("----------------------------------------------------------------------------------------------");

                });
*/
        PropertyDefinition agePropertyDefinition = new PropertyDefinitionImpl("age", PropertyType.INT, true, new Range(10, 50));
        PropertyDefinition smokingInDayPropertyDefinition = new PropertyDefinitionImpl("smokingInDay", PropertyType.DOUBLE, false, 11.5);
        PropertyDefinition cancerPrecentage = new PropertyDefinitionImpl("cancerPrecentage", PropertyType.DOUBLE, true, new Range(0, 100));
        PropertyDefinition cancerAdvanement = new PropertyDefinitionImpl("cancerAdvancement", PropertyType.DOUBLE, true, new Range(0, 150));
        PropertyDefinition isCancerPositive = new PropertyDefinitionImpl("cancerPositive", PropertyType.BOOLEAN,false,true);


        EntityDefinition smokerEntityDefinition = new EntityDefinitionImpl("smoker", 100);
        smokerEntityDefinition.addProperty(agePropertyDefinition);
        smokerEntityDefinition.addProperty(smokingInDayPropertyDefinition);
        smokerEntityDefinition.addProperty(cancerPrecentage);
        smokerEntityDefinition.addProperty(cancerAdvanement);
        smokerEntityDefinition.addProperty(isCancerPositive);

        Rule rule1 = new RuleImpl("rule 1");
        rule1.addAction(new IncreaseAction(smokerEntityDefinition, "age", "2"));
        rule1.addAction(new SetAction(smokerEntityDefinition, "cancerPositive", "true"));
        rule1.addAction(new IncreaseAction(smokerEntityDefinition, "smokingInDay", "3.5"));
        rule1.addAction(new DecreaseAction(smokerEntityDefinition, "cancerPrecentage", "random(15)"));
        rule1.addAction(new IncreaseAction(smokerEntityDefinition, "cancerAdvancement", "environment(tax-amount)"));
        rule1.addAction(new MultiplyAction(smokerEntityDefinition,"cancerAdvancement" ,"environment(tax-amount)" , "random(2)"));

        EnvironmentVariableManager envVariablesManager = new EnvironmentVariableManagerImpl();
        EntityInstanceManager manager = new EntityInstanceManagerImpl();
        for (int i = 1; i <= 3; i++) {
            manager.create(smokerEntityDefinition);
            manager.getInstances().get(i - 1).setEntityFirstName("shavit" + (i - 1));
        }
        ActiveEnvironment activeEnvironment = envVariablesManager.createActiveEnvironment();
        PropertyDefinition taxAmountEnvironmentVariablePropertyDefinition = new PropertyDefinitionImpl("tax-amount", PropertyType.INT, true, new Range(10, 100));
        envVariablesManager.addEnvironmentVariable(taxAmountEnvironmentVariablePropertyDefinition);
        PropertyInstance taxAmountEnvironmentVariablePropertyInstance = new PropertyInstanceImpl(taxAmountEnvironmentVariablePropertyDefinition);
        activeEnvironment.addPropertyInstance(taxAmountEnvironmentVariablePropertyInstance);

        EntityInstance entityInstance = manager.getInstances().get(0);
        manager.
                getInstances().
                forEach(instance -> {
                    Context context = new ContextImpl(instance, manager, activeEnvironment);
                    System.out.println("first name :" + instance.getEntityFirstName());
                    System.out.println("before the change the value of age is :" + instance.getPropertyByName("age").getValue());
                    System.out.println("before the change the value of smokingInDay is :" + instance.getPropertyByName("smokingInDay").getValue());
                    System.out.println("before the change the value of cancerP is :" + instance.getPropertyByName("cancerPrecentage").getValue());
                    System.out.println("before the change the value of cancerAdvancement is :" + instance.getPropertyByName("cancerAdvancement").getValue());
                    rule1.invoke(context);
                    System.out.println("----------------------------------------------");
                    System.out.println("after the change the value of age is :" + instance.getPropertyByName("age").getValue());
                    System.out.println("after the change the value of smokingInDay is :" + instance.getPropertyByName("smokingInDay").getValue());
                    System.out.println("after the change the value of cancerP is :" + instance.getPropertyByName("cancerPrecentage").getValue());
                    System.out.println("after the change the value of cancerAdvancement is :" + instance.getPropertyByName("cancerAdvancement").getValue());
                    System.out.println("----------------------------------------------------------------------------------------------");

                });

        /*System.out.println("hi");
        PropertyDefinition p1 = new PropertyDefinitionImpl("name", PropertyType.STRING, false, "Avi");
        PropertyDefinition p2 = new PropertyDefinitionImpl("age", PropertyType.INT, true, new Range(10, 50));

        EntityDefinition student = new EntityDefinitionImpl("Student", 140);
        student.addProperty(p1);
        student.addProperty(p2);

        PropertyInstance inst1 = new PropertyInstanceImpl(p1);
        PropertyInstance inst2 = new PropertyInstanceImpl(p2);

        EntityInstanceManager manager = new EntityInstanceManagerImpl();
        manager.create(student);

        ActiveEnvironment environment = new ActiveEnvironmentImpl();
        environment.addPropertyInstance(inst1);
        environment.addPropertyInstance(inst2);

        String expression = "name";
        Expression value = new ExpressionFactory(expression, manager.getInstances().get(0));
        System.out.println(value.getValue(new ContextImpl(manager.getInstances().get(0), manager, environment)));*/

    }
}
