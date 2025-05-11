package be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.buildgameinfo;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import javafx.scene.layout.VBox;

public class BuildFieldInfo implements BuildGameInfo {
    public boolean canBuild(MenuModel model) {
        return model.getSelectedSpelveldMinion() == null && model.getSelectedField() != null;
    }
    public void buildThis(MenuModel model, VBox container) {
        container.getChildren().add(model.getSelectedField().getFieldOverview());
    }
}