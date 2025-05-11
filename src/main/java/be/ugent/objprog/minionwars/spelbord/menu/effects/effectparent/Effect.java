package be.ugent.objprog.minionwars.spelbord.menu.effects.effectparent;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;

public abstract class Effect {

    protected MenuModel model;
    private final String name;
    private final String confName;
    private final boolean friendly;
    private Integer duration;
    private Integer value;

    public Effect(String confName, String name, Integer duration, Integer value, boolean friendly) {
        this.confName = confName;
        this.name = name;
        this.duration = duration;
        this.value = value;
        this.friendly = friendly;
    }

    public boolean isFriendly() { return friendly; }

    public String getConfName() { return confName; }
    public String getName() { return name; }
    public Integer getDuration() { return duration; }
    public Integer getValue() { return value; }

    public void setModel(MenuModel model) { this.model = model; }
    public void setValue(Integer value) { this.value = value; }
    public void setDuration(Integer duration) { this.duration = duration; }
    public void apply(Minion minion) {
        if (getValue() != null) applyValue(minion);
        applyNonValue(minion);
    }
    public abstract void applyValue(Minion minion);
    public abstract void applyNonValue(Minion minion);
}