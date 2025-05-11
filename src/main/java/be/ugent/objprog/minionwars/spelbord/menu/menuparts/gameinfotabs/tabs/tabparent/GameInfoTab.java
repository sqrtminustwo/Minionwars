package be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.tabparent;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.layout.*;

public abstract class GameInfoTab extends Tab implements ChangeListener<Boolean>, InvalidationListener {

    protected MenuModel model;
    protected StackPane pane;
    protected VBox tabActions;

    public GameInfoTab(String name, MenuModel model) {
        this.model = model;
        setText(name);
        setClosable(false);
        model.addListener(this);
        selectedProperty().addListener((ChangeListener<? super Boolean>) this);

        pane = new StackPane();
        setContent(pane);

        tabActions = new VBox();
        VBox.setVgrow(tabActions, Priority.ALWAYS);
    }

    public void setSpacingAndAlignment() {
        tabActions.setSpacing(10);
        tabActions.setAlignment(Pos.CENTER);
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue && model.getSelectedSpelveldMinion() != null) {
            model.setChangingTab();
            change();
        }
    }

    public abstract void change();
    public void skip() {}
}