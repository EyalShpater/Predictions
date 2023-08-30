package javafx.tab.details.rules;

import com.sun.javafx.binding.StringFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class RulesController {

    @FXML
    private TitledPane titledPane;

    @FXML
    private Label nameLabel;

    @FXML
    private Label numOfTicksLabel;

    @FXML
    private Label probabilityLabel;

    @FXML
    private TreeView<String> actionsTreeView;

    @FXML
    private void initialize() {
        titledPane.setText("Rule 1");
        nameLabel.setText("Rule 1");
        numOfTicksLabel.setText("10");
        probabilityLabel.setText("0.35");

        TreeItem<String> root = new TreeItem<>("Actions");
        TreeItem<String> increase = new TreeItem<>("Increase");
        TreeItem<String> decrease = new TreeItem<>("Decrease");

        TreeItem<String> condition = new TreeItem<>("Condition");

        TreeItem<String> then = new TreeItem<>("Then");
        TreeItem<String> thenInc = new TreeItem<>("Increase");
        TreeItem<String> thenMulty = new TreeItem<>("Multiple");

        TreeItem<String> elseCond = new TreeItem<>("Else");
        TreeItem<String> kill = new TreeItem<>("Kill");

        then.getChildren().addAll(thenInc, thenMulty);
        elseCond.getChildren().addAll(kill);
        condition.getChildren().addAll(then, elseCond);

        root.getChildren().addAll(increase, decrease, condition);

        actionsTreeView.setRoot(root);
        actionsTreeView.setShowRoot(false);
    }
}
