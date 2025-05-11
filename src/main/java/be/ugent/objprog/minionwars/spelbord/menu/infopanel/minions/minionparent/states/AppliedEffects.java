package be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.states;

import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class AppliedEffects extends ScrollPane {

    private final VBox holder;
    private final List<AppliedEffect> effects;
    private final List<AppliedEffect> effectsToRemoveFromUI;

    public AppliedEffects() {
        holder = new VBox();
        effects = new ArrayList<>();
        effectsToRemoveFromUI = new ArrayList<>();
        setContent(holder);
        setMaxHeight(200);
        setMinHeight(0);
        setFitToWidth(true);
        setHbarPolicy(ScrollBarPolicy.NEVER);
        holder.setFillWidth(true);
    }

    public void addEffect(AppliedEffect effect) { effects.add(effect); }

    public void applyEffects(Minion minion) {
        holder.getChildren().clear();
        List<AppliedEffect> effectsToProcess = new ArrayList<>(effects);

        for (AppliedEffect appliedEffect: effectsToProcess) {
            appliedEffect.applyOn(minion);
            holder.getChildren().add(appliedEffect);
            if (appliedEffect.isDone()) {
                effects.remove(appliedEffect);
                effectsToRemoveFromUI.add(appliedEffect);
            }
        }
    }
    public void applyVisual() {
        for (AppliedEffect appliedEffect: effects) appliedEffect.updateStatus();
        for (AppliedEffect toRemoveEffect: effectsToRemoveFromUI) holder.getChildren().remove(toRemoveEffect);
    }
}