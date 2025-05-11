package be.ugent.objprog.minionwars.startscherm.input;

import be.ugent.objprog.minionwars.startscherm.StartScherm;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class StartSpel extends Button {

    private final StartScherm startscherm;

    public StartSpel(StartScherm startscherm) {
        this.startscherm = startscherm;
        setText("Start spel");
        setOnAction(this::handleClick);
        getStyleClass().add("StartSpel");
    }

    public void handleClick(ActionEvent event) {
        if (startscherm.validInputs()) startscherm.nextFase();
    }
}