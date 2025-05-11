package be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.minionhandler;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;

public class AfhandelingHandler implements MinionHandler {
    public boolean canHandle(MenuModel model) { return model.getSelectedSpelveldMinion() != null; }
    public void handleExecute(MenuModel model, Minion minion) {
        minion.getStyleClass().clear();
        if (minion.isAfgehandeld()) minion.getStyleClass().add("MinionMainNotActive");
        else minion.getStyleClass().add("MinionMain");
    }
}