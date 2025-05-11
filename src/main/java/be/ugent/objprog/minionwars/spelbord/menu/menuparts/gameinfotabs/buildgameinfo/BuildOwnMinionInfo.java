package be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.buildgameinfo;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.tabparent.GameInfoTab;
import javafx.scene.layout.VBox;

public class BuildOwnMinionInfo implements BuildGameInfo {

    public boolean canBuild(MenuModel model) {
        Minion selected = model.getSelectedSpelveldMinion();
        return selected != null && model.isCurrentPlayerMinion(selected);
    }

    public void buildThis(MenuModel model, VBox container) {
        container.getChildren().add(model.getActies());
        model.setNotChangingSelection();
        model.getActies().getSelectionModel().select(0);
        ((GameInfoTab) model.getActies().getSelectionModel().getSelectedItem()).change();
    }
}