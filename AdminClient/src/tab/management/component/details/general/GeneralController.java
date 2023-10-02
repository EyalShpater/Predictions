package tab.management.component.details.general;

import com.google.gson.Gson;
import general.constants.GeneralConstants;
import impl.GridDTO;
import impl.WorldDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import servlet.request.RequestHandler;

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
        WorldDTO world = RequestHandler.getWorld(selectedWorld);

        numOfCols.set(String.valueOf(world.getGridNumOfCols()));
        numOfRows.set(String.valueOf(world.getGridNumOfRows()));
    }

    public void setSelectedWorld(String worldName) {
        this.selectedWorld = worldName;
    }

}
