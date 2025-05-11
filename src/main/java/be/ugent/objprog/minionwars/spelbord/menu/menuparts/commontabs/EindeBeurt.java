package be.ugent.objprog.minionwars.spelbord.menu.menuparts.commontabs;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class EindeBeurt extends HBox implements InvalidationListener {

    private final MenuModel model;
    private final Button eindebeurt;
    private final Button rust;

    public EindeBeurt(MenuModel model) {
        this.model = model;
        model.addListener(this);
        eindebeurt = new Button("Einde beurt");
        eindebeurt.setOnAction(this::eindebeurtHandle);
        rust = new Button("Rust");
        rust.setOnAction(this::rustHandle);
        getChildren().add(eindebeurt);
        setAlignment(Pos.CENTER);
        setSpacing(10);
    }

    public void setButtonDisabled(Button button, boolean disable) {
        button.setDisable(disable);
        if (disable) button.getStyleClass().add("ButtonUsed");
        else button.getStyleClass().removeAll("ButtonUsed");
    }

    public void eindebeurtHandle(ActionEvent actionEvent) {
        model.changeSpeler();
    }
    public void eindebeurtInvalidate() {
        setButtonDisabled(eindebeurt, !model.getCurrentSpeler().isAfgehandeld());
    }

    public void rustHandle(ActionEvent actionEvent) {
        Minion selected = model.getSelectedSpelveldMinion();
        if (selected != null) selected.rest();
        model.setRested();
    }
    public void rustInvalidate() {
        Minion selected = model.getSelectedSpelveldMinion();
        getChildren().remove(rust);
        if (selected == null || !model.isCurrentPlayerMinion(selected)) return;
        getChildren().addFirst(rust);
        setButtonDisabled(rust, !selected.canRest());
    }

    @Override
    public void invalidated(Observable ignore) {
        if (model.isStartedMainGame()) {
            eindebeurtInvalidate();
            rustInvalidate();
            return;
        }
        setButtonDisabled(eindebeurt, model.getCurrentSpeler().getAantalMinions() <= 0);
    }
}