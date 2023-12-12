package component.details.general;

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
    private Label gridColumnNumberLabel;
    @FXML
    private Label gridRowsNumberLabel;

    private String selectedWorld;
    private StringProperty numOfCols = new SimpleStringProperty();
    private StringProperty numOfRows = new SimpleStringProperty();

    @FXML
    private void initialize() {
        gridColumnNumberLabel.textProperty().bind(numOfCols);
        gridRowsNumberLabel.textProperty().bind(numOfRows);
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
