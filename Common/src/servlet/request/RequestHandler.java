package servlet.request;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.sun.org.apache.bcel.internal.generic.RET;
import general.constants.GeneralConstants;
import impl.*;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public static int runSimulation(SimulationInitDataFromUserDTO simulationInitDataFromUserDTO) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(GeneralConstants.BASE_URL + GeneralConstants.RUN_SIMULATION_RESOURCE).newBuilder();
        String finalURL = urlBuilder.build().toString();

        RequestBody body = RequestBody.create("".getBytes());

        Request request = new Request.Builder()
                .url(finalURL)
                .put(body)
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        Response response = call.execute();

        Integer simulationSerialNumber = Integer.valueOf(response.body().string());
        return simulationSerialNumber;
    }
}
