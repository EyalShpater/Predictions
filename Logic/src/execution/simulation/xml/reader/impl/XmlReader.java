package execution.simulation.xml.reader.impl;

import definition.world.api.World;
import resources.xml.ex3.generated.PRDWorld;

public class XmlReader {
    private PRDWorld prdWorld;

    public XmlReader(PRDWorld world) {
        this.prdWorld = world;
    }


    public void readXml(World world) {

        world.setName(prdWorld.getName());

        Integer sleep = prdWorld.getSleep();
        if (sleep != null) {
            world.setSleepTime(sleep);
        }

        //Object that reads into env vars
        EnvReader envReader = new EnvReader();
        envReader.read(this.prdWorld, world);

        //Object that read into entity
        EntityReader entityReader = new EntityReader();
        entityReader.read(this.prdWorld, world);

        //Object that read into rule
        RuleReader ruleReader = new RuleReader();
        ruleReader.read(this.prdWorld, world);

        world.setGridCols(prdWorld.getPRDGrid().getColumns());
        world.setGridRows(prdWorld.getPRDGrid().getRows());
    }

}
