package be.ugent.objprog.minionwars.eindscherm;

import be.ugent.objprog.minionwars.controllers.EindSchermController;
import be.ugent.objprog.minionwars.startscherm.input.Speler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class EindScherm extends VBox {

    private final EindSchermController controller;

    public EindScherm(Speler winner, EindSchermController controller) {
        this.controller = controller;
        getStyleClass().add("EindScherm");
        setSpacing(20);

        Label winnerLabel = new Label("Het spel werd gewonnen door " + winner.getNaam() + "!");
        winnerLabel.setAlignment(Pos.CENTER);
        winnerLabel.setTextAlignment(TextAlignment.CENTER);
        winnerLabel.setWrapText(true);
        winnerLabel.setMaxWidth(Double.MAX_VALUE);
        winnerLabel.setPrefWidth(400);

        Button exitButton = new Button("Spel afsluiten");
        exitButton.setOnAction(this::handleExitButton);

        this.setAlignment(Pos.CENTER);
        getChildren().addAll(winnerLabel, exitButton);
    }

    private void handleExitButton(ActionEvent actionEvent) {
        controller.nextFase();
    }
}