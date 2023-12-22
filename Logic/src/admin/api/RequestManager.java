package admin.api;

import execution.simulation.termination.impl.TerminationImpl;
import impl.RunRequestDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestManager {
    private Map<Integer, UserRequest> requests;
    private AtomicInteger serialNumber;

    public RequestManager() {
        serialNumber = new AtomicInteger(1);
        requests = new HashMap<>();
    }

    public int addNewRequest(RunRequestDTO request) {
        int id = serialNumber.getAndIncrement();

        UserRequest newRequest = new UserRequest(
                id,
                request.getWorldName(),
                request.getUserName(),
                request.getNumOfRequestedRuns(),
                new TerminationImpl(request.getTermination()));

        synchronized (this) {
            requests.put(newRequest.getSerialNumber(), newRequest);
        }

        return id;
    }

    public UserRequest getRequest(int serialNUmber) {
        return requests.get(serialNUmber);
    }

    public List<UserRequest> getAllRequests() {
        return new ArrayList<>(requests.values());
    }
}
