package be.ugent.objprog.minionwars.controllers;

import be.ugent.objprog.minionwars.eindscherm.EindScherm;
import be.ugent.objprog.minionwars.startscherm.input.Speler;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class EindSchermController extends ControllerHead {

    private final Speler winner;

    public EindSchermController(Speler winner) { this.winner = winner; }

    public Parent start() {
        StackPane rootPane = new StackPane();
        EindScherm startscherm = new EindScherm(winner, this);
        rootPane.getStyleClass().add("RootPane");
        rootPane.getChildren().add(startscherm);
        return rootPane;
    }

    public void nextFase() { application.endGame(); }
}