package be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.tabhandler.attacktabhandler;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.tabhandler.TabHandler;
import javafx.scene.control.ToggleButton;

public class AttackHandler implements TabHandler {

    public void handleUIUpdate(MenuModel model) {
        ToggleButton button = model.getTabs().attack().getBaseAttack();
        if (!model.isAttacking()) button.getStyleClass().removeAll("SelectedButton");
        updateButtonStyle(button, canHandle(model));
    }

    public boolean canHandle(MenuModel model) {
        Minion selected = model.getSelectedSpelveldMinion();
        return selected != null && selected.isNotAttacked() && model.canAttack();
    }
}