package instance.property.api;

public class Consistency {
    int lastUpdatedTick;
    int numOfChanges;
    double average;

    public void update(int currentTick) {
        if (currentTick != lastUpdatedTick) {
            numOfChanges++;
            average += currentTick - lastUpdatedTick - 1;
            lastUpdatedTick = currentTick;
        }
    }

    public double calculateAverage(int endTick) {
        int finalNotUpdatedTicks = endTick - lastUpdatedTick;

        average += finalNotUpdatedTicks;
        numOfChanges++;

        return numOfChanges != 0 ?
                average / numOfChanges :
                endTick;
    }
}
