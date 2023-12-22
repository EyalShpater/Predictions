package component.details.rules;

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
        mainScreen.textProperty().bind(ruleName);
        nameLabel.textProperty().bind(ruleName);
        numOfTicksLabel.textProperty().bind(numOfTicks.asString());
        probabilityLabel.textProperty().bind(probability.asString());

        actionsTreeView.setRoot(new TreeItem<>("Actions"));
        actionsTreeView.setShowRoot(false);
    }

    public void setDataFromDTO(RuleDTO rule) {
        ruleName.set(rule.getName());
        numOfTicks.set(rule.getTicks());
        probability.set(rule.getProbability());
        rule.getActionsDTO().forEach(this::addActionToTreeView);
    }

    private void addActionToTreeView(ActionDTO action) {
        TreeItem<String> typeRoot = new TreeItem<>(action.getType().toLowerCase());

        typeRoot.getChildren().add(new TreeItem<>("Main Entity: " + action.getMainEntity().getName()));
        if (action.getSecondaryEntity() != null) {
            typeRoot.getChildren().add(new TreeItem<>("Secondary Entity: " + action.getSecondaryEntity().getName()));
        }

        if (action.getAdditionalInformation() != null) {
            action.getAdditionalInformation()
                    .forEach((key, value) -> typeRoot
                            .getChildren()
                            .add(new TreeItem<>(new String(key + ": " + value)))
                    );
        }

        if (action.getArguments() != null) {
            TreeItem<String> arguments = new TreeItem<>("Arguments");
            action.getArguments().forEach((key, value) -> {
                arguments.getChildren().add(new TreeItem<>(key + ": " + value));
            });

            typeRoot.getChildren().add(arguments);
        }

        actionsTreeView.getRoot().getChildren().add(typeRoot);
    }
}
