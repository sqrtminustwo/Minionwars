package be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.tabhandler;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import javafx.scene.control.Control;

public interface TabHandler {

    default void updateButtonStyle(Control button, boolean enabled) {
        if (!enabled) {
            button.getStyleClass().removeAll("SelectedButton");
            button.getStyleClass().removeAll("ButtonUsed");
            button.getStyleClass().add("ButtonUsed");
            button.setDisable(true);
        } else {
            button.getStyleClass().removeAll("ButtonUsed");
            button.setDisable(false);
        }
    }

    void handleUIUpdate(MenuModel model);
    boolean canHandle(MenuModel model);
}