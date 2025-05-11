package be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.minionhandler;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;

public interface MinionHandler {
    default  void handle(MenuModel model, Minion minion) { if (canHandle(model)) handleExecute(model, minion); }
    boolean canHandle(MenuModel model);
    void handleExecute(MenuModel model, Minion minion);
}