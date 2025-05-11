package be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.buildgameinfo;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import javafx.scene.layout.VBox;

public interface BuildGameInfo {

    default void build(MenuModel model, VBox container) {
        if (canBuild(model)) buildThis(model, container);
    }

    boolean canBuild(MenuModel model);
    void buildThis(MenuModel model, VBox container);
}