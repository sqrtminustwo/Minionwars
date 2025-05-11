package be.ugent.objprog.minionwars.spelbord;

import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;
import be.ugent.objprog.minionwars.startscherm.input.Speler;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import java.util.ArrayList;
import java.util.List;

public abstract class SpelbordSubModel implements InvalidationListener, Observable {

    private final List<InvalidationListener> listeners;
    protected Spelbord model;

    public SpelbordSubModel(Spelbord model) {
        this.model = model;
        model.addListener(this);
        listeners = new ArrayList<>();
    }

    public boolean isStartingMainGame() { return model.isStartingMainGame(); }
    public boolean isStartedMainGame() { return model.isStartedMainGame(); }
    public boolean isCurrentPlayerMinion(Minion minion) { return model.isCurrentPlayerMinion(minion); }
    public boolean isChangingPlayer() { return model.isChangingPlayer(); }
    public boolean isPlacing() { return model.isPlacing(); }
    public boolean isDeleting() { return model.isDeleting(); }
    public boolean isMoveSelecting() { return model.isMoveSelecting(); }
    public boolean isAttackSelecting() { return model.isAttackSelecting(); }
    public boolean isAttacking() { return model.isAttacking(); }
    public boolean isBaseAttacking() { return model.isBaseAttacking(); }
    public boolean isSpecialAttacking() { return model.isSpecialAttacking(); }
    public boolean isBonusSelecting() { return model.isBonusSelecting(); }
    public boolean isBonusing() { return model.isBonusing(); }
    public boolean isBonused() { return model.isBonused(); }

    public Speler getCurrentSpeler() { return model.getCurrentSpeler(); }
    public void setMoved() { model.setMoved(); }

    public void damageMinion(Minion minion, Integer value) { model.damageMinion(minion, value); }
    public void restoreMinion(Minion minion, Integer value) { model.restoreMinion(minion, value); }

    @Override
    public void invalidated(Observable ignore) {
        if (model.isStartingMainGame()) startedMainGame();
        for (InvalidationListener listener : listeners) {
            listener.invalidated(null);
        }
    }

    public abstract void startedMainGame();

    @Override
    public void addListener(InvalidationListener invalidationListener) { listeners.add(invalidationListener); }

    @Override
    public void removeListener(InvalidationListener invalidationListener) { listeners.remove(invalidationListener); }
}