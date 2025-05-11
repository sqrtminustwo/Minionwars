package be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent;

import be.ugent.objprog.minionwars.MinionWars;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;
import be.ugent.objprog.minionwars.spelbord.spelveld.SpelveldModel;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.fieldhandler.FieldHandler;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.fieldstate.FieldState;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.util.Objects;

public class Field extends Hexagon implements InvalidationListener {

    private SpelveldModel model;
    private final Blend blend;
    private final FieldOverview fieldOverview;

    private Minion placedminion;
    private final FieldState fieldState;
    private final ImagePattern base;
    private final String name;
    private final Color selectedColor;
    private final Color enemyColor;
    private final Integer movementcost;

    public Field(Double radius, String name, int x, int y, double centerX, double centerY, Integer homebase, String imageName, boolean walkable, Integer movementcost, boolean canAttackFrom) {
        super(x, y, centerX, centerY, radius, homebase);
        blend = new Blend(x, y, centerX, centerY, radius, homebase);
        fieldOverview = new FieldOverview("tiles/" + imageName, name);

        this.base = new ImagePattern(new Image(Objects.requireNonNull(MinionWars.class.getResource("images/tiles/" + imageName)).toExternalForm()));
        this.name = name;
        this.movementcost = movementcost;
        fieldState = new FieldState(walkable, canAttackFrom);
        setPlacedMinion(null);

        selectedColor = Color.web("#156c15");
        enemyColor = Color.web("bd0505");
        setStrokeWidth(3);
        addEventHandler(MouseEvent.MOUSE_RELEASED, this::handleClicked);
    }

    public boolean hasMinion() { return fieldState.hasMinion(); }
    public boolean isHomebaseOfCurrentPlayer() { return homebase != null && homebase.equals(model.getCurrentSpeler().getNummer()); }
    public boolean isWalkable() { return fieldState.isWalkable(); }
    public boolean isCanAttackFrom() { return fieldState.isCanAttackFrom(); }
    public boolean isBonusable() { return hasMinion() && fieldState.isInBonusingRange(); }

    public Minion getMinion() { return placedminion; }
    public Integer getMovementCost() { return movementcost; }
    public Blend getBlend() { return blend; }
    public String getName() { return name; }
    public FieldOverview getFieldOverview() { return fieldOverview; }
    public FieldState getFieldState() { return fieldState; }
    public Color getSelectedColor() { return selectedColor; }
    public Color getEnemyColor() { return enemyColor; }
    public ImagePattern getBase() { return base; }

    public void setModel(SpelveldModel model) {
        this.model = model;
        model.addListener(this);
    }
    public void setPlacedMinion(Minion placedminion) {
        this.placedminion = placedminion;
        fieldState.setHasMinion(placedminion != null);
    }

    @Override
    public void invalidated(Observable ignore) {
        if (model.isStartingMainGame()) startedMainGame();
        blend.setTransparent();
        for (FieldHandler handler : model.getHandlers()) {
            handler.invalidate(model, this);
        }
    }

    private void handleClicked(MouseEvent mouseEvent) {
        boolean isGameStarted = model.isStartedMainGame();
        for (FieldHandler handler : model.getHandlers()) {
            if (isGameStarted && handler.handle(model, this)) return;
            else if (!isGameStarted) handler.handle(model, this);
        }
    }

    public void hovered(Observable observable) { model.setHoweredField(this); }
    public void startedMainGame() { hoverProperty().addListener(this::hovered); }
}