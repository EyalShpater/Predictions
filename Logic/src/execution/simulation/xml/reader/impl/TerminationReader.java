package execution.simulation.xml.reader.impl;

import definition.world.api.World;
import execution.simulation.termination.api.TerminateCondition;
import execution.simulation.termination.impl.TerminationImpl;
import resources.generated.*;

import java.io.Serializable;
import java.util.List;

public class TerminationReader  {
    public void read (PRDWorld prdWorld , World world){
        List<Object> terminationList = prdWorld.getPRDTermination().getPRDByTicksOrPRDBySecond();
        int len = terminationList.size();

        if (len == 1){
            if (terminationList.get(0) instanceof PRDByTicks) {
                PRDByTicks byTicks = ( PRDByTicks ) terminationList.get(0);
                world.setTermination(new TerminationImpl(byTicks.getCount(), TerminateCondition.BY_TICKS));
            } else if (terminationList.get(0) instanceof PRDBySecond) {
                PRDBySecond bySecond = ( PRDBySecond ) terminationList.get(0);
                world.setTermination(new TerminationImpl(bySecond.getCount(), TerminateCondition.BY_SECONDS));
            }
        } else if ( len == 2 ) {
            PRDByTicks byTicks = (PRDByTicks) terminationList.get(0);
            PRDBySecond bySecond = (PRDBySecond) terminationList.get(1);
            world.setTermination(new TerminationImpl(byTicks.getCount() , bySecond.getCount()));
        }
    }
}