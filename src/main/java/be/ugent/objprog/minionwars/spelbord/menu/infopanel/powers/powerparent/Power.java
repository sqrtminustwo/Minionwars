package be.ugent.objprog.minionwars.spelbord.menu.infopanel.powers.powerparent;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import be.ugent.objprog.minionwars.spelbord.menu.effects.effectparent.Effect;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.infopanelparent.Infopanel;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public abstract class Power extends Infopanel {

    private final String selectedStyle;
    private final Color blendColor;
    private String primaryIcon;
    private final String name;
    private final Integer radius;
    private final Integer value;
    private final String effectName;
    private final Integer effectValue;
    private Effect effect;

    public Power(String selectedStyle, String blendColor, String imageName, String primaryIcon, String name, Integer radius, Integer value, String effectName, Integer effectvalue) {
        super("powers/" + imageName, name);
        this.selectedStyle = selectedStyle;
        this.blendColor = Color.web(blendColor);
        this.primaryIcon = "attack-D60000.png";
        if (primaryIcon != null) this.primaryIcon = primaryIcon;
        this.name = name;
        this.radius = radius;
        this.value = value;
        this.effectName = effectName;
        this.effectValue = effectvalue;
        startedMainGamePower();
    }

    @Override
    public void setModel(MenuModel model) {
        super.setModel(model);
        effect = model.getBaseEffect(effectName);
        if (effectValue != null) effect.setValue(effectValue);
        setStatsOverview(new PowerStatsOverview(this));
    }

    public Color getBlendColor() { return blendColor; }
    public String getPrimaryIcon() { return primaryIcon; }
    public String getName() { return name; }
    public Integer getRadius() { return radius; }
    public Integer getValue() { return value; }
    public Integer getDuration() { return effect != null ? effect.getDuration() : null; }
    public Effect getGameEffect() { return effect; }

    public void startedMainGamePower() {
        Label effectLabel = new Label("Effect: " + (effect != null ? effect.getName().toLowerCase() : name.toLowerCase()));
        addNewElement(effectLabel);
        showname.getStyleClass().clear();
        effectLabel.getStyleClass().add("EffectLabel");
        removeLeftSpacer();
    }

    public Integer getSortingValue() { return radius; }

    public void invalidatedSpel() {
        if (model.isBonusSelecting() || model.isBonusing() || model.isBonused()) {
            if (model.canUsePower(this)) {
                if (this.equals(model.getSelectedPower())) {
                    updateStyleClass(selectedStyle);
                } else {
                    updateStyleClass("PowerPanel");
                }
            } else {
                updateStyleClass("PowerNotActive");
            }
        }
    }
    public void handleSpel() {
        if (!model.canUsePower(this)) return;

        if (this.equals(model.getSelectedPower())) {
            model.setSelectedPower(null);
            model.setBonusSelecting();
        } else {
            model.setSelectedPower(this);
            model.setBonusing();
        }
    }

    public boolean canApplyOn(Minion minion) { return false; }
    public abstract void apply(Minion minion);
    public abstract void applyEffect(Minion minion);
}