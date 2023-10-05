package tab.requests.component.submit;

import general.constants.GeneralConstants;
import impl.RequestedSimulationDataDTO;
import impl.RunRequestDTO;
import impl.TerminationDTO;
import impl.WorldDTO;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import servlet.request.RequestHandler;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SubmitComponentController {

    @FXML
    private ComboBox<String> worldComboBox;

    @FXML
    private Spinner<Integer> runsAmountSpinner;

    @FXML
    private Spinner<Integer> secondsSpinner;

    @FXML
    private Spinner<Integer> ticksSpinner;

    @FXML
    private RadioButton terminateBySecondsRadioButton;

    @FXML
    private RadioButton terminateByTicksRadioButton;

    @FXML
    private Button submitButton;

    private String userName;
    private StringProperty selectedWorld;
    private IntegerProperty runsAmount;
    private IntegerProperty seconds;
    private IntegerProperty ticks;
    private BooleanProperty isTerminateByTicks;
    private BooleanProperty isTerminateBySeconds;
    private ObservableList<String> worlds;

    public SubmitComponentController() {
        this.selectedWorld = new SimpleStringProperty();
        this.runsAmount = new SimpleIntegerProperty();
        this.seconds = new SimpleIntegerProperty();
        this.ticks = new SimpleIntegerProperty();
        this.isTerminateByTicks = new SimpleBooleanProperty();
        this.isTerminateBySeconds = new SimpleBooleanProperty();
        this.worlds = FXCollections.observableArrayList();
    }

    @FXML
    private void initialize() {
        bindProperties();
        setSpinners();
        setWorldComboBox();
    }

    @FXML
    void onSubmitButtonClicked(ActionEvent event) {
        RunRequestDTO request = new RunRequestDTO(
                userName,
                selectedWorld.get(),
                runsAmount.get(),
                new TerminationDTO(
                        ticks.get(),
                        seconds.get(),
                        isTerminateBySeconds.get(),
                        isTerminateByTicks.get()
                ));

        RequestHandler.addNewAllocationRequest(request);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private void bindProperties() {
        selectedWorld.bind(worldComboBox.valueProperty());
        runsAmount.bind(runsAmountSpinner.valueProperty());
        seconds.bind(secondsSpinner.valueProperty());
        ticks.bind(ticksSpinner.valueProperty());
        isTerminateByTicks.bind(terminateByTicksRadioButton.selectedProperty().not());
        isTerminateBySeconds.bind(terminateBySecondsRadioButton.selectedProperty().not());

        secondsSpinner.disableProperty().bind(isTerminateBySeconds);
        ticksSpinner.disableProperty().bind(isTerminateByTicks);
    }

    private void setSpinners() {
        setSpinner(runsAmountSpinner);
        setSpinner(secondsSpinner);
        setSpinner(ticksSpinner);
    }

    private void setSpinner(Spinner<Integer> spinner) {
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE));
        spinner.getValueFactory().setWrapAround(false);

        spinner.getEditor().setOnAction(event -> spinner.increment(0));
        spinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                spinner.increment(0);
            }
        });

        spinner.getEditor().setOnKeyTyped(keyEvent -> {
            String input = keyEvent.getCharacter();
            if (!input.matches("[0-9]") && !(input.equals(".") && !spinner.getEditor().getText().contains("."))) {
                keyEvent.consume();
            }
        });
    }

    private void setWorldComboBox() {
        worldComboBox.setPromptText("Simulation Name");
        worldComboBox.setItems(worlds);

        TimerTask refreshRequestsTable = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> refreshUploadedWorlds());
            }
        };
        Timer timer = new Timer();
        timer.schedule(refreshRequestsTable, 1000, 2000);
    }

    private void refreshUploadedWorlds() {
        List<WorldDTO> updatedWorlds = null;

        try {
            updatedWorlds = RequestHandler.getWorlds();
        } catch (Exception e) {
            return;
        }

        updatedWorlds.forEach(updatedWorld -> {
            if (!worlds.contains(updatedWorld.getName())) {
                worlds.add(updatedWorld.getName());
            }
        });
    }
}
