package execution.simulation.xml.reader.impl;

import definition.world.api.World;
import resources.xml.ex3.generated.PRDWorld;
//import resources.xml.ex2.generated.PRDWorld;
//import resources.xml.ex1.generated.PRDWorld;

public class XmlReader {
    private PRDWorld prdWorld;

    public XmlReader(PRDWorld world) {
        this.prdWorld = world;
    }


    public void readXml(World world) {

        world.setName(prdWorld.getName());

        //Object that reads into env vars
        EnvReader envReader = new EnvReader();
        envReader.read(this.prdWorld, world);

        //Object that read into entity
        EntityReader entityReader = new EntityReader();
        entityReader.read(this.prdWorld, world);

        //Object that read into rule
        RuleReader ruleReader = new RuleReader();
        ruleReader.read(this.prdWorld, world);

        //TODO: delete
        /*TerminationReader terminationReader = new TerminationReader();
        terminationReader.read(this.prdWorld, world);*/

        // set thread pool size

        //world.setThreadPoolSize(this.prdWorld.getPRDThreadCount());

        world.setGridCols(prdWorld.getPRDGrid().getColumns());
        world.setGridRows(prdWorld.getPRDGrid().getRows());
    }

}
