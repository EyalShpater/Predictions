package javafx.tab.details.rules;

import impl.ActionDTO;
import impl.RuleDTO;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Map;
import java.util.function.BiConsumer;

public class RulesController {

    @FXML
    private TitledPane mainScreen;
    @FXML
    private Label nameLabel;
    @FXML
    private Label numOfTicksLabel;
    @FXML
    private Label probabilityLabel;
    @FXML
    private TreeView<String> actionsTreeView;

    private StringProperty ruleName = new SimpleStringProperty();
    private IntegerProperty numOfTicks = new SimpleIntegerProperty();
    private DoubleProperty probability = new SimpleDoubleProperty();


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

    private void addActionToTreeView(ActionDTO action) {
        TreeItem<String> typeRoot = new TreeItem<>(action.getType().toLowerCase());

        typeRoot.getChildren().add(new TreeItem<>("Main Entity: " + action.getMainEntity().getName()));
        if (action.getSecondaryEntity() != null) {
            typeRoot.getChildren().add(new TreeItem<>("Secondary Entity: " + action.getSecondaryEntity().getName()));
        }

        TreeItem<String> arguments = new TreeItem<>("Arguments");
        action.getArguments().forEach((key, value) -> {
            arguments.getChildren().add(new TreeItem<>(key + ": " + value));
        });
        typeRoot.getChildren().add(arguments);

        actionsTreeView.getRoot().getChildren().add(typeRoot);
    }
}
