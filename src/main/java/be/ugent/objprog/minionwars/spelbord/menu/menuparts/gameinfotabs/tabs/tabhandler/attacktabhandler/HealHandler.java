package be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.tabhandler.attacktabhandler;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.tabhandler.TabHandler;

public class HealHandler implements TabHandler {

    public void handleUIUpdate(MenuModel model) {
        updateButtonStyle(model.getTabs().attack().getHeal(), canHandle(model));
    }

    public boolean canHandle(MenuModel model) {
        return model.getSelectedSpelveldMinion().isNotAttacked() && model.getSelectedSpelveldMinion().canHeal();
    }
}