package be.ugent.objprog.minionwars.configloader.effectloader;

import be.ugent.objprog.minionwars.spelbord.menu.effects.effectparent.Effect;

public interface EffectFactory {
    Effect create(String confName, String name, Integer duration, Integer value);
}