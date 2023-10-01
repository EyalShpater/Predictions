package tab.details.general;

import com.google.gson.Gson;
import general.constants.GeneralConstants;
import impl.GridDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static general.configuration.Configuration.HTTP_CLIENT;

public class GeneralController {

    @FXML
    private Label ticksNumberLabel;
    @FXML
    private Label secondsNumberLabel;
    @FXML
    private Label gridColumnNumberLabel;
    @FXML
    private Label gridRowsNumberLabel;
    @FXML
    private Label terminateByUserLabel;

    private String selectedWorld;
    private StringProperty numOfTicks = new SimpleStringProperty();
    private StringProperty numOfSeconds = new SimpleStringProperty();
    private StringProperty numOfCols = new SimpleStringProperty();
    private StringProperty numOfRows = new SimpleStringProperty();

    @FXML
    private void initialize() {
        ticksNumberLabel.textProperty().bind(numOfTicks);
        secondsNumberLabel.textProperty().bind(numOfSeconds);
        gridColumnNumberLabel.textProperty().bind(numOfCols);
        gridRowsNumberLabel.textProperty().bind(numOfRows);
        terminateByUserLabel.visibleProperty().bind(numOfTicks.isEmpty().and(numOfSeconds.isEmpty()));
    }

    public void setPropertiesFromEngine() throws IOException {
//        boolean isTerminateByTicks = engine
//                .getLoadedSimulationDetails()
//                .getTermination()
//                .isTerminateByTicks();
//        boolean isTerminateBySeconds = engine
//                .getLoadedSimulationDetails()
//                .getTermination()
//                .isTerminateBySeconds();
//
//        numOfTicks.set(
//                isTerminateByTicks ?
//                        String.valueOf(engine
//                                .getLoadedSimulationDetails()
//                                .getTermination()
//                                .getTicksToTerminate()) :
//                        "");
//
//        numOfSeconds.set(
//                isTerminateBySeconds ?
//                        String.valueOf(engine
//                                .getLoadedSimulationDetails()
//                                .getTermination()
//                                .getSecondsToTerminate()) :
//                        "");
        Gson gson = new Gson();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(GeneralConstants.BASE_URL + GeneralConstants.GET_WORLD_GENERAL_INFORMATION_RESOURCE).newBuilder();
        urlBuilder.addQueryParameter(GeneralConstants.WORLD_NAME_PARAMETER_NAME, selectedWorld);
        String finalUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        Call call = HTTP_CLIENT.newCall(request);

        Response response = call.execute();

        GridDTO gridInformation = gson.fromJson(response.body().string(), GridDTO.class);

        numOfCols.set(String.valueOf(gridInformation.getCol()));
        numOfRows.set(String.valueOf(gridInformation.getRow()));
    }

    public void setSelectedWorld(String worldName) {
        this.selectedWorld = worldName;
    }

}
