package be.ugent.objprog.minionwars.spelbord;
import be.ugent.objprog.minionwars.configloader.effectloader.EffectLoader;
import be.ugent.objprog.minionwars.configloader.fieldloader.FieldLoader;
import be.ugent.objprog.minionwars.configloader.minionloader.MinionLoader;
import be.ugent.objprog.minionwars.configloader.powerloader.PowerLoader;
import be.ugent.objprog.minionwars.controllers.SpelbordController;
import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import be.ugent.objprog.minionwars.spelbord.menu.effects.effectparent.Effect;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.powers.powerparent.Power;
import be.ugent.objprog.minionwars.spelbord.spelveld.SpelveldModel;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Field;
import be.ugent.objprog.minionwars.startscherm.input.Speler;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import org.jdom2.Element;
import java.util.ArrayList;
import java.util.List;

public class Spelbord implements Observable {

    private final SpelbordController controller;
    private Speler currentSpeler;
    private final SpelveldModel spelveldModel;
    private final MenuModel menuModel;
    private final List<InvalidationListener> listenerList;
    public enum ChangeState {
        STARTINGMAINGAME,
        STARTEDMAINGAME,
        NOTCHANGING
    }

    public enum State {
        CHANGINGPLAYER,
        CHANGINGTAB,
        PLACING,
        PLACED,
        DELETING,
        MOVESELECTION,
        ATTACKSELECTION,
        ATTACKING,
        SPECIALATTACKING,
        BONUSSELECTION,
        BONUSING,
        BONUSED,
        FREE
    }
    private ChangeState changeState;
    private State gameState;

    public Spelbord(SpelbordController controller, Element config, BorderPane menuPane, ScrollPane spelVeld) {
        this.controller = controller;
        currentSpeler = getSpelers().getFirst();
        listenerList = new ArrayList<>();

        this.menuModel = new MenuModel(this, menuPane,
                new EffectLoader(config.getChild("effects").getChildren()),
                new PowerLoader(config.getChild("powers").getChildren()),
                new MinionLoader(config.getChild("minions").getChildren())
        );

        this.spelveldModel = new SpelveldModel(this, spelVeld,
                new FieldLoader(config.getChild("field").getChildren())
        );

        changeState = ChangeState.NOTCHANGING;
        gameState = State.FREE;
        fireInvalidation();
    }

    public boolean isStartedMainGame() { return isStartingMainGame() || changeState == ChangeState.STARTEDMAINGAME; }
    public boolean isStartingMainGame() { return changeState == ChangeState.STARTINGMAINGAME; }
    public boolean isCurrentPlayerMinion(Minion minion) { return currentSpeler.isOwnerOf(minion); }
    public boolean isChangingPlayer() { return gameState == State.CHANGINGPLAYER; }
    public boolean isPlacing() { return gameState == State.PLACING; }
    public boolean isPlaced() { return gameState == State.PLACED; }
    public boolean isDeleting() { return gameState == State.DELETING; }
    public boolean isMoveSelecting() { return gameState == State.MOVESELECTION; }
    public boolean isAttackSelecting() { return gameState == State.ATTACKSELECTION; }
    public boolean isAttacking() { return isBaseAttacking() || isSpecialAttacking(); }
    public boolean isBaseAttacking() { return gameState == State.ATTACKING; }
    public boolean isSpecialAttacking() { return gameState == State.SPECIALATTACKING; }
    public boolean isBonusSelecting() { return gameState == State.BONUSSELECTION; }
    public boolean isBonusing() { return gameState == State.BONUSING; }
    public boolean isBonused() { return gameState == State.BONUSED; }
    public boolean isChangingSelection() { return spelveldModel.isChangingSelection(); }
    public boolean canAttack() { return spelveldModel.canAttack(); }

    public void setPlacing() { gameState = State.PLACING; }
    public void setFree() { gameState = State.FREE; fireInvalidation(); }
    public void setRested() { setFree(); }
    public void setMoveSelecting() { gameState = State.MOVESELECTION; fireInvalidation(); }
    public void setMoved() {
        spelveldModel.getSelectedSpelveldMinion().setMoved();
        fireInvalidation();
        setFree();
    }
    public void setAttackSelecting() { gameState = State.ATTACKSELECTION; fireInvalidation(); }
    public void setAttacking() { gameState = State.ATTACKING; fireInvalidation(); }
    public void setSpecialAttacking() { gameState = State.SPECIALATTACKING; fireInvalidation(); }
    public void setAttacked() {
        spelveldModel.getSelectedSpelveldMinion().setAttacked();
        fireInvalidation();
        setFree();
    }
    public void setBonusSelecting() { gameState = State.BONUSSELECTION; fireInvalidation(); }
    public void setBonusing() { gameState = State.BONUSING; fireInvalidation(); }
    public void setBonused() {
        gameState = State.BONUSED;
        currentSpeler.usedPower(getSelectedPower());
        spelveldModel.updatedSpelVeldModel();
        fireInvalidation();
        setFree();
    }
    public void setNotChangingSelection() { spelveldModel.setNotChangingSelection(); }
    public void setChangingTab() {
        gameState = State.CHANGINGTAB;
        menuModel.setSelectedPower(null);
        fireInvalidation();
    }

    public List<Speler> getSpelers() { return controller.getSpelers(); }
    public Speler getCurrentSpeler() { return currentSpeler; }
    public Speler getOponentSpeler() {
        return getSpelers().get((currentSpeler.getNummer()+1)%getSpelers().size());
    }
    public Speler getOwner(Minion minion) {
        return isCurrentPlayerMinion(minion) ? currentSpeler : getOponentSpeler();
    }
    public Minion getSelectedMenuMinion() { return menuModel.getSelectedMenuMinion(); }
    public Minion getSelectedSpelveldMinion() { return spelveldModel.getSelectedSpelveldMinion(); }
    public Field getSelectedField() { return spelveldModel.getSelectedField(); }
    public Power getSelectedPower() { return menuModel.getSelectedPower();}
    public void clearFieldSelectionSpelveld() { spelveldModel.setSelectedField(null, null); }
    public void clearFieldSelectionMenu() { menuModel.setSelectedMenuMinion(null); }

    public void deleteSelectedMinion() {
        gameState = State.DELETING;
        Minion minion = spelveldModel.getSelectedSpelveldMinion();
        if (!isStartedMainGame())  currentSpeler.setMunten(currentSpeler.getMunten() + minion.getCost());
        currentSpeler.removeMinion(minion);
        spelveldModel.removeListener(minion);
        spelveldModel.invalidated(null);
        fireInvalidation();
        gameState = State.FREE;
    }

    public void baseAttackMinion(Minion minion) {
        Minion attackingMinion = spelveldModel.getSelectedSpelveldMinion();
        damageMinion(minion, attackingMinion.getAttack());
        setAttacked();
    }
    public void specialAttackMinion(Minion target) {
        Minion attacker = spelveldModel.getSelectedSpelveldMinion();
        Effect effect = attacker.getGameEffect();
        (effect.isFriendly() ? attacker : target).addGameEffect(effect);
        attacker.setSpecialAttacked();
        baseAttackMinion(target);
    }

    public void damageMinion(Minion minion, Integer value) {
        minion.damageMinion(value);
        if (minion.getCurDefence() == 0) {
            spelveldModel.deleteMinion(minion);
            getOwner(minion).removeMinion(minion);
            spelveldModel.invalidated(null);
            checkWon();
        }
    }
    public void restoreMinion(Minion minion, Integer value) { minion.restoreMinion(value); }

    public void placedSelectedMinion() {
        gameState = State.PLACED;
        Minion placingMinion = menuModel.getSelectedMenuMinion();
        currentSpeler.setMunten(currentSpeler.getMunten() - placingMinion.getCost());
        currentSpeler.addMinion(placingMinion);
        fireInvalidation();
        gameState = State.FREE;
        menuModel.setSelectedMenuMinion(null);
    }

    public void changeSpeler() {
        clearFieldSelectionSpelveld();
        clearFieldSelectionMenu();

        if (!isStartedMainGame()) {
            currentSpeler.setPlaced(true);
            changeState = getSpelers().stream().allMatch(Speler::isPlaced) ? ChangeState.STARTINGMAINGAME : ChangeState.NOTCHANGING;
        }
        currentSpeler = getOponentSpeler();
        gameState = State.CHANGINGPLAYER;
        fireInvalidation();

        if (isStartingMainGame()) {
            changeState = ChangeState.STARTEDMAINGAME;
            fireInvalidation();
        }

        gameState = State.FREE;
    }
    public void checkWon() {
        List<Speler> winners = getSpelers().stream().filter(s -> s.getAantalMinions() != 0).toList();
        if (winners.size() == 1) controller.nextFase(winners.getFirst());
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) { listenerList.add(invalidationListener); }
    @Override
    public void removeListener(InvalidationListener invalidationListener) { listenerList.remove(invalidationListener); }

    public void fireInvalidation() {
        if (isStartedMainGame()) checkWon();
        for (InvalidationListener listener : listenerList) listener.invalidated(this);
    }
}