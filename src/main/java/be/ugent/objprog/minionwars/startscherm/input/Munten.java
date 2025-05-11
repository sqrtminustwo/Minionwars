package be.ugent.objprog.minionwars.startscherm.input;

import be.ugent.objprog.minionwars.startscherm.input.inputparent.Input;
import javafx.scene.control.Label;

public class Munten extends Input {

    private int munten;

    public Munten() {
        super("Munten", new Label("Munten"), "Tussen 10 en 50 (inclusief)");
    }

    public int getMunten() {
        return munten;
    }

    public boolean notValid() {
        String input = getInput().getText();
        if (input.matches("^\\d{1,2}$")) {
            munten = Integer.parseInt(input);
            return munten < 10 || munten > 50;
        }
        return true;
    }
}