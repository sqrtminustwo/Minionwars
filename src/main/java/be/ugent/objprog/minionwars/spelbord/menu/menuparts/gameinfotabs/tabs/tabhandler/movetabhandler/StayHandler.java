package be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.tabhandler.movetabhandler;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.tabhandler.TabHandler;

public class StayHandler implements TabHandler {

    public void handleUIUpdate(MenuModel model) {
        updateButtonStyle(model.getTabs().move().getSkipMove(), canHandle(model));
    }

    public boolean canHandle(MenuModel model) {
        return model.getSelectedSpelveldMinion() != null && !model.getSelectedSpelveldMinion().isMoved();
    }
}