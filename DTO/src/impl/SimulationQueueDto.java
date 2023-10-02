package impl;

public class SimulationQueueDto {
    private final int total;
    private final int waiting;
    private final int running;
    private final int numOfThreads;

    public SimulationQueueDto(int total, int waiting, int running, int numOfThreads) {
        this.total = total;
        this.waiting = waiting;
        this.running = running;
        this.numOfThreads = numOfThreads;
    }

    public int getTotal() {
        return total;
    }

    public int getWaiting() {
        return waiting;
    }

    public int getRunning() {
        return running;
    }

    public int getNumOfThreadsInThreadPool() {
        return numOfThreads;
    }
}
