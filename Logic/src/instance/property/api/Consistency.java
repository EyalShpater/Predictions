package instance.property.api;

public class Consistency {
    private int lastUpdatedTick;
    private int numOfChanges;
    private double average;

    public static final int NOT_DEAD = -1;

    public void update(int currentTick) {
        if (currentTick != lastUpdatedTick) {
            numOfChanges++;
            average += currentTick - lastUpdatedTick - 1;
            lastUpdatedTick = currentTick;
        }
    }

    public double calculateAverage(int endTick, int deathTick) {
        int finalNotUpdatedTicks = deathTick == NOT_DEAD ?
                endTick - lastUpdatedTick :
                deathTick - lastUpdatedTick;

        average += finalNotUpdatedTicks;
        numOfChanges++;

        return numOfChanges != 0 ?
                average / numOfChanges :
                endTick;
    }
}
