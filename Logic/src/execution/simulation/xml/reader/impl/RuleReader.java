package execution.simulation.xml.reader.impl;

import definition.world.api.World;
import resources.xml.ex3.generated.PRDAction;
import resources.xml.ex3.generated.PRDActivation;
import resources.xml.ex3.generated.PRDRule;
import resources.xml.ex3.generated.PRDWorld;

import rule.api.Activation;
import rule.api.Rule;
import rule.impl.ActivationImpl;
import rule.impl.RuleImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*import resources.xml.ex1.generated.PRDAction;
import resources.xml.ex1.generated.PRDActivation;
import resources.xml.ex1.generated.PRDRule;
import resources.xml.ex1.generated.PRDWorld;*/

public class RuleReader {

    public void read(PRDWorld prdWorld, World world) {
        List<PRDRule> prdRuleList = prdWorld.getPRDRules().getPRDRule();
        prdRuleList.forEach(prdRule -> readDataFromPRDRuleToRule(prdRule, world));
    }

    private void readDataFromPRDRuleToRule(PRDRule prdRule, World world) {

        Activation activation = createActivation(prdRule.getPRDActivation());
        Rule newRule = new RuleImpl(prdRule.getName(), activation);
        List<PRDAction> prdActionsList = prdRule.getPRDActions().getPRDAction();
        for( PRDAction prdAction : prdActionsList ){
            ActionReader actionReader = new ActionReader();
            newRule.addAction(actionReader.read(prdAction  , world.getEntityByName(prdAction.getEntity()) , world));
        }

        world.addRule(newRule);
    }

    private Activation createActivation(PRDActivation prdActivation) {
        if ( prdActivation != null ){
            if (prdActivation.getProbability() == null) {
                return new ActivationImpl(prdActivation.getTicks());
            } else if (prdActivation.getTicks() == null ) {
                return new ActivationImpl(prdActivation.getProbability());
            }else {
                return new ActivationImpl( prdActivation.getTicks() , prdActivation.getProbability() );
            }
        }
        return new ActivationImpl();
    }

    private List<String> createRelevantEntityList(List<PRDAction> prdActionList) {
        Set<String> relevantEntitySet = new HashSet<>();

        prdActionList.forEach(prdAction -> relevantEntitySet.add(prdAction.getEntity()));

        return new ArrayList<>(relevantEntitySet);
    }


}
