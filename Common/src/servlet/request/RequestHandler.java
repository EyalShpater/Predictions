package servlet.request;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import general.constants.GeneralConstants;
import impl.*;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static general.configuration.Configuration.HTTP_CLIENT;

public class RequestHandler {

    public static List<EntityDefinitionDTO> getEntities(String worldName) throws IOException {
        return getWorld(worldName).getEntities();
    }

    public static List<WorldDTO> getWorlds() throws IOException {
        Gson gson = new Gson();

        Request request = new Request.Builder()
                .url(GeneralConstants.BASE_URL + GeneralConstants.GET_LOADED_WORLDS_RESOURCE)
                .build();

        Call call = HTTP_CLIENT.newCall(request);

        Response response = call.execute();

        JsonArray worldsJson = JsonParser.parseString(response.body().string()).getAsJsonArray();
        List<WorldDTO> worlds = new ArrayList<>();

        worldsJson.forEach(json -> worlds.add(gson.fromJson(json, WorldDTO.class)));

        return worlds;
    }

    public static List<RuleDTO> getRules(String worldName) throws IOException {
        return getWorld(worldName).getRules();
    }

    public static WorldDTO getWorld(String worldName) throws IOException {
        Response response = getResponseUsingWorldName(GeneralConstants.GET_WORLD_RESOURCE, worldName);
        Gson gson = new Gson();

        return (WorldDTO) gson.fromJson(response.body().string(), WorldDTO.class);
    }

    public static List<PropertyDefinitionDTO> getEnvironmentVariablesToSet(String worldName) throws IOException {
        List<PropertyDefinitionDTO> environmentVariables = new ArrayList<>();
        Response response = getResponseUsingWorldName(GeneralConstants.GET_ENVIRONMENT_VARIABLES_TO_SET_RESOURCE, worldName);
        Gson gson = new Gson();

        JsonArray varsJson = JsonParser.parseString(response.body().string()).getAsJsonArray();
        varsJson.forEach(varJson -> environmentVariables.add(gson.fromJson(varJson, PropertyDefinitionDTO.class)));

        return environmentVariables;
    }

    public static SimulationQueueDto getSimulationQueueData() throws IOException {
        Gson gson = new Gson();

        Request request = new Request.Builder()
                .url(GeneralConstants.BASE_URL + GeneralConstants.GET_SIMULATION_QUEUE_DETAILS_RESOURCE)
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        Response response = call.execute();

        return (SimulationQueueDto) gson.fromJson(response.body().string(), SimulationQueueDto.class);
    }

    public static int setThreadPoolSize(int size) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(GeneralConstants.BASE_URL + GeneralConstants.SET_THREAD_POOL_SIZE_RESOURCE).newBuilder();
        urlBuilder.addQueryParameter(GeneralConstants.THREAD_POOL_SIZE_PARAMETER_NAME, String.valueOf(size));
        String finalUrl = urlBuilder.build().toString();

        RequestBody body = RequestBody.create("".getBytes());

        Request request = new Request.Builder()
                .url(finalUrl)
                .put(body)
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        Response response = call.execute();

        return response.code();
    }

    public static List<RequestedSimulationDataDTO> getRequestsByUserName(String userName) throws IOException {
        List<RequestedSimulationDataDTO> requests = new ArrayList<>();
        Gson gson = new Gson();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(GeneralConstants.BASE_URL + GeneralConstants.GET_ALL_USER_REQUESTS_RESOURCE).newBuilder();
        urlBuilder.addQueryParameter(GeneralConstants.USER_NAME_PARAMETER_NAME, userName);
        String finalUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        Response response = call.execute();

        JsonArray requestsJson = JsonParser.parseString(response.body().string()).getAsJsonArray();
        requestsJson.forEach(requestJson -> requests.add(gson.fromJson(requestJson, RequestedSimulationDataDTO.class)));

        return requests;
    }

    public static void addNewAllocationRequest(RunRequestDTO simulationRequest) {
        try {
            Gson gson = new Gson();

            RequestBody body = RequestBody.create(gson.toJson(simulationRequest).getBytes());
            System.out.println("body check");
            System.out.println(gson.toJson(simulationRequest));
            Request request = new Request.Builder()
                    .url(GeneralConstants.BASE_URL + GeneralConstants.ALLOCATION_REQUEST_RESOURCE)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = HTTP_CLIENT.newCall(request).execute();
        } catch (Exception ignored) {
        }
    }

    public static void changeRequestStatus(int requestSerialNumber, boolean isAccept, String userName) throws IOException {
        String body = GeneralConstants.REQUEST_ID_PARAMETER_NAME + "=" + requestSerialNumber + System.lineSeparator()
                + GeneralConstants.NEW_STATUS_PARAMETER_NAME + "=" + isAccept + System.lineSeparator()
                + GeneralConstants.USER_NAME_PARAMETER_NAME + "=" + userName;

        Request request = new Request.Builder()
                .url(GeneralConstants.BASE_URL + GeneralConstants.CHANGE_REQUEST_STATUS_RESOURCE)
                .put(RequestBody.create(body.getBytes()))
                .build();

        HTTP_CLIENT.newCall(request).execute();
    }

    public static int runSimulation(SimulationInitDataFromUserDTO simulationInitDataFromUserDTO) throws IOException {
        Integer simulationSerialNumber = -1;
        Gson gson = new Gson();
        String reqJsonData = gson.toJson(simulationInitDataFromUserDTO);

        HttpUrl.Builder urlBuilder = HttpUrl.parse(GeneralConstants.BASE_URL + GeneralConstants.RUN_SIMULATION_RESOURCE).newBuilder();
        String finalURL = urlBuilder.build().toString();

        RequestBody body = RequestBody.create(gson.toJson(simulationInitDataFromUserDTO).getBytes());

        Request request = new Request.Builder()
                .url(finalURL)
                .put(body)
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        Response response = call.execute();

        String responseBody = response.body().string().trim();

        try {
            simulationSerialNumber = Integer.valueOf(responseBody);
        } catch (Exception ignored) {
        }
        return simulationSerialNumber;
    }

    public static SimulationDataDTO getSimulationData(int simulationId, String entityName, String propertyName) throws IOException {
        Gson gson = new Gson();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(GeneralConstants.BASE_URL + GeneralConstants.GET_ALL_USER_REQUESTS_RESOURCE).newBuilder();
        urlBuilder.addQueryParameter(GeneralConstants.SIMULATION_SERIAL_NUMBER_PARAMETER_NAME, String.valueOf(simulationId));
        urlBuilder.addQueryParameter(GeneralConstants.ENTITY_NAME_PARAMETER_NAME, entityName);
        urlBuilder.addQueryParameter(GeneralConstants.PROPERTY_NAME_PARAMETER_NAME, propertyName);
        String finalUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        Response response = call.execute();

        return (SimulationDataDTO) gson.fromJson(response.body().string(), SimulationDataDTO.class);
    }

    public static Map<String, Double> getConsistencyByEntityName(int serialNumber, String entityName) throws IOException {
        //todo: debug
        Gson gson = new Gson();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(GeneralConstants.BASE_URL + GeneralConstants.GET_CONSISTENCY_BT_ENTITY_RESOURCE).newBuilder();
        urlBuilder.addQueryParameter(GeneralConstants.SIMULATION_SERIAL_NUMBER_PARAMETER_NAME, String.valueOf(serialNumber));
        urlBuilder.addQueryParameter(GeneralConstants.ENTITY_NAME_PARAMETER_NAME, entityName);
        String finalUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        Response response = call.execute();

        Type mapType = new TypeToken<Map<String, Double>>() {
        }.getType();

        return gson.fromJson(response.body().string(), mapType);
    }

    public static List<SimulationDTO> getSimulationsDTOByUserName(String userName) throws IOException {
        List<SimulationDTO> simulations = new ArrayList<>();
        Gson gson = new Gson();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(GeneralConstants.BASE_URL + GeneralConstants.GET_SIMULATION_DTO_RESOURCE).newBuilder();
        urlBuilder.addQueryParameter(GeneralConstants.USER_NAME_PARAMETER_NAME, userName);
        String finalUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        Response response = call.execute();

        String resp = response.body().string(); //todo: Debug

        JsonArray simulationsJson = JsonParser.parseString(resp).getAsJsonArray();
        simulationsJson.forEach(simulationJson -> simulations.add(gson.fromJson(simulationJson, SimulationDTO.class)));

        return simulations;
    }

    public static Map<String, Map<Integer, Long>> getPopulationCountSortedByName(int serialNumber) throws IOException {
        //todo: debug
        Gson gson = new Gson();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(GeneralConstants.BASE_URL + GeneralConstants.GET_POPULATION_COUNT_SORTED_BY_NAME_RESOURCE).newBuilder();
        urlBuilder.addQueryParameter(GeneralConstants.SIMULATION_SERIAL_NUMBER_PARAMETER_NAME, String.valueOf(serialNumber));
        String finalUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        Response response = call.execute();

        Type mapType = new TypeToken<Map<String, Map<Integer, Long>>>() {
        }.getType();

        return gson.fromJson(response.body().string(), mapType);
    }

    public static double getFinalNumericPropertyAvg(int simulationId, String entityName, String propertyName) throws IOException {
        Gson gson = new Gson();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(GeneralConstants.BASE_URL + GeneralConstants.GET_FINAL_PROPERTY_AVERAGE_RESOURCE).newBuilder();
        urlBuilder.addQueryParameter(GeneralConstants.SIMULATION_SERIAL_NUMBER_PARAMETER_NAME, String.valueOf(simulationId));
        urlBuilder.addQueryParameter(GeneralConstants.ENTITY_NAME_PARAMETER_NAME, entityName);
        urlBuilder.addQueryParameter(GeneralConstants.PROPERTY_NAME_PARAMETER_NAME, propertyName);
        String finalUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        Response response = call.execute();

        return gson.fromJson(response.body().string(), Double.class);
    }

    public static boolean isEnded(int serialNumber) throws IOException {
        return checkSimulationRunningStatus(serialNumber, GeneralConstants.GET_IF_SIMULATION_ENDED_RESOURCE);
    }

    public static boolean hasStarted(int serialNumber) throws IOException {
        return checkSimulationRunningStatus(serialNumber, GeneralConstants.GET_IF_SIMULATION_STARTED_RESOURCE);
    }

    public static void resumeSimulationBySerialNumber(int serialNumber) throws IOException {
        changeSimulationRunningStatus(serialNumber, GeneralConstants.RESUME_SIMULATION_RESOURCE);
    }

    public static void pauseSimulationBySerialNumber(int serialNumber) throws IOException {
        changeSimulationRunningStatus(serialNumber, GeneralConstants.PAUSE_SIMULATION_RESOURCE);
    }

    public static boolean isStop(int serialNumber) throws IOException {
        return checkSimulationRunningStatus(serialNumber, GeneralConstants.STOP_SIMULATION_RESOURCE);
    }

    public static boolean isPause(int serialNumber) throws IOException {
        return checkSimulationRunningStatus(serialNumber, GeneralConstants.PAUSE_SIMULATION_RESOURCE);
    }


    public static void stopSimulationBySerialNumber(int serialNumber) throws IOException {
        changeSimulationRunningStatus(serialNumber, GeneralConstants.STOP_SIMULATION_RESOURCE);
    }

    public static EntitiesAmountDTO getSimulationEntitiesAmountMap(int serialNumber) throws IOException {
        Gson gson = new Gson();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(GeneralConstants.BASE_URL + GeneralConstants.GET_SIMULATION_ENTITIES_AMOUNT_RESOURCE).newBuilder();
        urlBuilder.addQueryParameter(GeneralConstants.SIMULATION_SERIAL_NUMBER_PARAMETER_NAME, String.valueOf(serialNumber));
        String finalUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        Response response = call.execute();

        return gson.fromJson(response.body().string(), EntitiesAmountDTO.class);
    }

    public static SimulationRunDetailsDTO getSimulationRunDetail(int serialNumber) throws IOException {
        Gson gson = new Gson();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(GeneralConstants.BASE_URL + GeneralConstants.GET_SIMULATION_RUN_DETAILS_RESOURCE).newBuilder();
        urlBuilder.addQueryParameter(GeneralConstants.SIMULATION_SERIAL_NUMBER_PARAMETER_NAME, String.valueOf(serialNumber));
        String finalUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        Response response = call.execute();

        return gson.fromJson(response.body().string(), SimulationRunDetailsDTO.class);
    }

    private static void changeSimulationRunningStatus(int serialNumber, String resource) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(GeneralConstants.BASE_URL + resource).newBuilder();
        urlBuilder.addQueryParameter(GeneralConstants.SIMULATION_SERIAL_NUMBER_PARAMETER_NAME, String.valueOf(serialNumber));
        String finalURL = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(finalURL)
                .put(RequestBody.create("".getBytes()))
                .build();

        Call call = HTTP_CLIENT.newCall(request);

        call.execute();
    }

    private static boolean checkSimulationRunningStatus(int serialNumber, String resource) throws IOException {
        Gson gson = new Gson();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(GeneralConstants.BASE_URL + resource).newBuilder();
        urlBuilder.addQueryParameter(GeneralConstants.SIMULATION_SERIAL_NUMBER_PARAMETER_NAME, String.valueOf(serialNumber));
        String finalUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        Response response = call.execute();

        return gson.fromJson(response.body().string(), Boolean.class);
    }

    private static Response getResponseUsingWorldName(String resource, String worldName) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(GeneralConstants.BASE_URL + resource).newBuilder();
        urlBuilder.addQueryParameter(GeneralConstants.WORLD_NAME_PARAMETER_NAME, worldName);
        String finalUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        Response response = call.execute();

        return response;
    }
}
