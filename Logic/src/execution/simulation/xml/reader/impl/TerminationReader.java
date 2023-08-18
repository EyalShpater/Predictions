package execution.simulation.xml.reader.impl;

import definition.world.api.World;
import execution.simulation.termination.impl.TerminationImpl;
import resources.generated.*;

import java.util.List;

public class TerminationReader {


    public void read (PRDWorld prdWorld , World world){
        List<Object> terminationList = prdWorld.getPRDTermination().getPRDByTicksOrPRDBySecond();
        int len = terminationList.size();
    //todo      
        if (len == 1){
            if ( terminationList.get(0) instanceof PRDByTicks ){
                PRDByTicks byTicks = ( PRDByTicks ) terminationList.get(0);
                world.setTermination(new TerminationImpl(byTicks.getCount() , -1));
            }else if (terminationList.get(0) instanceof PRDBySecond){
                PRDBySecond bySecond = ( PRDBySecond ) terminationList.get(0);
                world.setTermination(new TerminationImpl(-1 , bySecond.getCount()));
            }
        }else if( len == 2 ){
            PRDByTicks byTicks = ( PRDByTicks ) terminationList.get(0);
            PRDBySecond bySecond = ( PRDBySecond ) terminationList.get(1);
            world.setTermination(new TerminationImpl(byTicks.getCount() , bySecond.getCount()));
        }
    }
}
