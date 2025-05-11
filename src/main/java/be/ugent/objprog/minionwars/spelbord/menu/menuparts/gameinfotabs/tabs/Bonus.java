package be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.tabparent.GameInfoTab;
import javafx.beans.Observable;

public class Bonus extends GameInfoTab {

    public Bonus(MenuModel model) {
        super("Bonus", model);
        tabActions.getChildren().addAll(model.getPowers());
        pane.getChildren().add(tabActions);
    }

    public void change() { model.setBonusSelecting(); }
    public void invalidated(Observable ignore) {}
}