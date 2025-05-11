package be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions;

import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;

public class Axe extends Minion {
    public Axe(String type, String name, int cost, int movement, Range range, int attack, int defence, String effectName, Integer effectValue) {
        super(type, name, cost, movement, range, attack, defence, "axe.png", effectName, effectValue);
    }
}