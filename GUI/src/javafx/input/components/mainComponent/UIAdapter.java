package javafx.input.components.mainComponent;


import javafx.application.Platform;
import javafx.input.components.singleEntity.EntityData;

import java.util.function.Consumer;

public class UIAdapter {
    private Consumer<EntityData> introduceNewEntity;

    public UIAdapter(Consumer<EntityData> introduceNewEntity) {
        this.introduceNewEntity = introduceNewEntity;
    }

    public void addNewWord(EntityData entityData) {
        Platform.runLater(
                () -> {
                    introduceNewEntity.accept(entityData);
                }
        );
    }
}
