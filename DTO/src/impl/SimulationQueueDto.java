package impl;

public class SimulationQueueDto {
    int total;
    int waiting;
    int running;

    public SimulationQueueDto(int total, int waiting, int running) {
        this.total = total;
        this.waiting = waiting;
        this.running = running;
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
}
