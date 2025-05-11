package be.ugent.objprog.minionwars.startscherm.input.inputparent;

import javafx.beans.Observable;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public abstract class Input extends GridPane {

    private final TextField input;
    private final String type;
    private final Label error;

    public Input(String type, Label label, String error) {
        this.type = type;
        this.input = new TextField();
        this.error = new Label(error);
        this.error.getStyleClass().add("ErrorLabel");

        add(label, 0, 0);
        add(input, 1, 0);
        add(this.error, 1, 1);

        GridPane.setValignment(label, VPos.CENTER);
        setHgap(10);
        setVgap(2);

        input.textProperty().addListener(this::checkValid);
        setCommonStyle();
    }

    public abstract boolean notValid();

    public void setCommonStyle() {
        getStyleClass().clear();
        getStyleClass().add(type);
        error.setOpacity(0);
    }

    public void setErrorStyle() {
        getStyleClass().add("Error");
        error.setOpacity(1);
    }

    public TextField getInput() { return input; }

    public void checkValid(Observable observable) {
        if (notValid()) setErrorStyle();
        else setCommonStyle();
    }
}