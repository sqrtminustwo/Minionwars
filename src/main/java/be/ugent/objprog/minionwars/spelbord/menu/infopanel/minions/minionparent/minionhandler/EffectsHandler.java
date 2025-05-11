package be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.minionhandler;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;

public class EffectsHandler implements MinionHandler {
    public boolean canHandle(MenuModel model) { return model.isChangingPlayer(); }
    public void handleExecute(MenuModel model, Minion minion) {
        minion.reset();
        if (model.isCurrentPlayerMinion(minion)) minion.getAppliedEffects().applyEffects(minion);
        else minion.getAppliedEffects().applyVisual();
    }
}