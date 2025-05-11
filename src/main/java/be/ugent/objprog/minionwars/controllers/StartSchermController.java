package be.ugent.objprog.minionwars.controllers;

import be.ugent.objprog.minionwars.startscherm.StartScherm;
import be.ugent.objprog.minionwars.startscherm.input.Speler;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import java.util.List;

public class StartSchermController extends ControllerHead {

    public Parent start() {
        StackPane rootPane = new StackPane();
        StartScherm startscherm = new StartScherm(this);
        rootPane.getStyleClass().add("RootPane");
        rootPane.getChildren().add(startscherm);
        return rootPane;
    }

    public void nextFase(List<Speler> spelers) {
        try {
            application.spelBord(spelers);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}