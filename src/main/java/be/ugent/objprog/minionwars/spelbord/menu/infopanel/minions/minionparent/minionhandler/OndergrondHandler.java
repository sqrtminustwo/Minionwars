package be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.minionhandler;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;

public class OndergrondHandler implements MinionHandler {
    public boolean canHandle(MenuModel model) { return model.getSelectedField() != null; }
    public void handleExecute(MenuModel model, Minion minion) {
        minion.getOndergrond().setText("Ondergrond: " + model.getSelectedField().getName());
    }
}