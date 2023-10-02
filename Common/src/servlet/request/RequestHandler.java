package servlet.request;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import general.constants.GeneralConstants;
import impl.EntityDefinitionDTO;
import impl.RuleDTO;
import impl.WorldDTO;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static general.configuration.Configuration.HTTP_CLIENT;

public class RequestHandler {

    public static List<EntityDefinitionDTO> getEntities(String worldName) throws IOException {
        List<EntityDefinitionDTO> entities = new ArrayList<>();
        Gson gson = new Gson();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(GeneralConstants.BASE_URL + GeneralConstants.GET_ENTITIES_INFO_RESOURCE).newBuilder();
        urlBuilder.addQueryParameter(GeneralConstants.WORLD_NAME_PARAMETER_NAME, worldName);
        String finalUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        Response response = call.execute();

        JsonArray entitiesJson = JsonParser.parseString(response.body().string()).getAsJsonArray();
        entitiesJson.forEach(entity -> entities.add(gson.fromJson(entity, EntityDefinitionDTO.class)));

        return entities;
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
        List<RuleDTO> rules = new ArrayList<>();
        Gson gson = new Gson();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(GeneralConstants.BASE_URL + GeneralConstants.GET_RULES_INFO_RESOURCE).newBuilder();
        urlBuilder.addQueryParameter(GeneralConstants.WORLD_NAME_PARAMETER_NAME, worldName);
        String finalUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        Response response = call.execute();

        JsonArray rulesJson = JsonParser.parseString(response.body().string()).getAsJsonArray();
        rulesJson.forEach(ruleJson -> rules.add(gson.fromJson(ruleJson, RuleDTO.class)));

        return rules;
    }
}
