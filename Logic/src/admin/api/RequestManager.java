package admin.api;

import execution.simulation.termination.impl.TerminationImpl;
import impl.RunRequestDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestManager {
    private Map<Integer, UserRequest> requests;
    private AtomicInteger serialNumber;

    public RequestManager() {
        serialNumber = new AtomicInteger(1);
        requests = new HashMap<>();
    }

    public void addNewRequest(RunRequestDTO request) {
        int id = serialNumber.getAndIncrement();

        UserRequest newRequest = new UserRequest(
                id,
                request.getWorldName(),
                request.getUserName(),
                request.getNumOfRequestedRuns(),
                new TerminationImpl(request.getTermination()));

        synchronized (this) {
            requests.put(id, newRequest);
        }
    }

    public UserRequest getRequest(int serialNUmber) {
        return requests.get(serialNUmber);
    }
}
