package be.ugent.objprog.minionwars.spelbord.menu;

import be.ugent.objprog.minionwars.configloader.effectloader.EffectLoader;
import be.ugent.objprog.minionwars.configloader.minionloader.MinionLoader;
import be.ugent.objprog.minionwars.configloader.powerloader.PowerLoader;
import be.ugent.objprog.minionwars.spelbord.Spelbord;
import be.ugent.objprog.minionwars.spelbord.SpelbordSubModel;
import be.ugent.objprog.minionwars.spelbord.menu.effects.effectparent.*;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.infopanelparent.Infopanel;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.powers.powerparent.Power;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.GameInfo;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.commontabs.EindeBeurt;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.commontabs.Margin;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.startinfotabs.MinionMenu;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.commontabs.PlayerInfo;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Field;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

import java.util.*;

public class MenuModel extends SpelbordSubModel {

    private final BorderPane menuPane;
    private GameInfo gameInfo;
    private int width;
    private final int margin;
    private final List<Minion> minions;
    private final List<Infopanel> powers;
    private Minion selectedMinion;
    private Minion toReturnMinion;
    private Power selectedPower;

    private final EffectLoader effectLoader;
    private final MinionLoader minionLoader;
    private final PowerLoader powerLoader;
    private final HashMap<String, Effect> baseEffects;


    public MenuModel(Spelbord model, BorderPane menuPane, EffectLoader effectLoader, PowerLoader powerLoader, MinionLoader minionLoader) {
        super(model);
        this.menuPane = menuPane;
        minions = new ArrayList<>();
        baseEffects = new HashMap<>();
        powers = new ArrayList<>();

        this.effectLoader = effectLoader;
        this.powerLoader = powerLoader;
        this.minionLoader = minionLoader;

        width = 330;
        margin = 5;

        startedSelection();
    }

    public boolean isChangingSelection() { return model.isChangingSelection(); }
    public boolean isPlaced() { return model.isPlaced(); }
    public boolean canAttack() { return model.canAttack(); }
    public boolean canUsePower(Power power) { return getSelectedSpelveldMinion() != null && getCurrentSpeler().canUsePower(power); }

    public Minion getSelectedMinion() { return selectedMinion; }
    public Minion getSelectedMenuMinion() { return toReturnMinion; }
    public Minion getSelectedSpelveldMinion() { return model.getSelectedSpelveldMinion(); }
    public Power getSelectedPower() { return selectedPower; }
    public Field getSelectedField() { return model.getSelectedField(); }
    public List<Minion> getMinions() { return minions; }
    public List<Infopanel> getPowers() { return powers; }
    public TabPane getActies() { return gameInfo.getActies(); }
    public GameInfo.Tabs getTabs() { return gameInfo.getTabs(); }
    public Effect getBaseEffect(String name) { return baseEffects.get(name); }
    public Effect getMinionEffect(String effectName, Integer effectValue) {
        Effect baseEffect = baseEffects.get(effectName);
        Effect newEffect = effectLoader.getEffectFactory(effectName).create(
                baseEffect.getConfName(),
                baseEffect.getName(),
                baseEffect.getDuration(),
                effectValue != null ? effectValue : baseEffect.getValue()
        );
        newEffect.setModel(this);
        return newEffect;
    }

    public void changeSpeler() { model.changeSpeler(); }
    public void setPlacing() { model.setPlacing(); }
    public void setChangingTab() { model.setChangingTab(); }
    public void setNotChangingSelection() { model.setNotChangingSelection(); }
    public void setMoveSelecting() { model.setMoveSelecting(); }
    public void setAttackSelecting () { model.setAttackSelecting(); }
    public void setAttacked() { model.setAttacked(); }
    public void setAttacking() { model.setAttacking(); }
    public void setSpecialAttacking() { model.setSpecialAttacking(); }
    public void setBonusSelecting() { model.setBonusSelecting(); }
    public void setBonusing() { model.setBonusing(); }
    public void setRested() { model.setRested(); }
    public void setSelectedMenuMinion(Minion minion) {
        selectedMinion = minion;
        toReturnMinion = selectedMinion != null ? cloneMinion(selectedMinion) : null;
        invalidated(null);
    }
    public void setSelectedPower(Power power) {
        selectedPower = power;
        invalidated(null);
    }

    public void startedSelection() {
        for (Effect effect: effectLoader.getElements()) { effect.setModel(this); baseEffects.put(effect.getConfName(), effect); }
        for (Minion minion: minionLoader.getElements()) { minion.setModel(this); minions.add(minion); }
        minions.sort(Comparator.comparingInt(Minion::getSortingValue).reversed());

        menuPane.setLeft(new Margin(margin));
        menuPane.setRight(new Margin(margin));
        menuPane.setCenter(new MinionMenu(this, width, margin+5));
        menuPane.getStyleClass().add("Menu");

        width += 2*margin;
        PlayerInfo playerinfo = new PlayerInfo(this, margin, width);
        menuPane.setTop(playerinfo);
        menuPane.setBottom(new EindeBeurt(this));
        menuPane.setPrefWidth(width);
        menuPane.setMinWidth(width);
        menuPane.setMaxWidth(width);
    }

    public Minion cloneMinion(Minion minion) {
        return minionLoader.getMinionFactory(minion.getType()).cloneFrom(minion);
    }

    public void startedMainGame() {
        for (Power power: powerLoader.getElements()) { power.setModel(this); powers.add(power); }
        for (Infopanel minion: minions) minion.removeAsListener();

        menuPane.setLeft(new Margin(margin));
        menuPane.setRight(new Margin(margin));
        gameInfo = new GameInfo(this, width, margin);
        menuPane.setCenter(gameInfo);
    }
}