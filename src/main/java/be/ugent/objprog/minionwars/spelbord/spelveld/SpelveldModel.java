package be.ugent.objprog.minionwars.spelbord.spelveld;

import be.ugent.objprog.minionwars.configloader.fieldloader.FieldLoader;
import be.ugent.objprog.minionwars.spelbord.Spelbord;
import be.ugent.objprog.minionwars.spelbord.SpelbordSubModel;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.powers.powerparent.Power;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.fieldhandler.*;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Field;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Hexagon;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Hexagon.Coords;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.pathfinder.PathCost;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.util.*;

public class SpelveldModel extends SpelbordSubModel {

    private final ScrollPane rootPane;
    private final AnchorPane spelveldPane;
    private final Map<Coords, Field> coordsToField;
    private SelectedFeild selectedField;
    private Field howeredField;
    private final Integer[][][] oddr_direction_differences;
    public record SelectedFeild(Field field, Minion minion) {}
    private final PathCost pathCost;
    public enum SpleveldState {
        CHANGINGSELECTION,
        BONUSABLE,
        FREE
    }
    private SpleveldState currentState;
    private List<FieldHandler> fieldHandlers;
    private final ChangeListener<Boolean> focusListener;
    private final EventHandler<KeyEvent> deleteHandler;

    public SpelveldModel(Spelbord model, ScrollPane spelVeld, FieldLoader loader) {
        super(model);
        rootPane = spelVeld;
        spelveldPane = new AnchorPane();
        rootPane.getStyleClass().add("SpelVeldScrollPane");

        deleteHandler = this::handleDelete;
        rootPane.addEventHandler(KeyEvent.KEY_RELEASED, deleteHandler);
        focusListener = this::handleNotFocused;
        rootPane.focusedProperty().addListener(focusListener);
        rootPane.setContent(spelveldPane);

        setNotChangingSelection();

        pathCost = new PathCost(this);
        oddr_direction_differences = new Integer[][][]{
                {{1, 0}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}},
                {{1, 0}, {1, -1}, {0, -1}, {-1, 0}, {0, 1}, {1, 1}}
        };
        fieldHandlers = List.of(
                new PlaceHandler(),
                new SelectionHandler(),
                new DeleteHandler(),
                new FillHandler(),
                new HomebaseHandler()
        );

        coordsToField = new HashMap<>();
        spelveldPane.setPadding(new Insets(loader.getPadding()));
        for (Field field: loader.getElements()) {
            field.setModel(this);
            coordsToField.put(field.getCoords(), field);
            spelveldPane.getChildren().addAll(field, field.getBlend());
        }
    }

    public boolean isChangingSelection() { return currentState == SpleveldState.CHANGINGSELECTION; }
    public boolean inMovementRange(Field goalField) {
        return inMovementRangeCalculate(
                model.getSelectedField(),
                goalField,
                getSelectedSpelveldMinion().getMovementRange()
        );
    }
    private boolean inMovementRangeCalculate(Field startField, Field goalField, Integer maxRange) {
        Integer cost = pathCost.search(startField, goalField);
        return cost != null && cost != 0 && cost <= maxRange;
    }
    public boolean canAttack() {
        if (getSelectedField() != null && getSelectedField().isCanAttackFrom()) {
            for (Field field: coordsToField.values()) {
                if (field.hasMinion() && !model.isCurrentPlayerMinion(field.getMinion()) && inAttackRange(field)) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean inAttackRange(Field goalField) {
        return inAttackRangeCalculate(
                model.getSelectedField(),
                goalField,
                getSelectedSpelveldMinion().getMaxAttackRange(),
                getSelectedSpelveldMinion().getMinAttackRange()
        );
    }
    private boolean inAttackRangeCalculate(Field startField, Field goalField, Integer maxRange, Integer minRange) {
        if (model.getSelectedField() != null && model.getSelectedSpelveldMinion() != null) {
            Coords minionAxial = oddrToAxial(startField.getCoords().x(), startField.getCoords().y());
            Coords fieldAxial = oddrToAxial(goalField.getCoords().x(), goalField.getCoords().y());

            int dx = fieldAxial.x() - minionAxial.x();
            int dy = fieldAxial.y() - minionAxial.y();
            int dz = -dx - dy;
            int distance = (Math.abs(dx) + Math.abs(dy) + Math.abs(dz)) / 2;

            return distance <= maxRange && (minRange == null || distance >= minRange);
        }
        return false;
    }
    public boolean inBonusRange(Field goalField) {
        return howeredField != null && inAttackRangeCalculate(
                howeredField,
                goalField,
                getSelectedPower().getRadius(),
                null
        );
    }
    public boolean canBonus() { return currentState == SpleveldState.BONUSABLE; }
    public boolean checkCanBonus() {
        for (Field field : coordsToField.values()) {
            if (field.hasMinion() && inBonusRange(field) && model.getSelectedPower().canApplyOn(field.getMinion())) {
                return true;
            }
        }
        return false;
    }

    public Field getSelectedField() { return selectedField != null ? selectedField.field : null; }
    public Minion getSelectedSpelveldMinion() { return selectedField != null ? selectedField.minion : null; }
    public Minion getSelectedMenuMinion() { return model.getSelectedMenuMinion(); }
    public List<Field> getFieldNeighbors(Field field) {
        List<Field> neighbors = new ArrayList<>();
        Coords coords = field.getCoords();
        for (Integer[] direction : oddr_direction_differences[coords.y()%2]) {
            Field neighbour = coordsToField(new Hexagon.Coords(coords.x()+direction[0], coords.y()+direction[1]));
            if (neighbour != null && !neighbour.hasMinion() && neighbour.isWalkable()) neighbors.add(neighbour);
        }
        return neighbors;
    }
    public Power getSelectedPower() { return model.getSelectedPower(); }
    public List<FieldHandler> getHandlers() { return fieldHandlers; }
    public Field coordsToField(Hexagon.Coords coords) { return coordsToField.get(coords); }

    public void setChangingSelection() { currentState = SpleveldState.CHANGINGSELECTION; }
    public void setNotChangingSelection() { currentState = SpleveldState.FREE; }
    public void setHoweredField(Field field) {
        currentState = SpleveldState.FREE;
        this.howeredField = field;
        if (!isBonusing()) return;
        if (checkCanBonus()) currentState = SpleveldState.BONUSABLE;
        invalidated(this);
    }
    public void setSelectedField(Field field, Minion minion) { selectedField = new SelectedFeild(field, minion); model.setFree(); }
    public void placedSelectedMinion() { model.placedSelectedMinion(); }
    public void updatedSpelVeldModel() { setHoweredField(null); }

    private Coords oddrToAxial(int col, int row) { return new Coords(col-(row-(row%2))/2, row); }
    public void attackMinion(Minion minion) {
        if (isBaseAttacking()) baseAttackMinion(minion);
        else if (isSpecialAttacking()) specialAttackMinion(minion);
    }
    public void baseAttackMinion(Minion minion) { model.baseAttackMinion(minion); }
    public void specialAttackMinion(Minion minion) { model.specialAttackMinion(minion); }
    public void utilizeBonus() {
        for (Field field : coordsToField.values()) {
            if (field.isBonusable()) getSelectedPower().apply(field.getMinion());
        }
        model.setBonused();
    }
    public void deleteMinion(Minion minion) {
        for (Field field : coordsToField.values()) {
            if (minion.equals(field.getMinion())) {
                field.setPlacedMinion(null);
                break;
            }
        }
    }
    public void handleDelete(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE && getSelectedSpelveldMinion() != null) {
            model.deleteSelectedMinion();
            model.clearFieldSelectionSpelveld();
        }
    }
    public void handleNotFocused(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        if (!newValue) model.clearFieldSelectionSpelveld();
    }
    private void handleMouseExited(MouseEvent mouseEvent) { updatedSpelVeldModel(); }

    public void startedMainGame() {
        rootPane.focusedProperty().removeListener(focusListener);
        rootPane.removeEventHandler(KeyEvent.KEY_RELEASED,  deleteHandler);
        spelveldPane.setOnMouseExited(this::handleMouseExited);
        fieldHandlers = List.of(
                new FillHandler(),
                new MoveHandler(),
                new AttackHandler(),
                new BonusHandler(),
                new SelectionHandler()
        );
    }
}