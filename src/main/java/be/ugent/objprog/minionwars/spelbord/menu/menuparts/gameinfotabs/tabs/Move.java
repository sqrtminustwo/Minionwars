package be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.tabhandler.TabHandler;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.tabhandler.movetabhandler.StayHandler;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.tabparent.GameInfoTab;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class Move extends GameInfoTab {

    private final Button skipMove;
    private final List<TabHandler> handlers;

    public Move(MenuModel model) {
        super("Bewegen", model);

        setSpacingAndAlignment();
        Label label = new Label("Selecteer een groen veld op het\nspelbord of kies om te blijven\nstaan");
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        skipMove = new Button("Blijf staan");
        skipMove.setOnAction(this::handleSkipMove);

        tabActions.getChildren().addAll(label, skipMove);
        pane.getChildren().add(tabActions);
        pane.getStyleClass().add("GameInfoTab");
        handlers = List.of(
                new StayHandler()
        );
    }

    private void handleSkipMove(ActionEvent actionEvent) { model.setMoved(); }

    public void change() {
        if (!model.getSelectedSpelveldMinion().isMoved()) {
            model.setMoveSelecting();
        }
    }

    @Override
    public void invalidated(Observable ignore) {
        if (model.getSelectedSpelveldMinion() != null) {
            for (TabHandler handler : handlers) {
                handler.handleUIUpdate(model);
            }
        }
    }

    public Button getSkipMove() { return skipMove; }
}