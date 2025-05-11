package be.ugent.objprog.minionwars.startscherm;

import be.ugent.objprog.minionwars.controllers.StartSchermController;
import be.ugent.objprog.minionwars.startscherm.input.inputparent.Input;
import be.ugent.objprog.minionwars.startscherm.input.Munten;
import be.ugent.objprog.minionwars.startscherm.input.Speler;
import be.ugent.objprog.minionwars.startscherm.input.StartSpel;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;

public class StartScherm extends VBox {

    private final List<Speler> spelers;
    private final Munten munten;
    private final ArrayList<Input> inputs;
    private final StartSchermController controller;

    public StartScherm(StartSchermController controller) {
        this.controller = controller;
        getStyleClass().add("StartScherm");

        spelers = List.of(new Speler(1), new Speler(2));
        munten = new Munten();
        StartSpel startspel = new StartSpel(this);
        inputs = new ArrayList<>();
        inputs.addAll(spelers);
        inputs.add(munten);
        getChildren().addAll(inputs);
        getChildren().add(startspel);
    }

    public void nextFase() {
        for (Speler speler : spelers) {
            speler.setMunten(munten.getMunten());
        }
        controller.nextFase(spelers);
    }

    public boolean validInputs() {
        boolean valid = true;
        for (Input input : inputs) {
            if (input.notValid()) {
                input.setErrorStyle();
                valid = false;
            }
        }
        return valid;
    }
}