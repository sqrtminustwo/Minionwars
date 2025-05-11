package be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import be.ugent.objprog.minionwars.spelbord.menu.effects.effectparent.Effect;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.infopanelparent.Infopanel;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.minionhandler.AfhandelingHandler;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.minionhandler.EffectsHandler;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.minionhandler.MinionHandler;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.minionhandler.OndergrondHandler;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.states.Afhandeling;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.states.AppliedEffect;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.states.AppliedEffects;
import javafx.scene.control.Label;
import javafx.scene.paint.ImagePattern;

import java.util.List;

public class Minion extends Infopanel {

    private boolean activated;
    private final Label ondergrond;

    private final String type;
    private final String name;
    private final int cost;
    private final int movement;
    private final int attack;
    private int curDefence;
    private final int defence;
    private final String effectName;
    private final Integer effectValue;
    private Effect specialEffect;
    private boolean canUseSpecial;
    private final AppliedEffects appliedEffects;

    public record Range(int min, int max) {}
    private final Range range;

    private int healedCounter;
    private final Afhandeling afhandeling;
    private Integer restCounter;
    private Integer moveRangeLimit;
    private Integer attackRangeLimit;
    private Integer extraAttack;
    private final List<MinionHandler> handlers;

    public Minion(String type, String name, int cost, int movement, Range range, int attack, int defence, String imagePath, String effectName, Integer effectValue) {
        super("minions/" + imagePath, name);
        activated = false;
        canUseSpecial = true;
        afhandeling = new Afhandeling();
        this.type = type;
        this.name = name;
        this.cost = cost;
        this.movement = movement;
        this.range = range;
        this.attack = attack;
        this.defence = defence;
        this.curDefence = defence;
        this.effectName = effectName;
        this.effectValue = effectValue;
        appliedEffects = new AppliedEffects();
        restCounter = 0;
        moveRangeLimit = 0;
        attackRangeLimit = 0;
        extraAttack = 0;

        ondergrond = new Label();
        setStatsOverview(new MinionStatsOverview(this));
        handlers = List.of(new OndergrondHandler(), new EffectsHandler(), new AfhandelingHandler());
    }

    @Override
    public void setModel(MenuModel model) {
        super.setModel(model);
        if (effectName != null) specialEffect = model.getMinionEffect(effectName, effectValue);
        statsOverview.setModel(model);
    }

    public void startedMainGame() {
        setSpacing(5);
        statsOverview.startedMainGame();
        updateStyleClass("MinionMain");
        addNewElement(ondergrond);
    }

    public void reset() {
        afhandeling.reset();
        moveRangeLimit = 0;
        attackRangeLimit = 0;
        extraAttack = 0;
    }

    public boolean isAfgehandeld() { return afhandeling.isAfgehandeld(); }
    public boolean isMoved() { return afhandeling.isMoved(); }
    public boolean isNotAttacked() { return !afhandeling.isAttacked(); }
    public boolean canSpecialAttack() { return canUseSpecial; }
    public boolean canHeal() { return healedCounter < 2 && curDefence < defence; }
    public boolean canRest() { return afhandeling.canRest(); }

    public int getCost() { return cost; }
    public Integer getSortingValue() { return getCost(); }
    public int getAttack() { return attack+extraAttack; }
    public int getDefence() { return defence; }
    public int getCurDefence() { return curDefence; }
    public int getMovementRange() { return Math.max(0, movement-moveRangeLimit); }
    public Effect getGameEffect() { return specialEffect; }
    public String getEffectName() { return effectName; }
    public Integer getEffectValue() { return effectValue; }
    public Range getRange() { return range; }
    public Integer getMinAttackRange() { return range.min; }
    public Integer getMaxAttackRange() { return Math.max(0, range.max-attackRangeLimit); }
    public String getType() { return type; }
    public String getName() { return name; }
    public ImagePattern getImage() { return image; }
    public AppliedEffects getAppliedEffects() { return appliedEffects; }
    public Label getOndergrond() { return ondergrond; }

    public void damageMinion(Integer value) { curDefence = Math.max(curDefence - value, 0); }
    public void restoreMinion(Integer value) { curDefence = Math.min(curDefence + value, defence); }
    public void rest() {
        if (!canUseSpecial) {
            if (++restCounter >= 2) {
                canUseSpecial = true;
                restCounter = 0;
            }
        }
        setAfgehandeld();
    }
    public void addMoveRangeLimit(Integer value) { moveRangeLimit += value; }
    public void addAttackRangeLimit(Integer value) { attackRangeLimit += value; }
    public void addExtraAttack(Integer value) { extraAttack += value; }
    public void addGameEffect(Effect effect) { appliedEffects.addEffect(new AppliedEffect(effect, effect.getDuration())); }
    public void setAfgehandeld() { setMoved(); setAttacked(); }
    public void setMoved() { afhandeling.setMoved(); }
    public void setAttacked() { afhandeling.setAttacked(); }
    public void setSpecialAttacked() { canUseSpecial = false; }
    public void incrementHealed() { healedCounter++; }

    public void invalidatedStartSpel() {
        activated = model.getCurrentSpeler().getMunten() >= cost;
        Minion selected = model.getSelectedMinion();
        if (!activated) {
            updateStyleClass("MinionNotActive");
            return;
        }
        if (!model.isPlaced() && selected != null && selected.equals(this)) {
            updateStyleClass("MinionSelected");
            model.setPlacing();
        } else {
            updateStyleClass("MinionPanel");
        }
    }

    public void invalidatedSpel() { for (MinionHandler handler : handlers) handler.handle(model, this); }
    public void handleStartSpel() { if (activated) model.setSelectedMenuMinion(this); }
}