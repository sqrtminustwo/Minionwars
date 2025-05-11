package be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.buildgameinfo;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;
import javafx.scene.layout.VBox;

public class BuildMinionInfo implements BuildGameInfo {

    public boolean canBuild(MenuModel model) { return model.getSelectedSpelveldMinion() != null; }

    public void buildThis(MenuModel model, VBox container) {
        Minion selected = model.getSelectedSpelveldMinion();
        container.getChildren().add(selected);
        container.getChildren().add(selected.getAppliedEffects());
    }
}