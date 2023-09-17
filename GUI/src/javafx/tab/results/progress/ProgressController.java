package javafx.tab.results.progress;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

public class ProgressController {

    @FXML
    private Button pauseButton;

    @FXML
    private ImageView pauseButtonIcon;

    @FXML
    private Button stopButton;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label secondsLabel;

    @FXML
    private Label ticksLabel;

    @FXML
    void initialize() {
    }
}
