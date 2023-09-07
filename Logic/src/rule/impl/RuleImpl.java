package rule.impl;

import action.api.Action;
import action.context.api.Context;
import action.second.entity.SecondEntity;
import impl.RuleDTO;
import instance.entity.api.EntityInstance;
import rule.api.Activation;
import rule.api.Rule;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


public class RuleImpl implements Rule , Serializable {
    private final String name;
    private SortedSet<String> relevantEntities; //todo: split to mainEntity and secondaryEntity (may be null)
    private SortedSet<String> relevantSecondaryEntities;
    private List<Action> actions;
    private Activation activation;


    public RuleImpl(String name, Activation activation, String... relevantEntity) {
        this.name = name;
        this.actions = new ArrayList<>();
        this.activation = activation;
        initRelevantEntities(Arrays.asList(relevantEntity));
        relevantSecondaryEntities = null;
    }

    //TODO: use this ctor for secondary entities
    public RuleImpl(String name, Activation activation, List<String> relevantEntity, List<String> relevantSecondaryEntity) {
        this.name = name;
        this.actions = new ArrayList<>();
        this.activation = activation;
        initRelevantEntities(relevantEntity);
        initRelevantSecondaryEntities(relevantSecondaryEntity);
    }

    public RuleImpl(RuleDTO dto) {
        this(dto.getName(),
                new ActivationImpl(dto.getTicks(), dto.getProbability()),
                dto.getActionsDTO().toArray(new String[0]));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isActive(int tickNumber, double probability) {
        return activation.isActive(tickNumber, probability);
    }

    @Override
    public void invoke(Context context) {
        if (relevantEntities.contains(context.getEntityInstance().getName()) && context.getEntityInstance().isAlive()) {
            actions.forEach(action -> {
                //3)TODO: Check if the action has a secondary entity
                //3)TODO: if it does place relevant secondary entities in a list
                if (!action.isSecondaryEntityExist()) {
                    action.invoke(context);
                } else {
                    List<EntityInstance> filteredSecondaryEntities = action.getSecondEntityFilteredList(context);
                    /*List<EntityInstance> filteredSecondaryEntities = new ArrayList<>();

                    SecondEntity secondEntity = action.getSecondaryEntityForAction();
                    String secondEntityName = secondEntity.getSecondEntity().getName();
                    String secondEntityCount = secondEntity.getInstancesCount();

                    List<EntityInstance> allSecondaryEntities = context.getInstancesWithName(secondEntityName);

                    if (secondEntityCount.equals("ALL")){

                    }
                    allSecondaryEntities.forEach(entityInstance -> {
                        Context secondEntityContext = context.duplicateContextWithEntityInstance(entityInstance);
                        if (secondEntity.evaluateCondition(secondEntityContext)) {
                            filteredSecondaryEntities.add(entityInstance);
                        }
                    });
                    EntityInstance secondEntity = action.getSecondaryEntityForAction().getSecondEntity();*/
                }
            });
        }
    }

    /*<PRD-rule name="r2">
            <PRD-actions>
                <PRD-action type="condition" entity="ent-1">
                    <PRD-secondary-entity entity="ent-2">
                        <PRD-selection count="4">
                            <PRD-condition singularity="single" entity="ent-2" property="p1" operator="bt" value="4"/>
                        </PRD-selection>
                    </PRD-secondary-entity>
                    <PRD-condition singularity="multiple" logical="or">
                        <PRD-condition singularity="single" entity="ent-1" property="ticks(ent-1.p1)" operator="bt" value="4"/>
                        <PRD-condition singularity="single" entity="ent-2" property="p2" operator="lt" value="3"/>
                        <PRD-condition singularity="multiple" logical="and">
                            <PRD-condition singularity="single" entity="ent-1" property="p4" operator="!=" value="nothing"/>
                            <PRD-condition singularity="single" entity="ent-1" property="p3" operator="=" value="environment(e2)"/>
                        </PRD-condition>
                    </PRD-condition>
                    <PRD-then>
                        <PRD-action type="increase" entity="ent-1" property="p1" by="3"/>
                        <PRD-action type="set" entity="ent-1" property="p1" value="random(3)"/>
                    </PRD-then>
                    <PRD-else>
                        <PRD-action type="kill" entity="ent-1"/>
                    </PRD-else>
                </PRD-action>
            </PRD-actions>
        </PRD-rule>*/
    @Override
    public void addAction(Action action) {
        if (action == null) {
            throw new NullPointerException("Action can not be null");
        }

        actions.add(action);
    }

    @Override
    public RuleDTO convertToDTO() {
        return new RuleDTO(
                name,
                activation.getNumOfTicksToActivate(),
                activation.getProbabilityToActivate(),
                actions.stream()
                        .map(Action::convertToDTO)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(name).append(System.lineSeparator());
        str.append(activation).append(System.lineSeparator());
        str.append(actions.size()).append("# actions:").append(System.lineSeparator());
        for (Action action : actions) {
            str.append(action).append(System.lineSeparator());
        }

        return str.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleImpl rule = (RuleImpl) o;
        return Objects.equals(name, rule.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    private void initRelevantEntities(List<String> names) {
        relevantEntities = new TreeSet<>();
        relevantEntities.addAll(names);
    }

    private void initRelevantSecondaryEntities(List<String> names) {
        relevantSecondaryEntities = new TreeSet<>();
        relevantSecondaryEntities.addAll(names);
    }
}