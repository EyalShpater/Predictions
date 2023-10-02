package tab.details.details;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import general.constants.GeneralConstants;
import impl.EntityDefinitionDTO;
import impl.PropertyDefinitionDTO;
import impl.RuleDTO;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.tab.details.rules.RulesController;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import tab.details.components.ComponentsController;
import tab.details.entities.EntitiesController;
import tab.details.environment.variables.EnvironmentVariablesController;
import tab.details.general.GeneralController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static general.configuration.Configuration.HTTP_CLIENT;

public class DetailsComponentController {

    @FXML
    private BorderPane borderPane;
    @FXML
    private StackPane sceneSwitcher;
    @FXML
    private ScrollPane buttonsComponent;
    @FXML
    private ComponentsController buttonsComponentController;

    private String worldName;

    @FXML
    void initialize() {
        buttonsComponentController.setMainController(this);
    }

    public void entitiesOnAction(ActionEvent event) {
        try {
            Accordion newScene = new Accordion();

            getEntities().forEach(entity -> setNewEntity(entity, newScene));

            sceneSwitcher.getChildren().clear();
            sceneSwitcher.getChildren().add(newScene);
        } catch (Exception ignored) {
        }
    }

    private List<EntityDefinitionDTO> getEntities() throws IOException {
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

    public void environmentVarOnAction(ActionEvent event) {
        FlowPane fp = new FlowPane();
        fp.setHgap(5);
        fp.setVgap(5);
        fp.setPadding(new Insets(5));
//
//        engine.getEnvironmentVariablesToSet()
//                .forEach(env -> setNewEnvironmentVariableTile(env, fp));

        ScrollPane sp = new ScrollPane(fp);
        sp.setFitToWidth(true);
        setScene(sp);
    }

    public void rulesOnAction(ActionEvent event) {
        try {
            Accordion newScene = new Accordion();

//            engine
//                    .getLoadedSimulationDetails()
//                    .getRules()
//                    .forEach(rule -> setNewRule(rule, newScene));

            sceneSwitcher.getChildren().clear();
            sceneSwitcher.getChildren().add(newScene);
        } catch (Exception ignored) {
        }
    }

    public void generalOnAction(ActionEvent event) {
        try {
            URL resource = getClass().getResource("/tab/details/general/General.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            StackPane general = loader.load();
            GeneralController generalController = loader.getController();

//            generalController.setEngine(engine);
            generalController.setPropertiesFromEngine();
            setScene(general);

        } catch (Exception ignored) {
        }

    }

    private void setScene(Node newScene) {
        sceneSwitcher.getChildren().clear();
        sceneSwitcher.getChildren().add(newScene);
    }

    private void setNewEntity(EntityDefinitionDTO entity, Accordion accordion) {
        try {
            URL resource = getClass().getResource("/tab/details/entities/Entities.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            TitledPane titledPane = loader.load();
            EntitiesController entitiesController = loader.getController();

            entitiesController.setDataFromDTO(entity);
            accordion.getPanes().add(titledPane);
        } catch (IOException ignored) {
        }
    }

    private void setNewEnvironmentVariableTile(PropertyDefinitionDTO env, FlowPane flowPane) {
        try {
            URL resource = getClass().getResource("/tab/details/environment/variables/EnvironmentVariables.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            StackPane stackPane = loader.load();
            EnvironmentVariablesController envVarController = loader.getController();

            envVarController.setDataFromDTO(env);
            flowPane.getChildren().add(stackPane);
        } catch (IOException ignored) {
        }
    }

    private void setNewRule(RuleDTO rule, Accordion accordion) {
        try {
            URL resource = getClass().getResource("/tab/details/rules/Rules.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            TitledPane rulePane = loader.load();
            RulesController rulesController = loader.getController();

            rulesController.setDataFromDTO(rule);
            accordion.getPanes().add(rulePane);
        } catch (IOException ignored) {

        }
    }

    public BooleanProperty getTabDisableProperty() {
        return buttonsComponent.disableProperty();
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }
}
