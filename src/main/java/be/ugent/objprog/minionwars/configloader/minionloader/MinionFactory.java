package be.ugent.objprog.minionwars.configloader.minionloader;

import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;

@FunctionalInterface
public interface MinionFactory {

    Minion create(String type, String name, int cost, int movement, Minion.Range range, int attack, int defence, String effectName, Integer effectValue);

    default Minion cloneFrom(Minion original) {
        Minion minion = create(
                original.getType(),
                original.getName(),
                original.getCost(),
                original.getMovementRange(),
                original.getRange(),
                original.getAttack(),
                original.getDefence(),
                original.getEffectName(),
                original.getEffectValue()
        );
        minion.setModel(original.getModel());
        return minion;
    }
}