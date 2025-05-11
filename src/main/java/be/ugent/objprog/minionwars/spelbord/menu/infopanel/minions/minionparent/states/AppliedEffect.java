package be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.states;

import be.ugent.objprog.minionwars.spelbord.menu.effects.effectparent.Effect;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class AppliedEffect extends HBox {
    private final Effect effect;
    private Integer duration;
    private final Label durationLabel;

    public AppliedEffect(Effect effect, Integer duration) {
        this.effect = effect;
        this.duration = duration;

        Label nameLabel = new Label(effect.getName());
        durationLabel = new Label();

        setMaxWidth(Double.MAX_VALUE);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        getChildren().addAll(nameLabel, spacer, durationLabel);
        getStyleClass().add("InfoPanelLabel");
        updateStatus();
    }

    public boolean isDone() { return duration <= 0; }
    public void used() {
        duration--;
    }

    public void updateStatus() { durationLabel.setText(getNewStatus(duration)); }
    public String getNewStatus(Integer visualDuration) {
        return "nog " + visualDuration + (visualDuration <= 1 ? " beurt" : " beurten");
    }

    public void applyOn(Minion minion) { effect.apply(minion); used(); }
}