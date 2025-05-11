package be.ugent.objprog.minionwars.configloader.powerloader;

import be.ugent.objprog.minionwars.spelbord.menu.infopanel.powers.powerparent.Power;

public interface PowerFactory {
    Power create(String name, Integer radius, Integer value, String effectName, Integer effectvalue);
}